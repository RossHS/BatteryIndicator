package com.khapilov.battery.indicator;

import com.khapilov.battery.indicator.controllers.BatteryIndicatorController;
import com.khapilov.battery.indicator.controllers.MainController;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class Battery {
    private String name;
    private String ipAddress;
    private int percentCharging = 100;
    private final long chargeSec;
    private long currentSec;
    private final BatteryIndicatorController controller;
    private final boolean isChargeVisible;

    private double minV = 14.8;
    private double maxV = 16.8;

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

    public Battery(String name, String ipAddress, double chargeH, BatteryIndicatorController controller, double minVoltage, double maxVoltage) {
        this(name, ipAddress, chargeH, controller, true);
        this.minV = minVoltage;
        this.maxV = maxVoltage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        controller.updateBatteryName();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public double getMaxVoltage() {
        return maxV;
    }

    public double getMinVoltage() {
        return minV;
    }

    public int getPercentCharging() {
        return percentCharging;
    }

    public void setPercentCharging(int percentCharging) {
        this.percentCharging = percentCharging;
        System.out.println((double) percentCharging / 100);
        currentSec = (long) ((double) percentCharging / 100 * chargeSec);
        System.out.println(currentSec);
        controller.update();
    }

    public long getCurrentSec() {
        return currentSec;
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

    public void saveVoltage(double inputV) {
        int tempPercent = (int) (((inputV - minV) * 100) / (maxV - minV));
        System.out.println("Расчетный процент " + tempPercent);
        setPercentCharging(tempPercent);
        MainController.updatePropertyFile(this);
        controller.update();
    }

    public void reset() {
        currentSec = chargeSec;
        percentCharging = 100;
        MainController.updatePropertyFile(this);
        controller.update();
    }
}
