import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserUIController implements Initializable {

    @FXML
    private JFXButton btnAppetizers;

    @FXML
    private JFXButton btnEntrees;

    @FXML
    private JFXButton btnDessert;

    @FXML
    private JFXButton btnDrinks;

    @FXML
    private JFXButton btnLunch;

    @FXML
    private JFXButton btnSpecial;

    @FXML
    private ChoiceBox<String> chooseRestaurant;

    @FXML
    private Button btnSelectRestaurant;

    @FXML
    private Text txtRestaurantName;

    @FXML
    private Text txtRestaurantLocation;

    @FXML
    private Text textOwner;

    @FXML
    private Text txtRestaurantHours;

    @FXML
    private Text txtRestLocation;

    @FXML
    private Text txtRestOwner;

    @FXML
    private Text txtRestHours;

    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, Integer> itemID;

    @FXML
    private TableColumn<Item, String> itemName;

    @FXML
    private TableColumn<Item, String> price;

    @FXML
    private TableColumn<Item, String> Availability;

    @FXML
    private Button btnGoBack;

    public static int setCurrentID;

    private int currentRestaurantId = setCurrentID;

    private int currentMenuId;

    ObservableList<Item> menuItems = FXCollections.observableArrayList();

    public Restaurant[] restaurantList;


    @FXML
    void appetizersWasClicked(ActionEvent event) {

        currentMenuId = 5;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void dessertWasClicked(ActionEvent event) {

        currentMenuId = 1;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void drinksWasClicked(ActionEvent event) {

        currentMenuId = 4;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void entreesWasClicked(ActionEvent event) {

        currentMenuId = 2;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void lunchWasClicked(ActionEvent event) {

        currentMenuId = 3;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void selectRestaurantClicked(ActionEvent event) {

        String currentRest = chooseRestaurant.getValue();

        for (int i = 0; i < restaurantList.length; i++) {

            if(currentRest.equals(restaurantList[i].name)){

                currentRestaurantId = restaurantList[i].id;
                break;
            }
            continue;
        }

        currentMenuId = 5;

        renderHeading(currentRestaurantId);
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void goBackClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = (Stage) btnGoBack.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e){
            System.err.print(e.getMessage());
        }
    }

    @FXML
    void specialWasCliked(ActionEvent event) {

        currentMenuId = 6;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<String> restaurantNames = new ArrayList<>();
        ArrayList<Integer> restaurantIDs = new ArrayList<>();

        try {

            Connection con = ConnectionDB.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT restaurantID, restaurantName FROM restaurants;");

            while(rs.next()) {

                restaurantIDs.add(rs.getInt("restaurantID"));
                restaurantNames.add(rs.getString("restaurantName"));
            }

            con.close();

        } catch (SQLException ex){

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);

        }

        restaurantList = new Restaurant[restaurantNames.size()];

        for (int i = 0; i < restaurantNames.size(); i++){

            chooseRestaurant.getItems().add(restaurantNames.get(i));
            restaurantList[i] = new Restaurant(restaurantNames.get(i), restaurantIDs.get(i));

        }

        for(int i = 0; i < restaurantList.length; i++) {

            if(currentRestaurantId == restaurantList[i].id){
                chooseRestaurant.setValue(restaurantNames.get(i));
                break;
            }
        }


        currentMenuId = 5;

        populateTable(currentMenuId, currentRestaurantId);

        renderHeading(currentRestaurantId);
    }


    public void populateTable(int foodTypeID, int restaurantID) {

        try {
            Connection con = ConnectionDB.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT itemID, itemName, price, Availability FROM items WHERE foodTypeID = " + foodTypeID + " AND restaurantID = " + restaurantID + ";");

            while(rs.next()) {
                menuItems.add(new Item(rs.getInt("itemID"), rs.getString("itemName"), rs.getString("price"), rs.getString("Availability")));
            }
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        itemID.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemID"));
        itemName.setCellValueFactory(new PropertyValueFactory<Item, String>("itemName"));
        price.setCellValueFactory(new PropertyValueFactory<Item, String>("Price"));
        Availability.setCellValueFactory(new PropertyValueFactory<Item, String>("Availability"));

        tableView.setItems(menuItems);
    }

    void renderHeading(int restuId) {

        try {
            Connection con = ConnectionDB.getConnection();

            ResultSet rs = con.createStatement().executeQuery(
                    "SELECT restaurantName, owner, location, businessHours FROM restaurants WHERE restaurantID = " + restuId + ";");

            while(rs.next()) {
                txtRestLocation.setText(rs.getString("location"));
                txtRestHours.setText(rs.getString("businessHours"));
                txtRestOwner.setText(rs.getString("owner"));
                txtRestaurantName.setText(rs.getString("restaurantName"));
            }

            con.close();
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
