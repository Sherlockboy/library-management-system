package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DBConnection;
import sample.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddLibrarianController {
    @FXML
    private TextField user_id_field;
    @FXML
    private TextField full_name_field;
    @FXML
    private TextField password_field;
    @FXML
    private TextField department_field;
    @FXML
    public void add(ActionEvent event) throws SQLException {
        PreparedStatement statement = DBConnection.getConnection().prepareStatement("insert into users values(?,?,?,?)"); //adding user into db
        statement.setString(1, user_id_field.getText());
        statement.setString(2, password_field.getText());
        statement.setString(3, full_name_field.getText());
        statement.setString(4, department_field.getText());

        statement.executeUpdate();

        LibrarianTableController.LibrarianList.add(
                new User(user_id_field.getText(),
                        password_field.getText(),
                        full_name_field.getText(),
                        department_field.getText(),
                        "Librarian",
                        new CheckBox()));

        statement = DBConnection.getConnection().prepareStatement("insert into user_roles values(?,?)"); //adding user role into db
        statement.setString(1, user_id_field.getText());
        statement.setInt(2, 2);
        statement.execute();

        Button button = (Button)(event).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

    }
}
