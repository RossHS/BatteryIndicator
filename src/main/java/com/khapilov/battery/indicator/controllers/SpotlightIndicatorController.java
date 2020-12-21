package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.App;
import com.khapilov.battery.indicator.Spotlight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ross Khapilov
 * @version 1.0 21.12.2020
 */
public class SpotlightIndicatorController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView indicatorImage;
    @FXML
    private Label spotlightIndicatorName;

    @FXML
    private Button powerButton;

    private Spotlight spotlight;

    private static final Image onImage = new Image(App.class.getResource("icon/green-button.png").toString(), true);
    private static final Image offImage = new Image(App.class.getResource("icon/red-button.png").toString(), true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Button getPowerButton() {
        return powerButton;
    }

    public void bindData(Spotlight spotlight) {
        this.spotlight = spotlight;
        System.out.println(spotlight.getName() + "|" + spotlight.getIpAddress());
        spotlightIndicatorName.setText(spotlight.getName());

        //Инициализация текста на кнопке и индикатора вкл
        if (spotlight.isPower()) turnOn();
        else turnOff();

    }

    @FXML
    public void powerOnOff(ActionEvent actionEvent) {
        if (spotlight.isPower()) {
            spotlight.turnOff();
        } else {
            spotlight.turnOn();
        }
    }

    public void turnOn() {
        indicatorImage.setImage(onImage);
        powerButton.setText("Выкл.");
    }

    public void turnOff() {
        indicatorImage.setImage(offImage);
        powerButton.setText("Вкл.");
    }

    @FXML
    public void angleMinusMousePressed() {
        if(!spotlight.isPower()) return;
        System.out.println(spotlight.getName()+" - angleMinusMousePressed()");
        spotlight.angleMinus();
    }

    @FXML
    public void anglePlusMousePressed() {
        if(!spotlight.isPower()) return;
        System.out.println(spotlight.getName()+" - anglePlusMousePressed()");
        spotlight.anglePlus();
    }

    @FXML
    public void angleMouseReleased() {
        if(!spotlight.isPower()) return;
        System.out.println(spotlight.getName()+" - angleMouseReleased()");
        spotlight.stop();
    }

}
