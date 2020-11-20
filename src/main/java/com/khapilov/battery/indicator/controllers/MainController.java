package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

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

    private List<Battery> batteryList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Батарея АРМ
        Battery arm = new Battery("ARM", "192.168.1.1", 36.5, armBatteryController);
        batteryList.add(arm);

        //Батареи RTR
        Battery rtr1 = new Battery("RTR1", "192.168.1.12", 36, rtr1BatteryController);
        rtr1.setPercentCharging(50);
        Battery rtr2 = new Battery("RTR2", "192.168.1.22", 37, rtr2BatteryController);
        rtr2.setPercentCharging(24);
        Battery rtr3 = new Battery("RTR3", "192.168.1.32", 35, rtr3BatteryController);
        rtr3.setPercentCharging(10);
        Battery rtr4 = new Battery("RTR4", "192.168.1.42", 35.5, rtr4BatteryController);
        batteryList.add(rtr1);
        batteryList.add(rtr2);
        batteryList.add(rtr3);
        batteryList.add(rtr4);

        //Батареи CAM
        Battery cam1 = new Battery("CAM1", "192.168.1.13", 30, cam1BatteryController);
        Battery cam2 = new Battery("CAM2", "192.168.1.23", 34, cam2BatteryController);
        Battery cam3 = new Battery("CAM3", "192.168.1.33", 31, cam3BatteryController);
        Battery cam4 = new Battery("CAM4", "192.168.1.43", 32, cam4BatteryController);
        batteryList.add(cam1);
        batteryList.add(cam2);
        batteryList.add(cam3);
        batteryList.add(cam4);


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
}
