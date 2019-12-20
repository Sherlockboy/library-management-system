package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.database.DBConnection;
import sample.model.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AllBooksController implements Initializable {
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, String> col_id;
    @FXML
    private TableColumn<Book, String> col_title;
    @FXML
    private TableColumn<Book, String> col_subject;
    @FXML
    private TableColumn<Book, String> col_author;
    @FXML
    private TableColumn<Book, String> col_isbn;
    @FXML
    private TableColumn<Book, Date> col_publish_date;
    @FXML
    private TableColumn<Book, Boolean> col_status;
    @FXML
    private TableColumn<Book, CheckBox> col_box;
    @FXML
    private Button add;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private Button take;

    public static ObservableList<Book> BookList = FXCollections.observableArrayList();
    public static Book staticBook = new Book();
    public static int rowIndexBook;
    ResultSet resultSet = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshBooksTable();
    }
    @FXML
    public void refreshBooksTable(){
        BookList.removeAll(BookList);
        if(LoginController.user_role.equals("Student")){
            add.setDisable(true);
            update.setDisable(true);
            delete.setDisable(true);
        } else if(LoginController.user_role.equals("Admin")){
            take.setDisable(true);
        }
        try {
            resultSet = DBConnection.getConnection().createStatement().executeQuery("select * from books;");
            while (resultSet.next()){
                    BookList.add(new Book(
                            resultSet.getLong("book_id"),
                            resultSet.getString("title"),
                            resultSet.getString("subject"),
                            resultSet.getString("author"),
                            resultSet.getLong("isbn"),
                            resultSet.getDate("publish_date"),
                            resultSet.getBoolean("status"),
                            new CheckBox()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        col_isbn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col_publish_date.setCellValueFactory(new PropertyValueFactory<>("publish_date"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_box.setCellValueFactory(new PropertyValueFactory<>("checkbox"));

        table.setItems(BookList);
    }

    @FXML
    public void deleteSelectedRows() throws SQLException {
        ObservableList<Book> removeList = FXCollections.observableArrayList();
        for (Book book: BookList) {
            if(book.getCheckbox().isSelected()){
                removeList.add(book);
            }
        }
        for (Book book: removeList) {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("delete from books where book_id=?");
            statement.setLong(1,book.getBook_id());
            statement.executeUpdate();
        }
        BookList.removeAll(removeList);
    }

    @FXML
    public void addPageOpener(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../pages/AddBookPage.fxml"));
        Stage stage1 = new Stage();
        stage1.setTitle("Adding Book...");
        stage1.setScene(new Scene(root, 492, 534));
        stage1.show();
    }
    @FXML
    public void updatePageOpener() throws IOException {
        getSelectedRow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../pages/UpdateBookPage.fxml"));

        Stage stage1 = new Stage();
        stage1.setTitle("Updating Book...");
        stage1.setScene(new Scene(root, 492, 534));
        stage1.show();

    }
    @FXML
    public void returnSelectedBook() throws SQLException {
        if(table.getSelectionModel().getSelectedItem() != null){
            if(table.getSelectionModel().getSelectedItem().isStatus()){
                PreparedStatement statement = DBConnection.getConnection().prepareStatement("delete from borrowed_books where book_id=?");
                statement.setLong(1,table.getSelectionModel().getSelectedItem().getBook_id());
                statement.executeUpdate();

                statement = DBConnection.getConnection().prepareStatement("update books set status=? where book_id=?");
                statement.setBoolean(1,false);
                statement.setLong(2,table.getSelectionModel().getSelectedItem().getBook_id());
                statement.executeUpdate();
                BookList.remove(table.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML
    public void takeSelectedBook() throws SQLException, IOException {
        getSelectedRow();
        if(table.getSelectionModel().getSelectedItem() != null){
            if(!table.getSelectionModel().getSelectedItem().isStatus()){
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("../pages/TakeBookPage.fxml"));

                Stage stage1 = new Stage();
                stage1.setTitle("Taking Book...");
                stage1.setScene(new Scene(root, 492, 357));
                stage1.show();

                staticBook.setStatus(true);
                BookList.set(rowIndexBook, staticBook);

                PreparedStatement statement = DBConnection.getConnection().prepareStatement("update books set status=? where book_id=?");
                statement.setBoolean(1,true);
                statement.setLong(2,table.getSelectionModel().getSelectedItem().getBook_id());
                statement.executeUpdate();
            }
        }
    }

    public void getSelectedRow(){
        if(table.getSelectionModel().getSelectedItem() != null){
            staticBook =  table.getSelectionModel().getSelectedItem();
            rowIndexBook = table.getSelectionModel().getSelectedIndex();
        }
    }
}
