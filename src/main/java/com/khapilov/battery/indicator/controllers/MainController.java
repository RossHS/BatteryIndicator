package com.khapilov.battery.indicator.controllers;

import com.khapilov.battery.indicator.Battery;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.InetAddress;
import java.net.NetworkInterface;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Батарея АРМ
        Battery arm = new Battery("ARM", "192.168.1.1", armBatteryController);

        //Батареи RTR
        Battery rtr1 = new Battery("RTR1", "192.168.1.12", rtr1BatteryController);
        rtr1.setPercentCharging(50);
        Battery rtr2 = new Battery("RTR2", "192.168.1.22", rtr2BatteryController);
        rtr2.setPercentCharging(24);
        Battery rtr3 = new Battery("RTR3", "192.168.1.32", rtr3BatteryController);
        rtr3.setPercentCharging(10);
        Battery rtr4 = new Battery("RTR4", "192.168.1.42", rtr4BatteryController);

        //Батареи CAM
        Battery cam1 = new Battery("CAM1", "192.168.1.13", cam1BatteryController);
        Battery cam2 = new Battery("CAM2", "192.168.1.23", cam2BatteryController);
        Battery cam3 = new Battery("CAM3", "192.168.1.33", cam3BatteryController);
        Battery cam4 = new Battery("CAM4", "192.168.1.43", cam4BatteryController);


        armBatteryController.turnOn();


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
                    for (NetworkInterface netint : Collections.list(nets))
                        checkAvailableInterfaceInformation(netint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 1_000, 2_000);
    }

    //TODO Идея попробовать через таймер вычитывать из подключенных батарей каждые N время процент заряда
    //TODO доделать реакцию на ip адреса
    private static void checkAvailableInterfaceInformation(NetworkInterface netInt) {
//        System.out.printf("Display name: %s\n", netInt.getDisplayName());
//        System.out.printf("Name: %s\n", netInt.getName());
        Set<String> availableHosts = new HashSet<>();

        Enumeration<InetAddress> inetAddresses = netInt.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
            System.out.println("Display name - " + netInt.getDisplayName());
            System.out.println("InetAddress - " + inetAddress.getHostAddress());
            availableHosts.add(inetAddress.getHostAddress());
        }


    }
}
