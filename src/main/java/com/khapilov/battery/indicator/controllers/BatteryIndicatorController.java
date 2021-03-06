package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.App;
import com.khapilov.battery.indicator.Battery;
import com.khapilov.battery.indicator.views.ChangeIPAddressAndNameWindow;
import com.khapilov.battery.indicator.views.VoltageWindow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class BatteryIndicatorController implements Initializable {
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView indicatorImage;
    @FXML
    private Label batteryIndicatorName;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label batteryPercent;

    @FXML
    private Label lowBatteryLabel;
    private Timer lowBatteryLabelTime;

    @FXML
    private Label workFromElectricityLabel;
    private boolean workFromElectricalNetwork; //Флаг индикации работы от сети

    private Battery battery;
    private boolean isActive;
    private boolean beepReady; //Флаг для звукового сигнала
    private static final Image onImage = new Image(App.class.getResource("icon/green-button.png").toString(), true);
    private static final Image offImage = new Image(App.class.getResource("icon/red-button.png").toString(), true);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


//        DropShadow ds = new DropShadow(15, Color.GREEN);
//        ds.setInput(new Glow(0.5));
//        progressBar.setEffect(ds);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem dataUpdate = new MenuItem("Замена АКБ");
        dataUpdate.setOnAction(actionEvent -> {
            if (battery != null) {
                System.out.println("Обновление данных батареи " + battery.getName());
                beepReady = true;
                battery.reset();
            }
        });

        MenuItem calculateVoltage = new MenuItem("Ввести напряжение");
        calculateVoltage.setOnAction(actionEvent -> {
            if (battery != null) {
                System.out.println("Открытие окна с вводом напряжения");
                new VoltageWindow(battery);
            }
        });

        MenuItem changeBatteryNameAndAddress = new MenuItem("Сменить IP адрес");
        changeBatteryNameAndAddress.setOnAction(actionEvent -> {
            if (battery != null) {
                System.out.println("Смена IP адреса и имени батареи");
                new ChangeIPAddressAndNameWindow(battery);
            }
        });

        MenuItem workFromElectrical = new MenuItem("Работа от сети");
        workFromElectrical.setOnAction(actionEvent -> {
            if (battery != null) {
                System.out.println("Работа от сети");
                setVisibleBatteryIndicators(!workFromElectricalNetwork);
            }
        });

        contextMenu.getItems().addAll(calculateVoltage,
//                changeBatteryNameAndAddress,
                dataUpdate, workFromElectrical);

        pane.setOnContextMenuRequested(event -> contextMenu.show(pane, event.getScreenX(), event.getScreenY()));

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
                bar.getStyleClass().removeAll(BarColor.getAll());
                bar.getStyleClass().add(barColor.ccs);
            }
        });
    }

    public void bindData(Battery battery) {
        this.battery = battery;
        System.out.println(battery.getName() + "|" + battery.getIpAddress());
        batteryIndicatorName.setText(battery.getName());

        //Удаляем невидимые элементы
        if (!battery.isChargeVisible()) {
            pane.getChildren().remove(batteryPercent);
            pane.getChildren().remove(progressBar);
        }
        update();
    }

    public void updateBatteryName() {
        batteryIndicatorName.setText(battery.getName());
    }

    public void update() {
        if (isActive) {
            System.out.println("UPDATE");
            progressBar.setProgress((double) battery.getPercentCharging() / 100);
            batteryPercent.setText(battery.getPercentCharging() + "%");

            lowBatteryCheck();

            //Если флаг готов и заряд меньше 10 процентов
            if (battery != null && beepReady && battery.getPercentCharging() <= Battery.LOW_BATTERY_PERCENT) {
                MainController.stagePopUpAction();
                beepReady = false;
            }
        }
    }

    private void lowBatteryCheck() {
        if (battery.getPercentCharging() <= Battery.LOW_BATTERY_PERCENT && !workFromElectricalNetwork) {
            if (lowBatteryLabelTime == null) {
                lowBatteryLabelTime = new Timer(true);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        lowBatteryLabel.setVisible(!lowBatteryLabel.isVisible());
                    }
                };
                lowBatteryLabelTime.scheduleAtFixedRate(timerTask, 100, 1_000);
            }

        } else if (battery.getPercentCharging() > Battery.LOW_BATTERY_PERCENT) {
            lowBatteryTimerReset();
        }
    }

    private void lowBatteryTimerReset() {
        if (lowBatteryLabelTime != null) {
            lowBatteryLabelTime.cancel();
            lowBatteryLabelTime = null;
        }
        lowBatteryLabel.setVisible(false);
    }

    public void turnOn() {
        if (!isActive) {
            isActive = true;
            indicatorImage.setImage(onImage);
            beepReady = true;
        }
    }

    public void turnOff() {
        if (isActive) {
            isActive = false;
            indicatorImage.setImage(offImage);
            beepReady = false;

            lowBatteryTimerReset();
        }
    }

    public void setVisibleBatteryIndicators(boolean hide) {
        if (hide) lowBatteryTimerReset();
        batteryPercent.setVisible(!hide);
        progressBar.setVisible(!hide);
        workFromElectricityLabel.setVisible(hide);
        workFromElectricalNetwork = hide;
    }

    private enum BarColor {
        RED_BAR("red-bar"),
        YELLOW_BAR("yellow-bar"),
        ORANGE_BAR("orange-bar"),
        GREEN_BAR("green-bar");

        private static String[] all;

        BarColor(String ccs) {
            this.ccs = ccs;
        }

        public static String[] getAll() {
            if (all == null) {
                BarColor[] colors = BarColor.values();
                all = new String[colors.length];
                for (int i = 0; i < all.length; i++) {
                    all[i] = colors[i].ccs;
                }
            }
            return all;
        }

        final String ccs;
    }
}
