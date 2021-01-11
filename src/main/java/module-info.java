module BatteryIndicator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    opens com.khapilov.battery.indicator to javafx.fxml;
    opens com.khapilov.battery.indicator.controllers to javafx.fxml;
    opens com.khapilov.battery.indicator.views to javafx.fxml;
    exports com.khapilov.battery.indicator;
    exports com.khapilov.battery.indicator.controllers;
}