module com.ardupilot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires java.desktop;

    requires kotlin.stdlib;
    requires java.prefs;
    requires jfxutils;

    opens com.ardupilot.paramextractor to javafx.fxml, com.google.gson;
    opens com.ardupilot.paramextractor.ui to javafx.fxml, com.google.gson;
    exports com.ardupilot.paramextractor to javafx.graphics, javafx.base, javafx.controls;
}