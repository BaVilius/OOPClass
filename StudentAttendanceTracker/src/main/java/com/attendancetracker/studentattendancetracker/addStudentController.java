package com.attendancetracker.studentattendancetracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class addStudentController implements actions{
    @FXML
    TextField addNameField;
    @FXML
    TextField addLNameField;
    @FXML
    TextField addGNumberField;
    @FXML
    Spinner addDateField;

    FileWriter myWriter;

    private static Stage stage;


    public void addClicked(ActionEvent event){
        Save();
        stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("Add-Dates.fxml").openStream());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Stage");
    }

    public void addDateClicked(ActionEvent event){

        int dateToAdd = (int)addDateField.getValue();
        try {
            myWriter = new FileWriter("studentsData.txt", true);
            myWriter.append(dateToAdd + " ");
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void Save() {
        String name = addNameField.getText();
        String lName = addLNameField.getText();
        int gNumber = Integer.parseInt(addGNumberField.getText());

        try {
            myWriter = new FileWriter("studentsData.txt", true);
            myWriter.append(name + " " + lName + " " + gNumber +" \n");
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void closeButton(ActionEvent event){
        try {
            myWriter = new FileWriter("studentsData.txt", true);
            myWriter.append("\n");
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.close();
    }

    @Override
    public void Load() {

    }
}
