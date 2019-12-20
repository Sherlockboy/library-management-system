package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("pages/LoginPage.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 670, 420));
        primaryStage.show();
    }

//    public static void switchUI(Stage stage, String page, String title, int width, int height){
//        Parent root = FXMLLoader.load(getClass().getResource(page));
//        stage.setTitle(title);
//        stage.setScene(new Scene(root, width, height));
//        stage.show();
//    }
    public static void main(String[] args) {
        launch(args);
    }
}
