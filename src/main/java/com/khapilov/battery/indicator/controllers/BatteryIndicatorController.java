package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class BatteryIndicatorController implements Initializable {
    @FXML
    private ImageView indicatorImage;
    @FXML
    private Label batteryIndicatorName;

    private Battery battery;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void bindData(Battery battery) {
        this.battery = battery;
        update();
    }

    private void update() {
        System.out.println(battery.getName() + "|" + battery.getIpAddress());
        batteryIndicatorName.setText(battery.getName());
    }
}
