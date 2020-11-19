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
    private BatteryIndicatorController controller;

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
}
