package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DashboardPageInitializer implements Initializable {
    @FXML
    private Label numberOfStudents;
    @FXML
    private Label numberOfBooks;
    @FXML
    private Label numberOfMyBooks;
    @FXML
    private Label numberOfBorrowedBooks;
    @FXML
    private Label todayDate;

    public String now(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(cal.getTime());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginController.num_students = 0;
        LoginController.num_books = 0;
        LoginController.myBooks = 0;
        LoginController.totalBorrowedBooks = 0;
        try {
            LoginController.setUserDetails();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(LoginController.user_role.equals("Admin")){
            numberOfStudents.setText(Integer.toString(LoginController.num_students));
            numberOfBooks.setText(Integer.toString(LoginController.num_books));
        } else if(LoginController.user_role.equals("Librarian")){
            numberOfStudents.setText(Integer.toString(LoginController.num_students));
            numberOfBooks.setText(Integer.toString(LoginController.num_books));
            numberOfBorrowedBooks.setText(Integer.toString(LoginController.totalBorrowedBooks));
        } else if(LoginController.user_role.equals("Student")){
            numberOfBooks.setText(Integer.toString(LoginController.num_books));
            numberOfMyBooks.setText(Integer.toString(LoginController.myBooks));
        }
        todayDate.setText(now());
    }
}
