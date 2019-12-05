import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewEntityController {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField owner;

    @FXML
    private JFXTextField restLocation;

    @FXML
    private Button btnCheckLocation;


    public static String[] newEntity = new String[6];

    @FXML
    void checkLocationClicked(ActionEvent event) {

        String[] parts = restLocation.getText().split("-");
        String newLocation = String.format("%03d", Integer.parseInt(parts[0])) + "-" + String.format("%03d", Integer.parseInt(parts[1]));

        System.out.println(newLocation);

        try {
            Connection con = ConnectionDB.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT location FROM restaurants WHERE location = " + newLocation + ";");

            if(rs.next()) {
                AlertBox.display("Rejected", restLocation.getText() + " is not available. Try a different location.");
            }
            else {

                newEntity[0] = name.getText();
                newEntity[1] = owner.getText();
                newEntity[2] = newLocation;

                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("NewEntityPage2.fxml"));
                    Stage addRestaurant = new Stage();
                    addRestaurant.initModality(Modality.APPLICATION_MODAL);
                    addRestaurant.setTitle("Add New Restaurant");
                    addRestaurant.setResizable(false);

                    Scene scene = new Scene(loader.load());
                    addRestaurant.setScene(scene);
                    addRestaurant.showAndWait();

                } catch (Exception e) {

                    System.err.print(e.getMessage());
                }
                newEntity[3] = NewEntityPage2Controller.moreData[0];
                newEntity[4] = NewEntityPage2Controller.moreData[1];
                newEntity[5] = NewEntityPage2Controller.moreData[2];

            }

            con.close();
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        createNewRestaurant();

        Stage newBox = (Stage) btnCheckLocation.getScene().getWindow();
        newBox.close();

    }

    public void createNewRestaurant() {

        try {
            Connection con = ConnectionDB.getConnection();

            con.createStatement().execute("INSERT INTO restaurants (restaurantName, owner, contactPerson, location, genre, businessHours) " +
                    "VALUES (\"" + newEntity[0] + "\", \"" + newEntity[1] + "\", \"" + newEntity[3] + "\", \"" + newEntity[2] +
                    "\", \"" + newEntity[4] + "\", \"" + newEntity[5] + "\"); ");

            con.close();
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}



