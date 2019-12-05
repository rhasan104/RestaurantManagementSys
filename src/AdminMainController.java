import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class AdminMainController implements Initializable {

    int n = 0;

    @FXML
    private WebView adminMain;

    @FXML
    private JFXButton btnSignOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        WebEngine engine = adminMain.getEngine();
        engine.load("http://www.google.com");
    }

    @FXML
    void initialize(MouseEvent event) {
        System.out.println(n);
        if ( n == 0) {
            n++;
            WebEngine engine = adminMain.getEngine();
            engine.load("https://venus.cs.qc.cuny.edu/~hara6098/php/index.php");
        }
    }

    @FXML
    void signOutBtnClicked(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
            Stage stage = (Stage) btnSignOut.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
    }
}


