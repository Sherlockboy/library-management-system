package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DBConnection;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TakeBookController implements Initializable {

    @FXML
    private DatePicker borrowed_date, return_date;
    @FXML
    private TextField student_id;
    @FXML
    public void takeBook(ActionEvent event) throws SQLException {
        if(LoginController.user_role.equals("Admin") || LoginController.user_role.equals("Librarian")){
            setBookTo(student_id.getText());
        } else {
            setBookTo(LoginController.user_id);
        }
        Button button = (Button)(event).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    private void setBookTo(String student_id) throws SQLException {
        PreparedStatement statement = DBConnection.getConnection().prepareStatement("insert into borrowed_books values(?,?)");
        statement.setString(1, student_id);
        statement.setLong(2, AllBooksController.staticBook.getBook_id());
        statement.execute();

        statement = DBConnection.getConnection().prepareStatement("update books set borrowed_date=?, return_date=? where book_id=?");
        statement.setDate(1, Date.valueOf(borrowed_date.getValue()));
        statement.setDate(2, Date.valueOf(return_date.getValue()));
        statement.setLong(3, AllBooksController.staticBook.getBook_id());
        statement.executeUpdate();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(LoginController.user_role.equals("Student")){
            student_id.setDisable(true);
        }
    }
}
