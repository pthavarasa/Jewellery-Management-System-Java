package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceData {
    private final StringProperty ID;
    private final StringProperty Description;
    private final StringProperty Quantitiy;
    private final StringProperty Amount;

    public InvoiceData(String id, String description, String quantitiy, String amount){
        this.ID = new SimpleStringProperty(id);
        this.Description = new SimpleStringProperty(description);
        this.Quantitiy = new SimpleStringProperty(quantitiy);
        this.Amount = new SimpleStringProperty(amount);
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

    public String getDescription() {
        return Description.get();
    }

    public StringProperty descriptionProperty() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description.set(description);
    }

    public String getQuantitiy() {
        return Quantitiy.get();
    }

    public StringProperty quantitiyProperty() {
        return Quantitiy;
    }

    public void setQuantitiy(String quantitiy) {
        this.Quantitiy.set(quantitiy);
    }

    public String getAmount() {
        return Amount.get();
    }

    public StringProperty amountProperty() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount.set(amount);
    }
}
