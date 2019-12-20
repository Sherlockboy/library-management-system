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

public class UpdateBookController {
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
    public void update(ActionEvent event) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement("update books set title=?, subject=?, author=?, isbn=?, publish_date=? where book_id=?;");
        preparedStatement.setString(1, title_field.getText());
        preparedStatement.setString(2, subject_field.getText());
        preparedStatement.setString(3, author_field.getText());
        preparedStatement.setLong(4, Long.parseLong(isbn_field.getText()));
        preparedStatement.setDate(5, Date.valueOf(publish_date_field.getValue()));
        preparedStatement.setLong(6, AllBooksController.staticBook.getBook_id());
        preparedStatement.executeUpdate();

        AllBooksController.staticBook.setTitle(title_field.getText());
        AllBooksController.staticBook.setSubject(subject_field.getText());
        AllBooksController.staticBook.setAuthor(author_field.getText());
        AllBooksController.staticBook.setISBN(Long.parseLong(isbn_field.getText()));
        AllBooksController.staticBook.setPublish_date(Date.valueOf(publish_date_field.getValue()));
        AllBooksController.staticBook.setCheckBox(new CheckBox());
        AllBooksController.BookList.set(AllBooksController.rowIndexBook, AllBooksController.staticBook);

        Button button = (Button)(event).getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    public void setObj(Book book){
        title_field.setText(book.getTitle());
        subject_field.setText(book.getSubject());
        author_field.setText(book.getAuthor());
        isbn_field.setText(Long.toString(book.getISBN()));
    }

    public void initialize(){
        setObj(AllBooksController.staticBook);
    }
}
