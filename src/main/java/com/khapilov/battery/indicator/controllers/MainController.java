package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.App;
import com.khapilov.battery.indicator.Battery;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class MainController implements Initializable {
    @FXML
    private AnchorPane main;
    @FXML
    private VBox vbox;

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

    private final List<Battery> batteryList = new ArrayList<>();
    private static final File propertyFile = new File("C:\\Users\\User\\AppData\\Roaming\\batt.properties");
    public static final Properties prop = new Properties();
    private double xOffset;
    private double yOffset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Связал размер основной сцены с размером контейнера
        main.prefHeightProperty().bind(vbox.heightProperty());

        //Батарея АРМ
        Battery arm = new Battery("ARM", "192.168.1.1", 36.5, armBatteryController);
        batteryList.add(arm);

        //Батареи RTR
        Battery rtr1 = new Battery("RTR1", "192.168.1.12", 36, rtr1BatteryController);
        Battery rtr2 = new Battery("RTR2", "192.168.1.22", 37, rtr2BatteryController);
        Battery rtr3 = new Battery("RTR3", "192.168.1.32", 35, rtr3BatteryController);
        Battery rtr4 = new Battery("RTR4", "192.168.1.42", 35.5, rtr4BatteryController);
        batteryList.add(rtr1);
        batteryList.add(rtr2);
        batteryList.add(rtr3);
        batteryList.add(rtr4);

        //Батареи CAM
        Battery cam1 = new Battery("CAM1", "192.168.1.101", 30, cam1BatteryController, false);
        Battery cam2 = new Battery("CAM2", "192.168.1.102", 34, cam2BatteryController, false);
        Battery cam3 = new Battery("CAM3", "192.168.1.103", 31, cam3BatteryController, false);
        Battery cam4 = new Battery("CAM4", "192.168.1.104", 32, cam4BatteryController, false);
        batteryList.add(cam1);
        batteryList.add(cam2);
        batteryList.add(cam3);
        batteryList.add(cam4);

        preparePropertyFile();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    for (Battery bat : batteryList) {
                        System.out.println(bat.getIpAddress());
                        InetAddress address = InetAddress.getByName(bat.getIpAddress());

                        new Thread(() -> {
                            try {
                                if (address.isReachable(3_000)) {
                                    Platform.runLater(bat::turnOn);
                                    System.out.println("contains");
                                } else {
                                    Platform.runLater(bat::turnOff);
                                    System.out.println("false");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();

                        Platform.runLater(() -> bat.changeTimer(5_000));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 2_000, 5_000);
    }

    private void preparePropertyFile() {
        if (!propertyFile.exists()) {
            // Если файла с настройками нет, то создаем его
            try (OutputStream out = new FileOutputStream(propertyFile)) {
                for (Battery battery : batteryList) {
                    //Сохранение расчетного времени разр
                    setPropertyTime(battery);
                }
                prop.store(out, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // Если файла с настройками есть, то считываем его
            try (InputStream input = new FileInputStream(propertyFile)) {
                prop.load(input);
                for (Battery battery : batteryList) {
                    LocalDateTime localDateTimeCalc = LocalDateTime.parse(prop.get(battery.getName() + ".time").toString());
                    battery.setCurrentSec(ChronoUnit.MILLIS.between(LocalDateTime.now(), localDateTimeCalc));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void closeButtonAction() {
        App.getStage().close();
    }

    @FXML
    private void minimizeButtonAction() {
        App.getStage().setIconified(true);
    }

    @FXML
    private void mousePressedAction(MouseEvent event) {
        xOffset = App.getStage().getX() - event.getScreenX();
        yOffset = App.getStage().getY() - event.getScreenY();
    }

    @FXML
    private void mouseDraggedAction(MouseEvent event) {
        App.getStage().setX(event.getScreenX() + xOffset);
        App.getStage().setY(event.getScreenY() + yOffset);
    }

    public static void updatePropertyFile(Battery battery) {
        if (!propertyFile.exists()) return;
        try (OutputStream out = new FileOutputStream(propertyFile)) {
            setPropertyTime(battery);
            prop.store(out, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setPropertyTime(Battery battery) {
        prop.setProperty(battery.getName() + ".time", LocalDateTime.now().plus(battery.getChargeSec(), ChronoUnit.MILLIS).toString());
    }
}
