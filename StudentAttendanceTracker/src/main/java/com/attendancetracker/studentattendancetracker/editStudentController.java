package com.attendancetracker.studentattendancetracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class editStudentController implements actions {

    @FXML
    TextField editNameField;
    @FXML
    TextField editLNameField;
    @FXML
    TextField editGNumberField;
    @FXML
    private Label nameLabel;
    @FXML
    private Label lnameLabel;
    @FXML
    private Label groupLabel;
    @FXML
    private Label attendanceLabel;
    @FXML
    TextField changeNameField;
    @FXML
    TextField changeLNameField;
    @FXML
    TextField changeGNumberField;
    @FXML
    Spinner changeSpinner;

    public String nameToEdit;
    public String lnameToEdit;
    public int gnumbToEdit;
    public String lineToSearch;
    public String attendancyString;
    public String rowToInsert;
    public ArrayList<Integer> days
            = new ArrayList<Integer>(31);
    private boolean studentFound;

    public void searchButton(ActionEvent event) throws IOException {
        studentFound = false;
        Save();
        Load();
        if(studentFound){
            nameLabel.setText(nameToEdit);
            lnameLabel.setText(lnameToEdit);
            groupLabel.setText(String.valueOf(gnumbToEdit));
            attendanceLabel.setText("Attendancy this month: " + attendancyString);
        }
    }

    @Override
    public void Save() {
        nameToEdit = editNameField.getText();
        lnameToEdit = editLNameField.getText();
        gnumbToEdit = Integer.parseInt(editGNumberField.getText());
        lineToSearch = (nameToEdit + " " + lnameToEdit + " " + gnumbToEdit).trim();
    }

    @Override
    public void Load() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(dataFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentLine = null;

        while(true) {
            try {
                if ((currentLine = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToSearch)) {
                try {
                    attendancyString = reader.readLine().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                studentFound = true;
                break;
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveButton(){
        rowToInsert = (changeNameField.getText() + " " + changeLNameField.getText() + " " + changeGNumberField.getText()).trim();
        Collections.sort(days);
        String daysString = "";

        File tempFile = new File("myTempFile.txt");
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(dataFile));
            writer = new BufferedWriter(new FileWriter(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String currentLine = null;

        while(true) {
            try {
                if ((currentLine = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToSearch)) {
                try {
                    reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.write(rowToInsert + System.getProperty("line.separator"));
                    for(Integer i: days){
                        daysString += i.toString();
                        daysString += " ";
                    }
                    writer.write(daysString + System.getProperty("line.separator"));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            }
            try {
                writer.write(currentLine + System.getProperty("line.separator"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataFile.delete();
        boolean successful = tempFile.renameTo(dataFile);
    }

    public void addButton(ActionEvent event){
        days.add((Integer) changeSpinner.getValue());
    }
}
