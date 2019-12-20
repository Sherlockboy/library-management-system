package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DBConnection;
import sample.model.Book;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookController {
    @FXML
    private TextField book_id_field;
    @FXML
    private TextField title_field;
    @FXML
    private TextField subject_field;
    @FXML
    private TextField author_field;
    @FXML
    private TextField isbn_field;
    @FXML
    private DatePicker publish_date_field;
    @FXML
    public void add(ActionEvent event) throws SQLException {
        PreparedStatement statement = DBConnection.getConnection().prepareStatement("insert into books values(?,?,?,?,?,?,?)"); //adding book into db
        statement.setLong(1, Long.parseLong(book_id_field.getText()));
        statement.setString(2, title_field.getText());
        statement.setString(3, subject_field.getText());
        statement.setString(4, author_field.getText());
        statement.setLong(5, Long.parseLong(isbn_field.getText()));
        statement.setDate(6, Date.valueOf(publish_date_field.getValue()));
        statement.setBoolean(7, false);

        statement.executeUpdate();

        AllBooksController.BookList.add(
                new Book(Long.parseLong(book_id_field.getText()),
                        title_field.getText(),
                        subject_field.getText(),
                        author_field.getText(),
                        Long.parseLong(isbn_field.getText()),
                        Date.valueOf(publish_date_field.getValue()),
                        false,
                        new CheckBox()));

        Button button = (Button)(event).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

    }
}
