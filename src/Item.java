import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {

    private final SimpleIntegerProperty itemID;
    private final SimpleStringProperty itemName;
    private final SimpleStringProperty price;
    private final SimpleStringProperty Availability;

    protected Item(Integer itemID, String itemName, String price, String Availability) {
        this.itemID = new SimpleIntegerProperty(itemID);
        this.itemName = new SimpleStringProperty(itemName);
        this.price = new SimpleStringProperty(price);
        this.Availability = new SimpleStringProperty(Availability);
    }

    public void setItemID(Integer itemID) {
        this.itemID.set(itemID);
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public void setAvailability(String availability) {
        this.Availability.set(availability);
    }

    public Integer getItemID() {
        return itemID.get();
    }

    public String getItemName() {
        return itemName.get();
    }

    public String getPrice() {
        return price.get();
    }

    public String getAvailability() {
        return Availability.get();
    }

}
