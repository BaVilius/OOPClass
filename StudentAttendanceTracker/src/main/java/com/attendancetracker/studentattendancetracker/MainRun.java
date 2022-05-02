/// Vilius Bakutis 5 grupe ///


package com.attendancetracker.studentattendancetracker;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainRun extends Application  {
    public Parent root;

    public void start(Stage stage) throws Exception{

        root = FXMLLoader.load(getClass().getResource("MainTable.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Stage");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

