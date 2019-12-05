import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnCustSupport;

    @FXML
    void custSupportBtnWasClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerSupport.fxml"));
            Stage stage = (Stage) btnCustSupport.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e){
            System.err.print(e.getMessage());
        }
    }

    @FXML
    void homeBtnWasClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = (Stage) btnHome.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e){
            System.err.print(e.getMessage());
        }
    }

    @FXML
    void loginBtnWasClicked(ActionEvent event) {

        boolean userExsists = false;
        String userID = username.getText();
        String pass = password.getText();
        try {
            userExsists = ConnectionDB.isValidUser(userID, pass);

            if(userExsists) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminUI.fxml"));
                Stage stage = (Stage) btnLogin.getScene().getWindow();

                Scene scene = new Scene(loader.load());
                stage.setScene(scene);

            }
        } catch (Exception e) {

            System.err.print(e.getMessage());
        }

    }
}