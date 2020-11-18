package com.khapilov.battery.indicator;

/**
 * @author Ross Khapilov
 * @version 1.0 18.11.2020
 */
public class Battery {
    private final String name;
    private final String ipAddress;
    private int percentCharging = 100;

    public Battery(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
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
    }
}
