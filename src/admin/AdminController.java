package admin;

import com.itextpdf.text.DocumentException;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminController implements Initializable {
    @FXML
    public BorderPane borderpane;

    @FXML
    private Label homeDate;
    @FXML
    private Label homeJewelsHands;
    @FXML
    private Label goldRate;
    @FXML
    private Label AluRate;
    @FXML
    private Label homeTotalClients;
    @FXML
    private Label homeTotalSells;
    @FXML
    private Label homeTotalAmount;
    @FXML
    private Label homeTotalJewels;
    @FXML
    private Label homeJewelsHead;
    @FXML
    private Label homeJewelsNeck;
    @FXML
    private Label homeJewelsArms;
    @FXML
    private Label homeJewelsBody;
    @FXML
    private Label homeJewelsFeet;
    @FXML
    private Label homeJewelsSp;

    @FXML
    private TextField jewelId;
    @FXML
    private Label jewelErr;
    @FXML
    private TextField jewelPrice;
    @FXML
    public ComboBox<String> jewelType;
    @FXML
    private ComboBox<String> jewelName;

    @FXML
    private TableView<JewelsData> jewelsDataTableView;
    @FXML
    private TableColumn<JewelsData, String> jewelsDataStringIDColumn;
    @FXML
    private TableColumn<JewelsData, String> jewelsDataStringTypeColumn;
    @FXML
    private TableColumn<JewelsData, String> jewelsDataStringNameColumn;
    @FXML
    private TableColumn<JewelsData, String> jewelsDataStringPriceColumn;

    @FXML
    private Label clientErr;
    @FXML
    private TextField clientFName;
    @FXML
    private TextField clientLName;
    @FXML
    private TextField clientID;
    @FXML
    private TextField clientPhone;
    @FXML
    private TextField clientEmail;
    @FXML
    private TextField clientAddress;
    @FXML
    private CheckBox clientLCard;

    @FXML
    private TableView<ClientsData> clientsDataStringTableView;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringIDColumn;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringFNameColumn;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringLNameColumn;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringPhoneColumn;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringEmailColumn;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringAdressColumn;
    @FXML
    private TableColumn<ClientsData, String> clientsDataStringLoyaltyColumn;

    @FXML
    private TextField workerName;
    @FXML
    private TextField workerPassword;
    @FXML
    private ComboBox<String> workerDiv;

    @FXML
    private TableView<WorkerData> workerDataTableView;
    @FXML
    private TableColumn<WorkerData, String> workerDataStringNameColumn;
    @FXML
    private TableColumn<WorkerData, String> workerDataStringPasswordColumn;
    @FXML
    private TableColumn<WorkerData, String> workerDataStringDivColumn;

    @FXML
    private TextArea invoiceTo;
    @FXML
    private Label invoiceDate;
    @FXML
    private Label invoiceSubTotal;
    @FXML
    private Label invoiceTax;
    @FXML
    private Label invoiceTotal;
    @FXML
    private Label invoiceErr;

    @FXML
    private TableView<InvoiceData> invoiceDataTableView;
    @FXML
    private TableColumn<InvoiceData, String> invoiceDataStringIDColumn;
    @FXML
    private TableColumn<InvoiceData, String> invoiceDataStringDescriptionColumn;
    @FXML
    private TableColumn<InvoiceData, String> invoiceDataStringQuantityColumn;
    @FXML
    private TableColumn<InvoiceData, String> invoiceDataStringAmountColumn;

    @FXML
    private TextField serviceTo;
    @FXML
    private TextField serviceSubject;
    @FXML
    private TextArea ServiceContent;
    @FXML
    private Label serviceErr;

    private ObservableList<JewelsData> data;
    private ObservableList<ClientsData> clientsData;
    private ObservableList<WorkerData> workerData;
    private CreatePdf invoiceFile;
    private Boolean isDataLoaded = false;

    public void initialize(URL url, ResourceBundle rb){
        new DbConnection();
    }

    @FXML
    public void homeButton(ActionEvent event){
        this.load("home");
        this.isDataLoaded = false;
    }
    @FXML
    public void jewelsButton(ActionEvent event){
        this.load("jewels");
        this.isDataLoaded = false;
    }
    @FXML
    public void clientsButton(ActionEvent actionEvent) {
        this.load("clients");
        this.isDataLoaded = false;
    }
    @FXML
    public void workerButton(ActionEvent actionEvent) {
        this.load("worker");
        this.isDataLoaded = false;
    }
    @FXML
    public void invoicesButton(ActionEvent actionEvent) {
        this.load("invoice");
        this.isDataLoaded = false;
    }
    @FXML
    public void servicesButton(ActionEvent actionEvent) {
        this.load("services");
        this.isDataLoaded = false;
    }
    @FXML
    public void settingsButton(ActionEvent actionEvent) {
        this.load("settings");
        this.isDataLoaded = false;
    }

    public void jewelModify(ActionEvent actionEvent) {
        String sqlRemove = "UPDATE jewels SET Type=?, Name=?, Price=? WHERE ID=?";

        if(jewelsDataTableView.getSelectionModel().isEmpty()){
            this.jewelErr.setText("Select Item In Table!");
            return;
        }
        if(isNumber(this.jewelPrice.getText())){
            this.jewelErr.setText("ID/Price Numeric Only!");
        }
        if(this.jewelPrice.getText().trim().isEmpty() ||
                this.jewelType.getSelectionModel().isEmpty() || this.jewelName.getSelectionModel().isEmpty()){
            this.jewelErr.setText("Insert Data Before Modify!");
            return;
        }
        JewelsData selectedData = jewelsDataTableView.getSelectionModel().getSelectedItem();
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlRemove);

            stmt.setString(3, this.jewelPrice.getText());
            stmt.setString(1, this.jewelType.getSelectionModel().getSelectedItem());
            stmt.setString(2, this.jewelName.getSelectionModel().getSelectedItem());
            stmt.setString(4, selectedData.getID());

            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadData();
    }

    public void jewelRemove(ActionEvent actionEvent) {
        String sqlRemove = "DELETE FROM jewels WHERE ID=?";

        if(jewelsDataTableView.getSelectionModel().isEmpty()){
            this.jewelErr.setText("Select Item In Table!");
            return;
        }
        JewelsData selectedData = jewelsDataTableView.getSelectionModel().getSelectedItem();
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlRemove);

            stmt.setString(1, selectedData.getID());

            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadData();
    }

    public void jewelAdd(ActionEvent actionEvent) {
        String sqlInsert = "INSERT INTO jewels(ID,Type,Name,Price) VALUES (?,?,?,?)";

        if(!isNumber(this.jewelId.getText()) || !isNumber(this.jewelPrice.getText())){
            this.jewelErr.setText("ID/Price Numeric Only!");
        }
        if(this.jewelId.getText().trim().isEmpty() || this.jewelPrice.getText().trim().isEmpty() ||
                this.jewelType.getSelectionModel().isEmpty() || this.jewelName.getSelectionModel().isEmpty()){
            this.jewelErr.setText("Load & Insert Data Before Add!");
            return;
        }
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlInsert);

            stmt.setString(1, this.jewelId.getText());
            stmt.setString(2, this.jewelType.getSelectionModel().getSelectedItem());
            stmt.setString(3, this.jewelName.getSelectionModel().getSelectedItem());
            stmt.setString(4, this.jewelPrice.getText());

            stmt.execute();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.loadData();
    }

    public void jewelLoad(ActionEvent actionEvent) {
        if(!isDataLoaded)this.optionJewelsLoad();
        this.loadData();
    }

    public void jewelClear(ActionEvent actionEvent) {
        this.jewelPrice.clear();
        this.jewelPrice.setPromptText("Price");
        this.jewelId.clear();
        this.jewelId.setPromptText("ID");
    }

    public void optionJewelsLoad(){
        isDataLoaded = true;
        this.jewelType.getItems().addAll("Hair/Head","Neck","Arms","Hands","Body", "Feet", "Special Item");
        this.jewelType.valueProperty().addListener((v, oldValue, newValue) ->
                {
                    switch (newValue){
                        case "Arms" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Bangle", "Bracelet", "Armlet");
                            break;
                        case "Hands" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Class ring", "Engagement ring", "Slave bracelet");
                            break;
                        case "Body" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Belly chain", "Chatelaine", "Brooch");
                            break;
                        case "Feet" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Toe ring", "Anklet");
                            break;
                        case "Special Item" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Medical alert jewelry");
                            break;
                        case "Neck" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Bolo tie", "Carcanet", "Choker");
                            break;
                        case "Hair/Head" :
                            this.jewelName.getItems().clear();
                            this.jewelName.getItems().addAll("Crown", "Earrings", "Fascinator");
                            break;
                        default:
                            break;
                    }
                }
        );
    }

    public void loadData(){
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            this.data = FXCollections.observableArrayList();
            String sql = "SELECT * FROM jewels";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                this.data.add(
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

        this.jewelsDataStringIDColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("ID"));
        this.jewelsDataStringTypeColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("Type"));
        this.jewelsDataStringNameColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("Name"));
        this.jewelsDataStringPriceColumn.setCellValueFactory(new PropertyValueFactory<JewelsData, String>("Price"));

        this.jewelsDataTableView.setItems(null);
        this.jewelsDataTableView.setItems(this.data);
    }

    public void load(String name){
        Pane root;
        try{
            root = FXMLLoader.load(getClass().getResource("/admin/"+name+".fxml"));
            this.borderpane.setCenter(root);
        }catch (IOException ex){
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean isNumber(String val){
        try {
            Double.parseDouble(val);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void LoadClientsData(){
        this.clientsData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM clients";
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                this.clientsData.add(
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

        this.clientsDataStringIDColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("ID"));
        this.clientsDataStringFNameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("FName"));
        this.clientsDataStringLNameColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("LName"));
        this.clientsDataStringPhoneColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("Phone"));
        this.clientsDataStringEmailColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("Mail"));
        this.clientsDataStringAdressColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("Address"));
        this.clientsDataStringLoyaltyColumn.setCellValueFactory(new PropertyValueFactory<ClientsData, String>("LCart"));

        this.clientsDataStringTableView.setItems(null);
        this.clientsDataStringTableView.setItems(this.clientsData);
    }

    public void clientAdd(ActionEvent actionEvent) {
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            String sqlInsert = "INSERT INTO clients(ID,FirstName,LastName,Email,Phone,Address,Loyalty) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sqlInsert);

            stmt.setString(1, this.clientID.getText());
            stmt.setString(2, this.clientFName.getText());
            stmt.setString(3, this.clientLName.getText());
            stmt.setString(4, this.clientEmail.getText());
            stmt.setString(5, this.clientPhone.getText());
            stmt.setString(6, this.clientAddress.getText());
            if(this.clientLCard.isSelected()){
                stmt.setString(7, "Yes");
            }else{
                stmt.setString(7, "No");
            }

            stmt.execute();
            con.close();
            this.LoadClientsData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clientEdit(ActionEvent actionEvent) {
        String sqlRemove = "UPDATE clients SET FirstName=?, LastName=?, Email=?, Phone=?, Address=?, Loyalty=? WHERE ID=?";
        ClientsData selectedData = clientsDataStringTableView.getSelectionModel().getSelectedItem();
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlRemove);

            stmt.setString(7, this.clientID.getText());
            stmt.setString(1, this.clientFName.getText());
            stmt.setString(2, this.clientLName.getText());
            stmt.setString(3, this.clientEmail.getText());
            stmt.setString(4, this.clientPhone.getText());
            stmt.setString(5, this.clientAddress.getText());
            if(this.clientLCard.isSelected()){
                stmt.setString(6, "Yes");
            }else{
                stmt.setString(6, "No");
            }

            stmt.executeUpdate();
            con.close();
            this.LoadClientsData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clientRemove(ActionEvent actionEvent) {
        String sqlRemove = "DELETE FROM clients WHERE ID=?";

        if(clientsDataStringTableView.getSelectionModel().isEmpty()){
            this.clientErr.setText("Select Item In Table!");
            return;
        }
        ClientsData selectedData = clientsDataStringTableView.getSelectionModel().getSelectedItem();
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlRemove);

            stmt.setString(1, selectedData.getID());

            stmt.executeUpdate();
            con.close();
            this.LoadClientsData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clientClearForm(ActionEvent actionEvent) {
        clientFName.clear();
        clientErr.setText("");
        clientAddress.clear();
        clientLName.clear();
        clientPhone.clear();
        clientEmail.clear();
        clientID.clear();
    }

    public void clientLoad(ActionEvent actionEvent) {
        this.LoadClientsData();
    }

    public void workerAdd(ActionEvent actionEvent) {
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            String sqlInsert = "INSERT INTO login(user,pass,div) VALUES (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sqlInsert);

            stmt.setString(1, this.workerName.getText());
            stmt.setString(2, this.workerPassword.getText());
            stmt.setString(3, this.workerDiv.getSelectionModel().getSelectedItem());

            stmt.execute();
            con.close();
            this.LoadWorkerData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void workerRemove(ActionEvent actionEvent) {
        String sqlRemove = "DELETE FROM login WHERE user=?";
/*
        if(workerDataTableView.getSelectionModel().isEmpty()){
            this.clientErr.setText("Select Item In Table!");
            return;
        }

 */
        WorkerData selectedData = workerDataTableView.getSelectionModel().getSelectedItem();
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            PreparedStatement stmt = con.prepareStatement(sqlRemove);

            stmt.setString(1, selectedData.getUserName());

            stmt.executeUpdate();
            con.close();
            this.LoadWorkerData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void workerLoad(ActionEvent actionEvent) {
        this.LoadWorkerData();
    }

    public void LoadWorkerData(){
        this.optionWorkerDiv();
        this.workerData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM login";
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()){
                this.workerData.add(
                        new WorkerData(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3)
                        )
                );
            }
            con.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        this.workerDataStringNameColumn.setCellValueFactory(new PropertyValueFactory<WorkerData, String>("UserName"));
        this.workerDataStringPasswordColumn.setCellValueFactory(new PropertyValueFactory<WorkerData, String>("Password"));
        this.workerDataStringDivColumn.setCellValueFactory(new PropertyValueFactory<WorkerData, String>("Div"));

        this.workerDataTableView.setItems(null);
        this.workerDataTableView.setItems(this.workerData);
    }

    public void optionWorkerDiv(){
        if(!isDataLoaded){
            this.workerDiv.getItems().addAll("Admin", "Worker");
        }
    }

    public void searchClient(ActionEvent actionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        invoiceDate.setText(dateFormat.format(date));
        ClientsData selectedData = SearchClient.display();
        String[] values = selectedData.getAddress().split(",");
        invoiceTo.setText(selectedData.getFName()+" "+selectedData.getLName()+"\n"+values[0]+"\n"+values[1]);
    }

    public void SearchItems(ActionEvent actionEvent) {
        JewelsData selectedData = SearchItem.display();
        invoiceDataStringIDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        invoiceDataStringDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        invoiceDataStringQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantitiy"));
        invoiceDataStringAmountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        invoiceDataTableView.getItems().add(new InvoiceData(selectedData.getID(), selectedData.getType()+" - "+selectedData.getName(),"1", selectedData.getPrice()));
        invoiceFile.addCell(selectedData.getID(),selectedData.getType()+" - "+selectedData.getName(),"1", selectedData.getPrice());
        this.updateInvoiceAmounts(selectedData.getPrice(), true);
    }

    public void invoiceRemove(ActionEvent actionEvent) {
        InvoiceData selectedItem = invoiceDataTableView.getSelectionModel().getSelectedItem();
        this.updateInvoiceAmounts(selectedItem.getAmount(), false);
        invoiceDataTableView.getItems().remove(selectedItem);
    }

    public void newInvoice(ActionEvent actionEvent) throws FileNotFoundException, DocumentException {
        invoiceSubTotal.setText("0");
        invoiceTax.setText("0");
        invoiceTo.setText("");
        invoiceTotal.setText("0");
        invoiceDataTableView.getItems().clear();
        invoiceFile = new CreatePdf("src/pdf/invoice.pdf");
    }

    public void updateInvoiceAmounts(String value, boolean status){
        if(status){
            invoiceSubTotal.setText(Integer.toString(Integer.parseInt(value)+Integer.parseInt(invoiceSubTotal.getText())));
        }else{
            invoiceSubTotal.setText(Integer.toString(Integer.parseInt(value)-Integer.parseInt(invoiceSubTotal.getText())));
        }
        invoiceTax.setText(Integer.toString(Integer.parseInt(invoiceSubTotal.getText())*20/100));
        invoiceTotal.setText(Integer.toString(Integer.parseInt(invoiceSubTotal.getText())+Integer.parseInt(invoiceTax.getText())));
    }

    public String fetchData(String url){
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();
            return response.toString().split(",")[1].split("\"")[0]+"€";
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void homeRefresh(ActionEvent actionEvent) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.homeDate.setText(dateFormat.format(date));
        this.homeTotalSells.setText(this.fetchSettingsData("Sells"));
        this.homeTotalAmount.setText(this.fetchSettingsData("Amount")+"€");
        this.fetchClientsCount();
        this.goldRate.setText(this.fetchData("https://data-asg.goldprice.org/GetData/EUR-XAU/1"));
        this.AluRate.setText(this.fetchData("https://data-asg.goldprice.org/GetData/EUR-XAG/1"));
    }

    public String fetchSettingsData(String section){
        String sql = "SELECT "+section+" FROM settings";
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(sql);
            String val =  rs.getString(1);
            con.close();
            return val;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void fetchClientsCount(){
        String head = "SELECT count(*) FROM jewels WHERE Type LIKE 'Hair%'";
        String Neck = "SELECT count(*) FROM jewels WHERE Type LIKE 'Neck'";
        String Arms = "SELECT count(*) FROM jewels WHERE Type LIKE 'Arms'";
        String Hands = "SELECT count(*) FROM jewels WHERE Type LIKE 'Hands'";
        String body = "SELECT count(*) FROM jewels WHERE Type LIKE 'Body'";
        String feet = "SELECT count(*) FROM jewels WHERE Type LIKE 'Feet'";
        String sp = "SELECT count(*) FROM jewels WHERE Type LIKE 'Special%'";
        String client = "SELECT count(*) FROM clients";
        int total = 0;
        try{
            Connection con = DbConnection.getConnection();
            assert con != null;
            ResultSet rs = con.createStatement().executeQuery(client);
            this.homeTotalClients.setText(Integer.toString(rs.getInt(1)));
            rs = con.createStatement().executeQuery(head);
            int v = rs.getInt(1);
            total+=v;
            this.homeJewelsHead.setText(Integer.toString(v));
            rs = con.createStatement().executeQuery(Neck);
            v = rs.getInt(1);
            total+=v;
            this.homeJewelsNeck.setText(Integer.toString(rs.getInt(1)));
            rs = con.createStatement().executeQuery(Arms);
            v = rs.getInt(1);
            total+=v;
            this.homeJewelsArms.setText(Integer.toString(rs.getInt(1)));
            rs = con.createStatement().executeQuery(body);
            v = rs.getInt(1);
            total+=v;
            this.homeJewelsBody.setText(Integer.toString(rs.getInt(1)));
            rs = con.createStatement().executeQuery(feet);
            v = rs.getInt(1);
            total+=v;
            this.homeJewelsFeet.setText(Integer.toString(rs.getInt(1)));
            rs = con.createStatement().executeQuery(sp);
            v = rs.getInt(1);
            total+=v;
            this.homeJewelsSp.setText(Integer.toString(rs.getInt(1)));
            rs = con.createStatement().executeQuery(Hands);
            v = rs.getInt(1);
            total+=v;
            this.homeJewelsHands.setText(Integer.toString(rs.getInt(1)));
            this.homeTotalJewels.setText(Integer.toString(total));
            con.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void invoiceSave(ActionEvent actionEvent) throws DocumentException {
        invoiceFile.finishCreating();
        invoiceErr.setText("Invoice is Saved Successfully.");
    }

    public void invoiceOpen(ActionEvent actionEvent) {
        if (Desktop.isDesktopSupported()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(new File(invoiceFile.getFile()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void serviceSendButton(ActionEvent actionEvent) throws MessagingException {
        new MailServices(serviceTo.getText(), "shanth936@gmail.com", "pirasanth123+", serviceSubject.getText(), ServiceContent.getText());
        serviceErr.setText("Mail Send!");
    }
}
