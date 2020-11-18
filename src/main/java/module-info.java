module com.khapilov {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.khapilov.battery.indicator to javafx.fxml;
    exports com.khapilov.battery.indicator;
}