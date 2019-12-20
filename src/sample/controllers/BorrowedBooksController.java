package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.database.DBConnection;
import sample.model.Book;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BorrowedBooksController implements Initializable {
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, String> col_id;
    @FXML
    private TableColumn<Book, String> col_title;
    @FXML
    private TableColumn<Book, String> col_isbn;
    @FXML
    private TableColumn<Book, String> col_borrowed_by;
    @FXML
    private TableColumn<Book, Date> col_borrowed_date;
    @FXML
    private TableColumn<Book, Date> col_return_date;

    private ObservableList<Book> BorrowedBookList = FXCollections.observableArrayList();
    ResultSet resultSet = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (LoginController.user_role.equals("Librarian")) {
            refreshAllBorrowedBooks();
        } else if(LoginController.user_role.equals("Student")){
            refreshMyBooks();
        }
    }

    public void refreshAllBorrowedBooks() {
        BorrowedBookList.removeAll(BorrowedBookList);
        try {
            resultSet = DBConnection.getConnection().createStatement().executeQuery(
                    "select b.book_id, b.title, b.isbn, u.user_id, b.borrowed_date, b.return_date from books b\n" +
                            "inner join borrowed_books bb on bb.book_id = b.book_id\n" +
                            "inner join users u on bb.user_id = u.user_id;");
            while (resultSet.next()) {
                BorrowedBookList.add(new Book(
                        resultSet.getLong("book_id"),
                        resultSet.getString("title"),
                        resultSet.getLong("isbn"),
                        resultSet.getString("user_id"),
                        resultSet.getDate("borrowed_date"),
                        resultSet.getDate("return_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_borrowed_by.setCellValueFactory(new PropertyValueFactory<>("borrowed_by"));
        col_borrowed_date.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        col_return_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));

        table.setItems(BorrowedBookList);
    }

    public void refreshMyBooks() {
        BorrowedBookList.removeAll(BorrowedBookList);
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement(
                    "select b.book_id, b.title, b.isbn, b.borrowed_date, b.return_date from books b\n" +
                    "inner join borrowed_books bb on bb.book_id = b.book_id\n" +
                    "inner join users u on bb.user_id = u.user_id and u.user_id=?;");
            statement.setString(1, LoginController.user_id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BorrowedBookList.add(new Book(
                        resultSet.getLong("book_id"),
                        resultSet.getString("title"),
                        resultSet.getLong("isbn"),
                        resultSet.getDate("borrowed_date"),
                        resultSet.getDate("return_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_borrowed_date.setCellValueFactory(new PropertyValueFactory<>("borrowed_date"));
        col_return_date.setCellValueFactory(new PropertyValueFactory<>("return_date"));

        table.setItems(BorrowedBookList);
    }

    @FXML
    public void returnSelectedBook() throws SQLException {
        if(table.getSelectionModel().getSelectedItem() != null){
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("delete from borrowed_books where book_id=?");
            statement.setLong(1, table.getSelectionModel().getSelectedItem().getBook_id());
            statement.executeUpdate();

            statement = DBConnection.getConnection().prepareStatement("update books set status=?, borrowed_date=?, return_date=? where book_id=?");
            statement.setBoolean(1,false);
            statement.setDate(2, null);
            statement.setDate(3, null);
            statement.setLong(4,table.getSelectionModel().getSelectedItem().getBook_id());
            statement.executeUpdate();

            BorrowedBookList.remove(table.getSelectionModel().getSelectedItem());
        }
    }
}
