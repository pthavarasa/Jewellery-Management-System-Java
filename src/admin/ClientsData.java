package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientsData {
    private final StringProperty ID;
    private final StringProperty FName;
    private final StringProperty LName;
    private final StringProperty Phone;
    private final StringProperty Address;
    private final StringProperty Mail;
    private final StringProperty LCart;

    public ClientsData(String id, String fName, String lName, String phone, String address, String mail, String lCart) {
        this.ID = new SimpleStringProperty(id);
        this.FName = new SimpleStringProperty(fName);
        this.LName = new SimpleStringProperty(lName);
        this.Phone = new SimpleStringProperty(phone);
        this.Address = new SimpleStringProperty(address);
        this.Mail = new SimpleStringProperty(mail);
        this.LCart = new SimpleStringProperty(lCart);
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

    public String getFName() {
        return FName.get();
    }

    public StringProperty FNameProperty() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName.set(FName);
    }

    public String getLName() {
        return LName.get();
    }

    public StringProperty LNameProperty() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName.set(LName);
    }

    public String getPhone() {
        return Phone.get();
    }

    public StringProperty phoneProperty() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone.set(phone);
    }

    public String getAddress() {
        return Address.get();
    }

    public StringProperty addressProperty() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address.set(address);
    }

    public String getMail() {
        return Mail.get();
    }

    public StringProperty mailProperty() {
        return Mail;
    }

    public void setMail(String mail) {
        this.Mail.set(mail);
    }

    public String getLCart() {
        return LCart.get();
    }

    public StringProperty LCartProperty() {
        return LCart;
    }

    public void setLCart(String LCart) {
        this.LCart.set(LCart);
    }
}
