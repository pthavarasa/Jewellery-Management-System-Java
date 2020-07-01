package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JewelsData {
    private final StringProperty ID;
    private final StringProperty Type;
    private final StringProperty Name;
    private final StringProperty Price;

    public JewelsData(String id, String type, String name, String price){
        this.ID = new SimpleStringProperty(id);
        this.Type = new SimpleStringProperty(type);
        this.Name = new SimpleStringProperty(name);
        this.Price = new SimpleStringProperty(price);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getType() {
        return Type.get();
    }

    public StringProperty typeProperty() {
        return Type;
    }

    public void setType(String type) {
        this.Type.set(type);
    }

    public String getName() {
        return Name.get();
    }

    public StringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getPrice() {
        return Price.get();
    }

    public StringProperty priceProperty() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price.set(price);
    }
}
