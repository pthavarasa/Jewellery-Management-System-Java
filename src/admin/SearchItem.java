package admin;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchItem {
    public static JewelsData selectedItem;
    public static JewelsData display() {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Search Jewel By Category");
        stage.getIcons().add(new Image("file:src/img/diamond.png"));
        stage.setWidth(450);
        stage.setHeight(550);

        final Label label = new Label("Jewels List");
        label.setFont(new Font("Arial", 20));


        TableView<JewelsData> jewelsDataTableView = new TableView<JewelsData>();
        TableColumn<JewelsData, String> jewelsDataStringIDColumn = new TableColumn<JewelsData, String>("ID");
        TableColumn<JewelsData, String> jewelsDataStringTypeColumn = new TableColumn<JewelsData, String>("Type");
        TableColumn<JewelsData, String> jewelsDataStringNameColumn = new TableColumn<JewelsData, String>("Name");
        TableColumn<JewelsData, String> jewelsDataStringPriceColumn = new TableColumn<JewelsData, String>("Price");


        jewelsDataStringIDColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("ID"));
        jewelsDataStringTypeColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("Type"));
        jewelsDataStringNameColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("Name"));
        jewelsDataStringPriceColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("Price"));

        jewelsDataTableView.getColumns().addAll(jewelsDataStringIDColumn,jewelsDataStringTypeColumn,jewelsDataStringNameColumn, jewelsDataStringPriceColumn);

        //Adding ChoiceBox and TextField here!
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("ID", "Type", "Name", "Price");
        choiceBox.setValue("ID");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.setOnKeyReleased(keyEvent ->
        {
            String sql;
            switch (choiceBox.getValue())//Switch on choiceBox value
            {
                case "Type":
                    sql = "SELECT * FROM jewels WHERE Type LIKE '"+textField.getText()+"%'";
                    break;
                case "Name":
                    sql = "SELECT * FROM jewels WHERE Name LIKE '"+textField.getText()+"%'";
                    break;
                case "Price":
                    sql = "SELECT * FROM jewels WHERE Price LIKE '"+textField.getText()+"%'";
                    break;
                default:
                    sql = "SELECT * FROM jewels WHERE ID LIKE '"+textField.getText()+"%'";
            }
            ObservableList<JewelsData> jewelsData;
            jewelsData = FXCollections.observableArrayList();
            try{
                Connection con = DbConnection.getConnection();
                assert con != null;
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()){
                    jewelsData.add(
                            new JewelsData(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(4)
                            )
                    );
                }
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            jewelsDataTableView.setItems(null);
            jewelsDataTableView.setItems(jewelsData);
        });

        jewelsDataTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if(jewelsDataTableView.getSelectionModel().isEmpty()){
                System.out.println("select ");
            }else{
                selectedItem = jewelsDataTableView.getSelectionModel().getSelectedItem();
                stage.close();
            }

        });
        HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
        hBox.setAlignment(Pos.CENTER);//Center HBox
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, jewelsDataTableView, hBox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();


        return selectedItem;
    }
}
