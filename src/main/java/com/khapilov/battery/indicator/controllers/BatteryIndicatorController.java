package com.khapilov.battery.indicator.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class BatteryIndicatorController implements Initializable {
    @FXML
    ImageView indicatorImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("!!!");
    }
}
