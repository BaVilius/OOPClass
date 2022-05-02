package com.attendancetracker.studentattendancetracker;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import java.io.*;

public class removeStudentController implements actions{

    @FXML
    TextField removeNameField;
    @FXML
    TextField removeLNameField;
    @FXML
    TextField removeGNumberField;

    private String nameToRemove;
    private String lnameToRemove;
    private int gnumbToRemove;

    public void removeButton(ActionEvent event) throws IOException {
        Save();
        Load();
    }

    @Override
    public void Save() {
        nameToRemove = removeNameField.getText();
        lnameToRemove = removeLNameField.getText();
        gnumbToRemove = Integer.parseInt(removeGNumberField.getText());
    }

    @Override
    public void Load() {
        File tempFile = new File("myTempFile.txt");
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(dataFile));
            writer = new BufferedWriter(new FileWriter(tempFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String lineToRemove = (nameToRemove + " " + lnameToRemove+ " " + gnumbToRemove).trim();
        String currentLine = null;

        while(true) {
            try {
                if ((currentLine = reader.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) {
                try {
                    reader.readLine();
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
}
