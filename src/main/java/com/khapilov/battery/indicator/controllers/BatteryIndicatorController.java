package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.App;
import com.khapilov.battery.indicator.Battery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class BatteryIndicatorController implements Initializable {
    private enum BarColor {
        RED_BAR("red-bar"),
        YELLOW_BAR("yellow-bar"),
        ORANGE_BAR("orange-bar"),
        GREEN_BAR("green-bar");

        BarColor(String ccs) {
            this.ccs = ccs;
        }

        final String ccs;
    }

    @FXML
    private ImageView indicatorImage;
    @FXML
    private Label batteryIndicatorName;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label batteryPercent;

    private Battery battery;

    private static final Image onImage = new Image(App.class.getResource("icon/green-button.png").toString(),true);
    private static final Image offImage = new Image(App.class.getResource("icon/red-button.png").toString(),true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        DropShadow ds = new DropShadow(15, Color.GREEN);
//        ds.setInput(new Glow(0.5));
//        progressBar.setEffect(ds);

        progressBar.progressProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double progress = newValue == null ? 0 : newValue.doubleValue();
                if (progress < 0.2) {
                    setBarStyleClass(progressBar, BarColor.RED_BAR);
                } else if (progress < 0.4) {
                    setBarStyleClass(progressBar, BarColor.ORANGE_BAR);
                } else if (progress < 0.6) {
                    setBarStyleClass(progressBar, BarColor.YELLOW_BAR);
                } else {
                    setBarStyleClass(progressBar, BarColor.GREEN_BAR);
                }
            }

            private void setBarStyleClass(ProgressBar bar, BarColor barColor) {
                bar.getStyleClass().removeAll();
                bar.getStyleClass().add(barColor.ccs);
            }
        });
    }

    public void bindData(Battery battery) {
        this.battery = battery;
        System.out.println(battery.getName() + "|" + battery.getIpAddress());
        batteryIndicatorName.setText(battery.getName());
        update();
    }

    private void update() {
        progressBar.setProgress((double) battery.getPercentCharging() / 100);
        batteryPercent.setText(battery.getPercentCharging() + "%");
    }

    public void turnOn() {
        indicatorImage.setImage(onImage);
    }

    public void turnOff() {
        indicatorImage.setImage(offImage);
    }
}
