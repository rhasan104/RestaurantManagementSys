import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LandingPageController implements Initializable {

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXTextField xCoor;

    @FXML
    private JFXTextField yCoor;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableView<Restaurant> restaurantTable;

    @FXML
    private TableColumn<Restaurant, String> restaurantName;

    @FXML
    private TableColumn<Restaurant, String> restaurantlocation;

    @FXML
    private TableColumn<Restaurant, String> restaurantGenre;

    @FXML
    private TableColumn<Restaurant, Double> restaurantDistance;

    ObservableList<Restaurant> restaurantList = FXCollections.observableArrayList();

    public int userX;

    public int userY;

    @FXML
    void adminButtonClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
            Stage stage = (Stage) btnAdmin.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);


        }catch (Exception e){
            System.err.print(e.getMessage());
        }
    }

    @FXML
    void searchWasClicked(ActionEvent event) {

        double distance;
        restaurantList.clear();

        try {
            Connection con = ConnectionDB.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT restaurantID, restaurantName, location, genre FROM restaurants;");

            while(rs.next()) {

                distance = calculateDistance(rs.getString("location"));
                restaurantList.add(new Restaurant(rs.getInt("restaurantID"),
                        rs.getString("restaurantName"), rs.getString("location"), rs.getString("genre"), distance));
            }
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        itemID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemID"));
        restaurantName.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("name"));
        restaurantlocation.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("location"));
        restaurantGenre.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("genre"));
        restaurantDistance.setCellValueFactory(new PropertyValueFactory<Restaurant, Double>("distance"));

        restaurantTable.setItems(restaurantList);
        restaurantTable.getSortOrder().add(restaurantDistance);

    }


    public double calculateDistance(String location) {

        int userX = Integer.parseInt(xCoor.getText());
        int userY = Integer.parseInt(yCoor.getText());

        String[] parts = location.split("-");

        int restX = Integer.parseInt(parts[0]);
        int restY = Integer.parseInt(parts[1]);

        double distance = Math.sqrt((Math.pow(userX - restX, 2)) + (Math.pow(userY-restY, 2)));

        return (Math.round(distance * 100.0) / 100.0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showTable();

        restaurantTable.setRowFactory(tv -> {
            TableRow<Restaurant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Restaurant rowData = row.getItem();
                    openUserUI(rowData);
                }
            });
            return row;
        });
    }

    public void openUserUI (Restaurant row) {

        UserUIController.setCurrentID = row.getId();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserUI.fxml"));
            Stage stage = (Stage) btnAdmin.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e){
            System.err.print(e.getMessage());
        }

    }

    public void showTable () {

        double distance;

        try {
            Connection con = ConnectionDB.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT restaurantID, restaurantName, location, genre FROM restaurants;");

            while(rs.next()) {

                distance = calculateDefaultDistance(rs.getString("location"));
                restaurantList.add(new Restaurant(rs.getInt("restaurantID"),
                        rs.getString("restaurantName"), rs.getString("location"), rs.getString("genre"), distance));
            }
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        itemID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemID"));
        restaurantName.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("name"));
        restaurantlocation.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("location"));
        restaurantGenre.setCellValueFactory(new PropertyValueFactory<Restaurant, String>("genre"));
        restaurantDistance.setCellValueFactory(new PropertyValueFactory<Restaurant, Double>("distance"));

        restaurantTable.setItems(restaurantList);
        restaurantTable.getSortOrder().add(restaurantDistance);
    }

    public double calculateDefaultDistance(String location) {

        int userX = 50;
        int userY = 50;

        String[] parts = location.split("-");

        int restX = Integer.parseInt(parts[0]);
        int restY = Integer.parseInt(parts[1]);

        double distance = Math.sqrt((Math.pow(userX - restX, 2)) + (Math.pow(userY-restY, 2)));

        return (Math.round(distance * 100.0) / 100.0);
    }
}
