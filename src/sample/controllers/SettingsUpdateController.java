package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DBConnection;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingsUpdateController implements Initializable {
    @FXML
    private TextField full_name_field;
    @FXML
    private TextField password_field;
    @FXML
    private TextField department_field;
    public void update(ActionEvent event) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update users set full_name=?, password=?, department=? where user_id=?;");
        preparedStatement.setString(1, full_name_field.getText());
        preparedStatement.setString(2, password_field.getText());
        preparedStatement.setString(3, department_field.getText());
        preparedStatement.setString(4, LoginController.user_id);
        preparedStatement.executeUpdate();

        Button button = (Button)(event).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    private void setDetails() throws SQLException {
        PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                "select full_name, department, password from users where user_id=?");
        statement.setString(1, LoginController.user_id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            full_name_field.setText(resultSet.getString("full_name"));
            password_field.setText(resultSet.getString("password"));
            department_field.setText(resultSet.getString("department"));
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
