package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.database.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private Label user_id_label;
    @FXML
    private Label full_name_label;
    @FXML
    private Label department_label;
    @FXML
    private Label password_label;
    @FXML
    private Label role_label;

    @FXML
    public void updatePageOpener() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../pages/SettingsPageUpdate.fxml"));

        Stage stage1 = new Stage();
        stage1.setTitle("Updating User Details...");
        stage1.setScene(new Scene(root, 492, 451));
        stage1.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "select full_name, department, password from users where user_id=?");
            statement.setString(1, LoginController.user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                user_id_label.setText(LoginController.user_id);
                full_name_label.setText(resultSet.getString("full_name"));
                department_label.setText(resultSet.getString("department"));
                password_label.setText(resultSet.getString("password"));
                role_label.setText(LoginController.user_role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
