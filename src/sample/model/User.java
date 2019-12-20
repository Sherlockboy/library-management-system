package sample.model;

import javafx.scene.control.CheckBox;

public class User {
    private String department;
    private String password;
    private String full_name;
    private String user_id;
    private String user_role;
    private CheckBox checkBox;



    public User() {
        this.department = "";
        this.password = "";
        this.full_name = "";
    }

    public User(String user_id, String password) {
        this.user_id = user_id;
        this.password = password;
    }

    public User(String user_id, String password, String full_name, String department, String user_role, CheckBox checkBox) {
        this.user_id = user_id;
        this.password = password;
        this.full_name = full_name;
        this.department = department;
        this.user_role = user_role;
        this.checkBox = checkBox;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public CheckBox getCheckbox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

}
