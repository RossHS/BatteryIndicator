package com.khapilov.battery.indicator;

import com.khapilov.battery.indicator.controllers.BatteryIndicatorController;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class Battery {
    private final String name;
    private final String ipAddress;
    private int percentCharging = 100;
    private int chargeSec = 2 * 60 * 1000; // 2 минуты
    private int currentSec = chargeSec;
    private final BatteryIndicatorController controller;

    public Battery(String name, String ipAddress, BatteryIndicatorController controller) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.controller = controller;
        controller.bindData(this);
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPercentCharging() {
        return percentCharging;
    }

    public void setPercentCharging(int percentCharging) {
        this.percentCharging = percentCharging;
        controller.update();
    }

    public void turnOn() {
        controller.turnOn();
//        controller.update();
    }

    public void turnOff() {
        controller.turnOff();
//        controller.update();
    }

    public void changeTimer(int sec) {
        currentSec -= sec;
        percentCharging = 100 * currentSec / chargeSec;
        if (percentCharging < 0) percentCharging = 0;
        controller.update();
    }

    public void reset() {
        currentSec = chargeSec;
        percentCharging = 100;
        controller.update();
    }
}
