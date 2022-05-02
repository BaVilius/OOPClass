package com.example.interestcalculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class anuityTableController implements Initializable {

    @FXML
    TableView<row> anuityTable;
    @FXML
    TextField fromField;
    @FXML
    TextField toField;

    public int from;
    public int to;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn month = new TableColumn<row, Integer>("MONTH");
        month.setCellValueFactory(new PropertyValueFactory<row, Integer>("month"));

        TableColumn amount = new TableColumn<row, Float>("AMOUNT");
        amount.setCellValueFactory(new PropertyValueFactory<row, Float>("amount"));

        TableColumn wointerest = new TableColumn<row, Float>("WOINTEREST");
        wointerest.setCellValueFactory(new PropertyValueFactory<row, Float>("wointerest"));

        TableColumn left = new TableColumn<row, Float>("LEFT");
        left.setCellValueFactory(new PropertyValueFactory<row, Float>("left"));

        anuityTable.getColumns().add(month);
        anuityTable.getColumns().add(amount);
        anuityTable.getColumns().add(wointerest);
        anuityTable.getColumns().add(left);

        try {
            fillTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter(ActionEvent event) throws IOException{
        anuityTable.getItems().clear();

        from = Integer.parseInt(fromField.getText());
        to = Integer.parseInt(toField.getText());

        File file = new File("user.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        secondController secondController = new secondController();


        float sum = Float.parseFloat(br.readLine());
        int years = Integer.parseInt(br.readLine());
        int months = Integer.parseInt(br.readLine());
        float interest = Float.parseFloat(br.readLine());
        int monthsDelayed = 0;
        float delayInterest = 0f;
        int fromD = 0;
        int toD = 0;
        if(HelloController.delayIncluded){
            fromD = Integer.parseInt(br.readLine());
            toD = Integer.parseInt(br.readLine());
            delayInterest = Float.parseFloat(br.readLine());
            monthsDelayed = toD - fromD;
        }




        float paymentFromDelay = 0f;
        int totalMonths = secondController.countMonths(years, months);
        float monthly = secondController.calculateAnuityMonthly(totalMonths, interest, sum);
        float wointerest; ;

        for(int i = 1; i <= totalMonths+ monthsDelayed; i++) {
           wointerest = secondController.countAnuityInterest(totalMonths, interest, sum);
            if (i >= from && i <= to) {
                if(i >= fromD && i <= toD){
                    populateRow(i, 0f , 0f, sum);
                    paymentFromDelay+=sum*(delayInterest/100/12);
                }
                else{
                    populateRow(i, monthly, wointerest, sum);
                    sum = sum - monthly;
                }
            }
            sum-=monthly;
        }

    }
    public void populateRow(int month, float monthly, float wointerest, float left){
        anuityTable.getItems().add(new row(month, monthly, wointerest, left));
    }

    public void fillTable() throws IOException {
        File file = new File("user.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        secondController secondController = new secondController();

        HelloController hello = new HelloController();
        if(hello.delayIncluded){
            System.out.println("/////////////////////////////");
        }
        float sum = Float.parseFloat(br.readLine());
        int years = Integer.parseInt(br.readLine());
        int months = Integer.parseInt(br.readLine());
        float interest = Float.parseFloat(br.readLine());
        int from = 0;
        int to = 0;
        int monthsDelayed = 0;
        float delayInterest = 0f;
        float paymentFromDelay = 0f;
        int totalMonths = secondController.countMonths(years, months);

        float monthly = secondController.calculateAnuityMonthly(totalMonths, interest, sum);
        float wointerest;

        if(HelloController.delayIncluded){
            from = Integer.parseInt(br.readLine());
            to = Integer.parseInt(br.readLine());
            delayInterest = Float.parseFloat(br.readLine());
            monthsDelayed = to - from;
        }


        for (int i = 1; i <= totalMonths; i++) {
            wointerest = secondController.countAnuityInterest(totalMonths, interest, sum);
            if(i >= from && i <= to){
                populateRow(i, 0f , 0f, sum);
                paymentFromDelay+=sum*(delayInterest/100/12);
            }
            else{
                populateRow(i, monthly, wointerest, sum);
                sum = sum - monthly;
            }
        }
        if(HelloController.delayIncluded){
            populateRow(0,0f,0f, paymentFromDelay);
        }
    }

    public void goToMain(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
