package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text user_info;

    @FXML
    public void loadDashboard(MouseEvent event){
        if(LoginController.user_role.equals("Admin")){
            loadUI("../pages/AdminDashboardPage.fxml");
        } else if(LoginController.user_role.equals("Librarian")){
            loadUI("../pages/LibrarianDashboardPage.fxml");
        } else if(LoginController.user_role.equals("Student")){
            loadUI("../pages/StudentDashboardPage.fxml");
        }
    }
    @FXML
    public void loadStudents(MouseEvent event){
        loadUI("../pages/StudentsTable.fxml");
    }
    @FXML
    public void loadLibrarians(MouseEvent event){
        loadUI("../pages/LibrarianTable.fxml");
    }
    @FXML
    public void loadBooks(MouseEvent event){
        loadUI("../pages/AllBooksPage.fxml");
    }
    @FXML
    public void loadBorrowedBooks(MouseEvent event){
        if(LoginController.user_role.equals("Librarian")){
            loadUI("../pages/AllBorrowedBooks.fxml");
        } else if(LoginController.user_role.equals("Student")){
            loadUI("../pages/MyBooks.fxml");
        }
    }
    @FXML
    public void loadSettings(MouseEvent event){
        loadUI("../pages/SettingsPage.fxml");
    }
    @FXML
    public void logOut(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../pages/LoginPage.fxml"));
        Stage stage1 = new Stage();
        stage1.setTitle("Login Page");
        stage1.setScene(new Scene(root, 670, 420));
        stage1.show();

        ImageView logout = (ImageView) (event).getSource();
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();

        LoginController.num_students = 0;
        LoginController.num_books = 0;
        LoginController.myBooks = 0;
        LoginController.totalBorrowedBooks = 0;
    }

    private void loadUI(String page){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(page));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(parent);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(LoginController.user_role.equals("Admin")){
            loadUI("../pages/AdminDashboardPage.fxml");
        } else if(LoginController.user_role.equals("Librarian")){
            loadUI("../pages/LibrarianDashboardPage.fxml");
        } else if(LoginController.user_role.equals("Student")){
            loadUI("../pages/StudentDashboardPage.fxml");
        }
        user_info.setText(LoginController.full_name);
    }

}
