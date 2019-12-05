import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddItemController {

    @FXML
    private JFXTextField foodName;

    @FXML
    private JFXTextField foodPrice;

    @FXML
    private JFXTextField foodAvailability;

    @FXML
    private JFXButton btnAddFood;

    @FXML
    private JFXButton btnCancel;

    public static String[] data;



    @FXML
    void addWasClicked(ActionEvent event) {

        if(isDouble()) {

            data = new String[3];
            data[0] = foodName.getText();
            data[1] = foodPrice.getText();
            data[2] = foodAvailability.getText();

        }
        Stage newBox = (Stage) btnAddFood.getScene().getWindow();
        newBox.close();
    }

    @FXML
    void cancelWasClicked(ActionEvent event) {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


    public boolean isDouble () {

        try{

            double price = Double.parseDouble(foodPrice.getText());
            return true;

        }catch(NumberFormatException e){

            AlertBox.display("Rejected", foodPrice.getText() + " is not a price");
            return false;
        }
    }

}
