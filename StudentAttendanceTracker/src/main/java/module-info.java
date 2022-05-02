module com.attendancetracker.studentattendancetracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.attendancetracker.studentattendancetracker to javafx.fxml;
    exports com.attendancetracker.studentattendancetracker;
}