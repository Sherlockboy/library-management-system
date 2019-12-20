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

public class UpdateLibrarianController {
    @FXML
    private TextField full_name_field;
    @FXML
    private TextField password_field;
    @FXML
    private TextField department_field;
    @FXML
    public void update(ActionEvent event) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update users set full_name=?, password=?, department=? where user_id=?;");
        preparedStatement.setString(1, full_name_field.getText());
        preparedStatement.setString(2, password_field.getText());
        preparedStatement.setString(3, department_field.getText());
        preparedStatement.setString(4, LibrarianTableController.staticLibrarian.getUser_id());
        preparedStatement.executeUpdate();

        LibrarianTableController.staticLibrarian.setFull_name(full_name_field.getText());
        LibrarianTableController.staticLibrarian.setPassword(password_field.getText());
        LibrarianTableController.staticLibrarian.setDepartment(department_field.getText());
        LibrarianTableController.staticLibrarian.setCheckBox(new CheckBox());
        LibrarianTableController.LibrarianList.set(LibrarianTableController.rowIndexLibrarian, LibrarianTableController.staticLibrarian);

        Button button = (Button)(event).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    public void setObj(User user){
        full_name_field.setText(user.getFull_name());
        password_field.setText(user.getPassword());
        department_field.setText(user.getDepartment());
    }

    public void initialize(){
        setObj(LibrarianTableController.staticLibrarian);
    }
}
