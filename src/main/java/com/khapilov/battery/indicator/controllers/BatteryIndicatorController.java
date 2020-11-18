package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    private static final Image onImage = new Image("src/main/resources/com/khapilov/battery/indicator/icon/green-button.png");
    private static final Image offImage = new Image("src/main/resources/com/khapilov/battery/indicator/icon/red-button.png");

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

    public void turnOn() {
        indicatorImage.setImage(onImage);
    }

    public void turnOff() {
        indicatorImage.setImage(offImage);
    }
}
