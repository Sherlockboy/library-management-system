package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.database.DBConnection;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label login_info;

    public static User user = new User();
    Connection connection = null;
    public static ResultSet resultSet = null;
    PreparedStatement statement = null;
    private boolean isUser = false;
    private ActionEvent EVENT;
    public static String user_id, full_name, user_role;
    public static int num_books = 0, num_students = 0, totalBorrowedBooks = 0, myBooks = 0;

    @FXML
    public void login(ActionEvent event) throws SQLException {
        EVENT = event;
        isUser = false;
        user.setUser_id(textField.getText());
        user.setPassword(passwordField.getText());

        if(!(user.getUser_id().equals("") || user.getPassword().equals(""))){
            try {
                connection = DBConnection.getConnection();
                statement = connection.prepareStatement("select * from users");
                resultSet = statement.executeQuery();
                while (resultSet.next()){

                    if((resultSet.getString("user_id").equals(user.getUser_id())) && resultSet.getString("password").equals(user.getPassword())){
                        setUserDetails();
                        if(user_role.equals("Admin")){
                            openMainPage("../pages/MainAdminPage.fxml");
                        } else if(user_role.equals("Librarian")){
                            openMainPage("../pages/MainLibrarianPage.fxml");
                        } else if(user_role.equals("Student")){
                            openMainPage("../pages/MainStudentPage.fxml");
                        }

                    }
                }
                if(!isUser){
                    login_info.setText("User not found!");
                    login_info.setTextFill(Color.RED);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
            }
        } else {
            login_info.setText("Some fields missed!");
            login_info.setTextFill(Color.RED);
        }
    }
    public void openMainPage(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(page));
        Stage stage1 = new Stage();
        stage1.setTitle("Library Management System");
        stage1.setScene(new Scene(root, 900, 610));
        stage1.show();

        Button button = (Button)(EVENT).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        isUser = true;
    }
    public static void setUserDetails() throws SQLException {
        resultSet = DBConnection.getConnection().createStatement().executeQuery(
                "select u.user_id, u.full_name,r.role_name from users u\n" +
                        "inner join user_roles ur on ur.user_id = u.user_id\n" +
                        "inner join roles r on r.role_id = ur.role_id;");
        while (resultSet.next()){
            if(resultSet.getString("user_id").equals(user.getUser_id())){
                user_id = user.getUser_id();
                full_name = resultSet.getString("full_name");
                user_role = resultSet.getString("role_name");
            } else if(resultSet.getString("role_name").equals("Student")){
                num_students++;
            }
        }
        resultSet = DBConnection.getConnection().createStatement().executeQuery("select *from books");
        while (resultSet.next()){
            num_books++;
        }
        resultSet = DBConnection.getConnection().createStatement().executeQuery("select *from borrowed_books");
        while (resultSet.next()){
            totalBorrowedBooks++;
        }
        if(user_role.equals("Student")){
            resultSet = DBConnection.getConnection().createStatement().executeQuery("select *from borrowed_books");
            while (resultSet.next()){
                if(resultSet.getString("user_id").equals(user_id))
                myBooks++;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
