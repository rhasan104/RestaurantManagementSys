import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminUIController implements Initializable {

    @FXML
    private ChoiceBox<String> chooseRestaurant;

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
    private Button btnAddItem;

    @FXML
    private Button btnRemoveItem;

    @FXML
    private Button btnEditItem;

    @FXML
    private Button btnSelectRestaurant;

    @FXML
    private Button btnSignOut;

    private int currentRestaurantId;

    private int currentMenuId;

    ObservableList<Item> menuItems = FXCollections.observableArrayList();

    public Restaurant[] restaurantList;

    @FXML
    void addItemWasClicked(ActionEvent event) {

        double itemPrice;
        String itemAvailability;
        String nameOfItem;

        String[] data;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddItem.fxml"));
            Stage addItemWindow = new Stage();
            addItemWindow.initModality(Modality.APPLICATION_MODAL);
            addItemWindow.setTitle("Add Item");
            addItemWindow.setResizable(false);

            Scene scene = new Scene(loader.load());
            addItemWindow.setScene(scene);
            addItemWindow.showAndWait();

        } catch (Exception e){

            System.err.print(e.getMessage());
        }

        try {

            data = AddItemController.data;

            nameOfItem = data[0];
            itemPrice = Math.round(Double.parseDouble(data[1]) * 100.0) / 100.0;
            itemAvailability = data[2];

            String s = "INSERT INTO items (restaurantID, foodTypeID, price, Availability, itemName) " +
                    "VALUES (" + currentRestaurantId + ", " + currentMenuId + ", " + itemPrice + ", \'" +
                    itemAvailability + "\', \'" + nameOfItem + "\');";

            try {
                Connection con = ConnectionDB.getConnection();

                con.createStatement().execute(s);

                con.close();
            }
            catch (SQLException ex) {

                Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {}

        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void addRestaurantWasClicked(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewEntity.fxml"));
            Stage addRestaurantWindow = new Stage();
            addRestaurantWindow.initModality(Modality.APPLICATION_MODAL);
            addRestaurantWindow.setTitle("Add New Restaurant");
            addRestaurantWindow.setResizable(false);

            Scene scene = new Scene(loader.load());
            addRestaurantWindow.setScene(scene);
            addRestaurantWindow.showAndWait();

        } catch (Exception e) {

            System.err.print(e.getMessage());
        }

    }

    @FXML
    void appetizersWasClicked(ActionEvent event) {

        currentMenuId = 5;
        menuItems.clear();
        populateTable(currentMenuId, currentRestaurantId);

    }

    @FXML
    void editItemWasClicked(ActionEvent event) throws NullPointerException {

        Item row = tableView.getSelectionModel().getSelectedItem();
        String itemAvailability;
        String nameOfItem;
        int itemID = row.getItemID();

        String[] data;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditItem.fxml"));
            Stage editItemWindow = new Stage();
            editItemWindow.initModality(Modality.APPLICATION_MODAL);
            editItemWindow.setTitle("Edit Item");
            editItemWindow.setResizable(false);

            Scene scene = new Scene(loader.load());
            editItemWindow.setScene(scene);
            editItemWindow.showAndWait();


        } catch (Exception e){

            System.err.print(e.getMessage());
        }

        try {

            data = EditItemController.data;

            nameOfItem = data[0];
            double newPrice = Math.round(Double.parseDouble(data[1]) * 100.0) / 100.0;
            itemAvailability = data[2];


            String s = "UPDATE items SET restaurantID = " + currentRestaurantId + ", foodTypeID = " + currentMenuId +
                       ", price = " + newPrice + ", Availability = \"" + itemAvailability + "\", itemName = \"" + nameOfItem +
                       "\" WHERE itemID = " + itemID + ";";


            try {
                Connection con = ConnectionDB.getConnection();

                con.createStatement().execute(s);

                con.close();
            }
            catch (SQLException ex) {

                Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (Exception e) {}
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
    void removeItemWasClicked(ActionEvent event) {

        Item row = tableView.getSelectionModel().getSelectedItem();

        try {
            Connection con = ConnectionDB.getConnection();

            con.createStatement().execute("DELETE FROM items WHERE itemID = " + row.getItemID() + ";");

            con.close();
        }
        catch (SQLException ex) {

            Logger.getLogger(AdminUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableView.getItems().removeAll(row);

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

    @FXML
    void specialWasCliked(ActionEvent event) {

        currentMenuId = 6;
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

        chooseRestaurant.setValue(restaurantNames.get(0));

        currentRestaurantId = restaurantIDs.get(0);
        currentMenuId = 5;

        populateTable(currentMenuId, currentRestaurantId);

        renderHeading(currentRestaurantId);

    }

    @FXML
    void signOutClicked(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
            Stage stage = (Stage) btnSignOut.getScene().getWindow();

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

        } catch (Exception e){
            System.err.print(e.getMessage());
        }
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

}

