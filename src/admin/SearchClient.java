package admin;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class SearchClient {
    public static ClientsData selectedItem;
    public static ClientsData display() {
        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Search Client By Category");
        stage.getIcons().add(new Image("file:src/img/diamond.png"));
        stage.setWidth(600);
        stage.setHeight(550);

        final Label label = new Label("Clients List");
        label.setFont(new Font("Arial", 20));


        TableView<ClientsData> clientsDataStringTableView = new TableView<ClientsData>();
        TableColumn<ClientsData, String> clientsDataStringIDColumn = new TableColumn<ClientsData, String>("ID");
        TableColumn<ClientsData, String> clientsDataStringFNameColumn = new TableColumn<ClientsData, String>("First Name");
        TableColumn<ClientsData, String> clientsDataStringLNameColumn = new TableColumn<ClientsData, String>("Last Name");
        TableColumn<ClientsData, String> clientsDataStringPhoneColumn = new TableColumn<ClientsData, String>("Phone");
        TableColumn<ClientsData, String> clientsDataStringEmailColumn = new TableColumn<ClientsData, String>("Email");
        TableColumn<ClientsData, String> clientsDataStringAdressColumn = new TableColumn<ClientsData, String>("Address");
        TableColumn<ClientsData, String> clientsDataStringLoyaltyColumn = new TableColumn<ClientsData, String>("Loyalty");


        clientsDataStringIDColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("ID"));
        clientsDataStringFNameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("FName"));
        clientsDataStringLNameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("LName"));
        clientsDataStringPhoneColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("Phone"));
        clientsDataStringEmailColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("Mail"));
        clientsDataStringAdressColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("Address"));
        clientsDataStringLoyaltyColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("LCart"));

        clientsDataStringTableView.getColumns().addAll(clientsDataStringIDColumn, clientsDataStringFNameColumn,
                clientsDataStringLNameColumn, clientsDataStringPhoneColumn, clientsDataStringEmailColumn,
                clientsDataStringAdressColumn, clientsDataStringLoyaltyColumn);

        //Adding ChoiceBox and TextField here!
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("ID", "First Name", "Last Name", "Email", "Phone");
        choiceBox.setValue("First Name");

        TextField textField = new TextField();
        textField.setPromptText("Search here!");
        textField.setOnKeyReleased(keyEvent ->
        {
            String sql;
            switch (choiceBox.getValue())//Switch on choiceBox value
            {
                case "First Name":
                    sql = "SELECT * FROM clients WHERE FirstName LIKE '"+textField.getText()+"%'";
                    break;
                case "Last Name":
                    sql = "SELECT * FROM clients WHERE LastName LIKE '"+textField.getText()+"%'";
                    break;
                case "Email":
                    sql = "SELECT * FROM clients WHERE Email LIKE '"+textField.getText()+"%'";
                    break;
                case "Phone":
                    sql = "SELECT * FROM clients WHERE Phone LIKE '"+textField.getText()+"%'";
                    break;
                default:
                    sql = "SELECT * FROM clients WHERE ID LIKE '"+textField.getText()+"%'";
            }
            ObservableList<ClientsData> clientsData;
            clientsData = FXCollections.observableArrayList();
            try{
                Connection con = DbConnection.getConnection();
                assert con != null;
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()){
                    clientsData.add(
                            new ClientsData(
                                    rs.getString(1),
                                    rs.getString(2),
                                    rs.getString(3),
                                    rs.getString(5),
                                    rs.getString(6),
                                    rs.getString(4),
                                    rs.getString(7)
                            )
                    );
                }
                con.close();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            clientsDataStringTableView.setItems(null);
            clientsDataStringTableView.setItems(clientsData);
        });

        clientsDataStringTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if(clientsDataStringTableView.getSelectionModel().isEmpty()){
                System.out.println("select ");
            }else{
                selectedItem = clientsDataStringTableView.getSelectionModel().getSelectedItem();
                stage.close();
            }

        });

        ClientsData selectedData = clientsDataStringTableView.getSelectionModel().getSelectedItem();
        HBox hBox = new HBox(choiceBox, textField);//Add choiceBox and textField to hBox
        hBox.setAlignment(Pos.CENTER);//Center HBox
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, clientsDataStringTableView, hBox);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();


        return selectedItem;
    }
}
