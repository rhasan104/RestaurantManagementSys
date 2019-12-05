import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NewEntityPage2Controller {

    @FXML
    private JFXTextField contact;

    @FXML
    private JFXTextField genre;

    @FXML
    private JFXTextField hours;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBack;

    public static String[] moreData = new String[3];

    @FXML
    void addClicked(ActionEvent event) {

        moreData[0] = contact.getText();
        moreData[1] = genre.getText();
        moreData[2] = hours.getText();

        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }

    @FXML
    void backClicked(ActionEvent event) {

        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

}
