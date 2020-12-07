package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import com.khapilov.battery.indicator.Utils;
import com.khapilov.battery.indicator.views.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class ChangeIPAddressAndNameController extends AbstractController implements Initializable {
    private Stage stage;
    private Battery battery;

    @FXML
    private TextField inputIPField;
    @FXML
    private TextField inputBatNameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildNumberField(inputBatNameField, ".{1,4}?");
    }

    private void buildNumberField(TextField tf, String regex) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(regex)) {
                tf.setText(oldValue);
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
        inputIPField.setText(battery.getIpAddress());
        inputBatNameField.setText(battery.getName());
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

    @FXML
    public void saveButtonAction() {
        String batteryName = inputBatNameField.getText();
        String ipAddress = inputIPField.getText();
        if (!Utils.isValidIPAddress(ipAddress)) {
            AlertUtil.createWarningAlert("Введен некорректный ip адрес -" + ipAddress);
            return;
        }
        battery.setIpAddress(ipAddress);
        battery.setName(batteryName);
        stage.close();
    }

}
