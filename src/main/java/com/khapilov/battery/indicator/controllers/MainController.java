package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane armBattery;
    @FXML
    private BatteryIndicatorController armBatteryController;


    @FXML
    private AnchorPane rtr1Battery;
    @FXML
    private BatteryIndicatorController rtr1BatteryController;
    @FXML
    private AnchorPane rtr2Battery;
    @FXML
    private BatteryIndicatorController rtr2BatteryController;
    @FXML
    private AnchorPane rtr3Battery;
    @FXML
    private BatteryIndicatorController rtr3BatteryController;
    @FXML
    private AnchorPane rtr4Battery;
    @FXML
    private BatteryIndicatorController rtr4BatteryController;

    @FXML
    private AnchorPane cam1Battery;
    @FXML
    private BatteryIndicatorController cam1BatteryController;
    @FXML
    private AnchorPane cam2Battery;
    @FXML
    private BatteryIndicatorController cam2BatteryController;
    @FXML
    private AnchorPane cam3Battery;
    @FXML
    private BatteryIndicatorController cam3BatteryController;
    @FXML
    private AnchorPane cam4Battery;
    @FXML
    private BatteryIndicatorController cam4BatteryController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        armBatteryController.bindData(new Battery("ARM", "192.168.1.1"));

        rtr1BatteryController.bindData(new Battery("RTR1", "192.168.1.12"));
        rtr2BatteryController.bindData(new Battery("RTR2", "192.168.1.22"));
        rtr3BatteryController.bindData(new Battery("RTR3", "192.168.1.32"));
        rtr4BatteryController.bindData(new Battery("RTR4", "192.168.1.42"));

        cam1BatteryController.bindData(new Battery("CAM1", "192.168.1.13"));
        cam2BatteryController.bindData(new Battery("CAM2", "192.168.1.23"));
        cam3BatteryController.bindData(new Battery("CAM3", "192.168.1.33"));
        cam4BatteryController.bindData(new Battery("CAM4", "192.168.1.43"));
    }
}
