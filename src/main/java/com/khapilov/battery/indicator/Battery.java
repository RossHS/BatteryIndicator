package com.khapilov.battery.indicator;

import com.khapilov.battery.indicator.controllers.BatteryIndicatorController;
import com.khapilov.battery.indicator.controllers.MainController;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class Battery {
    private final String name;
    private final String ipAddress;
    private int percentCharging = 100;
    private final long chargeSec;
    private long currentSec;
    private final BatteryIndicatorController controller;
    private final boolean isChargeVisible;

    public Battery(String name, String ipAddress, double chargeH, BatteryIndicatorController controller) {
        this(name, ipAddress, chargeH, controller, true);
    }

    public Battery(String name, String ipAddress, double chargeH, BatteryIndicatorController controller, boolean isChargeVisible) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.chargeSec = (long) (chargeH * 60 * 60 * 1000);
        currentSec = chargeSec;
        this.controller = controller;
        this.isChargeVisible = isChargeVisible;
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

    public long getChargeSec() {
        return chargeSec;
    }

    public void setCurrentSec(long currentSec) {
        this.currentSec = currentSec;
    }

    public boolean isChargeVisible() {
        return isChargeVisible;
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
        percentCharging = (int) (100 * currentSec / chargeSec);
        if (percentCharging < 0) percentCharging = 0;
        else if (percentCharging >= 100) percentCharging = 99;
        controller.update();
    }

    public void reset() {
        currentSec = chargeSec;
        percentCharging = 100;
        MainController.updatePropertyFile(this);
        controller.update();
    }
}
