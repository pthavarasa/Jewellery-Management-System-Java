package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Login extends Application {
    public void start(Stage stage)throws Exception{
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("login.fxml"));
        root.getStylesheets().add(getClass().getResource("loginStyle.css").toString());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Jewelry Shop Management");
        stage.getIcons().add(new Image("file:src/img/diamond.png"));
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}
