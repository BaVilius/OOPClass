package com.attendancetracker.studentattendancetracker;

public class Student {
    private String name;
    private String lastName;
    private int group;

    public Student(){
        this.name = "";
        this.lastName = "";
        this.group = 0;
    }

    public Student(String name, String lastName, int group){
        this.name = name;
        this.lastName = lastName;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
