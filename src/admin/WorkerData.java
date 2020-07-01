package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WorkerData {
    private final StringProperty UserName;
    private final StringProperty Password;
    private final StringProperty Div;

    public WorkerData(String userName, String password, String div) {
        this.UserName = new SimpleStringProperty(userName);
        this.Password = new SimpleStringProperty(password);
        this.Div = new SimpleStringProperty(div);
    }

    public String getDiv() {
        return Div.get();
    }

    public StringProperty divProperty() {
        return Div;
    }

    public void setDiv(String div) {
        this.Div.set(div);
    }

    public String getUserName() {
        return UserName.get();
    }

    public StringProperty userNameProperty() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName.set(userName);
    }

    public String getPassword() {
        return Password.get();
    }

    public StringProperty passwordProperty() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password.set(password);
    }
}
