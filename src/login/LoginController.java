package login;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import worker.WorkerController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbStatus;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<Option> comboBox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginError;
    @FXML
    private CheckBox viewPassCheckBox;

    public void initialize(URL url, ResourceBundle rb){
        if(!this.loginModel.isDatabaseConnected()){
            this.dbStatus.setText("Not Connected To Database!");
        }

        this.comboBox.setItems(FXCollections.observableArrayList(Option.values()));
    }

    @FXML
    public void Login(ActionEvent event){
        try{
            if(this.comboBox.getSelectionModel().isEmpty()){ this.loginError.setText("Select Your Division!"); return;}
            if(this.loginModel.isLogin(this.userName.getText(), this.password.getText(), ((Option)this.comboBox.getValue()).toString())){
                Stage stage = (Stage)this.loginButton.getScene().getWindow();
                stage.close();
                switch (((Option)this.comboBox.getValue()).toString()){
                    case "Admin":
                        adminLogin();
                        break;
                    case "Worker":
                        workerLogin();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + ((Option) this.comboBox.getValue()).toString());
                }
            }else{
                this.loginError.setText("Wrong Information!");
            }
        }catch (Exception LocalException){
            LocalException.printStackTrace();
        }
    }

    public void adminLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = (Pane) FXMLLoader.load(getClass().getResource("/admin/admin.fxml"));
            root.getStylesheets().add(getClass().getResource("/admin/adminStyle.css").toString());
            AdminController adminController = (AdminController)loader.getController();
            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Admin Dashboard");
            userStage.getIcons().add(new Image("file:src/img/diamond.png"));
            userStage.setResizable(false);
            userStage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void workerLogin(){
        try{
            Stage userStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane workerRoot = (Pane) FXMLLoader.load(getClass().getResource("/worker/worker.fxml"));

            WorkerController workerController = (WorkerController)loader.getController();

            Scene scene = new Scene(workerRoot);
            userStage.setScene(scene);
            userStage.setTitle("Worker Dashboard");
            userStage.getIcons().add(new Image("file:src/img/diamond.png"));
            userStage.setResizable(false);
            userStage.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void viewPass(ActionEvent actionEvent) {
        if(viewPassCheckBox.isSelected()){
            password.setPromptText(password.getText());
            password.setText("");
            password.setDisable(true);
        }else{
            password.setText(password.getPromptText());
            password.setPromptText("");
            password.setDisable(false);
        }
    }
}
