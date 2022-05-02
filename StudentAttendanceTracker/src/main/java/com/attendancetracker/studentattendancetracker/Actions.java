package com.attendancetracker.studentattendancetracker;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

interface actions {
    File dataFile = new File("studentsData.txt");
    File dataFileCSV = new File("studentsData.csv");

    void Save();
    void Load();

}

