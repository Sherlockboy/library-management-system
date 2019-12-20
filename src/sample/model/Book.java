package sample.model;

import javafx.scene.control.CheckBox;

import java.sql.Date;

public class Book {
    private Long book_id;
    private String title;
    private String subject;
    private String author;
    private Long ISBN;
    private Date publish_date;
    private Date borrowed_date;
    private Date return_date;
    private boolean status = false; // is borrowed
    private String borrowed_by;
    private CheckBox checkBox;

    public Book() {

    }

    public Book(Long book_id, String title, String subject, String author, Long ISBN, Date publish_date, boolean status, CheckBox checkBox) {
        this.book_id = book_id;
        this.title = title;
        this.subject = subject;
        this.author = author;
        this.ISBN = ISBN;
        this.publish_date = publish_date;
        this.status = status;
        this.checkBox = checkBox;
    }

    public Book(Long book_id, String title, Long ISBN, Date borrowed_date, Date return_date){
        this.book_id = book_id;
        this.title = title;
        this.ISBN = ISBN;
        this.borrowed_date = borrowed_date;
        this.return_date = return_date;
    }

    public Book(Long book_id, String title, Long ISBN, String borrowed_by, Date borrowed_date, Date return_date){
        this.book_id = book_id;
        this.title = title;
        this.ISBN = ISBN;
        this.borrowed_by = borrowed_by;
        this.borrowed_date = borrowed_date;
        this.return_date = return_date;
    }
    public Book(Long book_id, String title, String subject, String author, Long ISBN, Date publish_date, String borrowed_by, CheckBox checkBox) {
        this.book_id = book_id;
        this.title = title;
        this.subject = subject;
        this.author = author;
        this.ISBN = ISBN;
        this.publish_date = publish_date;
        this.borrowed_by = borrowed_by;
        this.checkBox = checkBox;
    }

    public Date getBorrowed_date() {
        return borrowed_date;
    }

    public void setBorrowed_date(Date borrowed_date) {
        this.borrowed_date = borrowed_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public String getBorrowed_by() {
        return borrowed_by;
    }

    public void setBorrowed_by(String borrowed_by) {
        this.borrowed_by = borrowed_by;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public CheckBox getCheckbox() {
        return checkBox;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getISBN() {
        return ISBN;
    }

    public void setISBN(Long ISBN) {
        this.ISBN = ISBN;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

