package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.database.DBConnection;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LibrarianTableController implements Initializable {
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> col_user_id;
    @FXML
    private TableColumn<User, String> col_full_name;
    @FXML
    private TableColumn<User, String> col_dep;
    @FXML
    private TableColumn<User, String> col_password;
    @FXML
    private TableColumn<User, String> col_role;
    @FXML
    private TableColumn<User, CheckBox> col_box;

    public static ObservableList<User> LibrarianList = FXCollections.observableArrayList();
    public static User staticLibrarian = new User(); //calling constructor for avoid null pointer exception if row is not selected
    public static int rowIndexLibrarian;
    ResultSet resultSet = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshLibrariansTable();
    }
    @FXML
    public void refreshLibrariansTable(){
        LibrarianList.removeAll(LibrarianList);

        try {
            resultSet = DBConnection.getConnection().createStatement().executeQuery(
                    "select u.user_id,u.password,u.full_name,u.department,r.role_name from users u\n" +
                            "inner join user_roles ur on ur.user_id = u.user_id\n" +
                            "inner join roles r on r.role_id = ur.role_id;");
            while (resultSet.next()){
                if(resultSet.getString("role_name").equals("Librarian")){
                    LibrarianList.add(new User(
                            resultSet.getString("user_id"),
                            resultSet.getString("password"),
                            resultSet.getString("full_name"),
                            resultSet.getString("department"),
                            resultSet.getString("role_name"),
                            new CheckBox()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        col_full_name.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        col_dep.setCellValueFactory(new PropertyValueFactory<>("department"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("user_role"));
        col_box.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        table.setItems(LibrarianList);
    }

    @FXML
    public void deleteSelectedRows() throws SQLException {
        ObservableList<User> removeList = FXCollections.observableArrayList();
        for (User user: LibrarianList) {
            if(user.getCheckbox().isSelected()){
                removeList.add(user);
            }
        }
        for (User user: removeList) {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("delete from users where user_id=?");
            statement.setString(1,user.getUser_id());
            statement.executeUpdate();
        }
        LibrarianList.removeAll(removeList);
    }

    @FXML
    public void addPageOpener(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../pages/AddLibrarianPage.fxml"));
        Stage stage1 = new Stage();
        stage1.setTitle("Adding Librarian...");
        stage1.setScene(new Scene(root, 492, 479));
        stage1.show();
    }
    @FXML
    public void updatePageOpener() throws IOException {
        getSelectedRow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../pages/UpdateLibrarianPage.fxml"));

        Stage stage1 = new Stage();
        stage1.setTitle("Updating Librarian...");
        stage1.setScene(new Scene(root, 492, 451));
        stage1.show();

    }

    public void getSelectedRow(){
        if(table.getSelectionModel().getSelectedItem() != null){
            staticLibrarian =  table.getSelectionModel().getSelectedItem();
            rowIndexLibrarian = table.getSelectionModel().getSelectedIndex();
        }
    }
}
