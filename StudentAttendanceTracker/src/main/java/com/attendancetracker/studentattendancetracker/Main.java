package com.attendancetracker.studentattendancetracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main implements actions{



    @FXML
    TableView<Student> studentTable;
    @FXML
    TableColumn<Student,Integer> groupCol;
    @FXML
    TableColumn<Student, String> nameCol;
    @FXML
    TableColumn<Student, String> lnameCol;
    @FXML
    TextField fromField;
    @FXML
    TextField toField;
    @FXML
    TextField groupFilterField;

    private int from;
    private int to;
    private int group;

    private static boolean tableInitialized = false;


    public void Add(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("Add.fxml").openStream());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Add Student");
    }

    public void updateButton(ActionEvent event) throws IOException{
        if(!tableInitialized){
            tableInit();
            tableInitialized = true;
        }
        studentTable.getItems().clear();
        Load();
    }
    public void tableInit(){
        groupCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("group"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
    }


    public void Edit(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("Edit.fxml").openStream());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Edit Student");
    }

    public void Remove(ActionEvent event) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane root = loader.load(getClass().getResource("Remove.fxml").openStream());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Remove Student");
    }

    public void Filter(ActionEvent event) {
        Save();
    }

    public void filterByPeriod(ActionEvent event){
        from = Integer.parseInt(fromField.getText());
        to = Integer.parseInt(toField.getText());
        String name = "";
        String lname = "";
        int tmpgroup = 0;
        studentTable.getItems().clear();
        try {
            Scanner reader = new Scanner(dataFile);
            while(reader.hasNextLine()){
                try{
                    name = reader.next();
                    lname = reader.next();
                    tmpgroup = reader.nextInt();
                }
                catch(NoSuchElementException elem){
                    System.out.println("File successfully read");
                }
                boolean alreadyPrinted = false;
                while(reader.hasNextInt()){

                    int tmpday = reader.nextInt();
                    if(tmpday >= from && tmpday <= to){
                        if(!alreadyPrinted){
                            studentTable.getItems().add(new Student(name, lname, tmpgroup));
                            alreadyPrinted = true;
                        }
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void filterByBoth(ActionEvent event){
        from = Integer.parseInt(fromField.getText());
        to = Integer.parseInt(toField.getText());
        group = Integer.parseInt(groupFilterField.getText());
        String name = "";
        String lname = "";
        int tmpgroup = 0;
        studentTable.getItems().clear();
        try {
            Scanner reader = new Scanner(dataFile);
            while(reader.hasNextLine()){
                boolean groupFits = false;
                try{
                    name = reader.next();
                    lname = reader.next();
                    tmpgroup = reader.nextInt();
                    if(tmpgroup == group){
                        groupFits = true;
                    }
                }
                catch(NoSuchElementException elem){
                    System.out.println("File successfully read");
                }
                boolean alreadyPrinted = false;
                while(reader.hasNextInt()){

                    int tmpday = reader.nextInt();
                    if(tmpday >= from && tmpday <= to){
                        if(!alreadyPrinted && groupFits){
                            studentTable.getItems().add(new Student(name, lname, tmpgroup));
                            alreadyPrinted = true;
                        }
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Save(){
        group = Integer.parseInt(groupFilterField.getText());
        studentTable.getItems().clear();
        try {
            Scanner reader = new Scanner(dataFile);
            while(reader.hasNextLine()){
                try{
                    String name = reader.next();
                    String lname = reader.next();
                    int tmpgroup = reader.nextInt();
                    if(tmpgroup == group){
                        studentTable.getItems().add(new Student(name, lname, group));
                    }
                }
                catch(NoSuchElementException elem){
                    System.out.println("File successfully read");
                }

                while(reader.hasNextInt()){
                    System.out.println("Attendancy: "+ reader.nextInt() +"\n");
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void Load() {
        try {
             Scanner reader = new Scanner(dataFile);
             while(reader.hasNextLine()){
                 try{
                     studentTable.getItems().add(new Student(reader.next(), reader.next(),reader.nextInt() ));
                 }
                 catch(NoSuchElementException elem){
                     System.out.println("File successfully read");
                 }

                 while(reader.hasNextInt()){
                     System.out.println("Attendancy: "+ reader.nextInt() +"\n");
                 }
             }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadFromCSV(){
        try {
            Scanner reader = new Scanner(dataFileCSV);
            while(reader.hasNextLine()){
                try{
                    studentTable.getItems().add(new Student(reader.next(), reader.next(),reader.nextInt() ));
                }
                catch(NoSuchElementException elem){
                    System.out.println("File successfully read");
                }

                while(reader.hasNextInt()){
                    System.out.println("Attendancy: "+ reader.nextInt() +"\n");
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void fileReport() {
        try {
            PrintWriter writer = new PrintWriter(dataFileCSV);
            Scanner reader = new Scanner(dataFile);
            while(reader.hasNextLine()){
                try{
                    writer.println(reader.nextLine());
                    writer.println(reader.nextLine());
                }
                catch(NoSuchElementException elem){
                    System.out.println("File successfully read");
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
