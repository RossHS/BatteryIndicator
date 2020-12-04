package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import com.khapilov.battery.indicator.views.AlertUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VoltageWindowController extends AbstractController implements Initializable {
    private Stage stage;
    private Battery battery;

    @FXML
    private Label batteryLabel;
    @FXML
    private TextField inputVField;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
        batteryLabel.setText(batteryLabel.getText()+" - "+ battery.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildNumberField(inputVField);
    }

    private void buildNumberField(TextField tf) {
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,3}([.]\\d{0,10})?")) {
                tf.setText(oldValue);
            }
        });
    }

    @FXML
    public void saveVoltage() {
        String input = inputVField.getText();
        if (input == null || input.equals("")) return;
        double voltage = Double.parseDouble(input);

        //Проверка выставляемого напряжения на попадание в диапазон
        if (voltage < battery.getMinVoltage() || voltage > battery.getMaxVoltage()) {
            AlertUtil.createWarningAlert("Заданное напряжение " + voltage +
                    " В не попадает в диапазон - от " + battery.getMinVoltage() +
                    " В до " + battery.getMaxVoltage() + " В");
            return;
        }
        System.out.println(Double.parseDouble(input));
//        Platform.runLater(() -> battery.saveVoltage(Double.parseDouble(inputVField.getText())));
        battery.saveVoltage(Double.parseDouble(inputVField.getText()));
        stage.close();
    }

    @Override
    protected Stage getStage() {
        return stage;
    }

}
