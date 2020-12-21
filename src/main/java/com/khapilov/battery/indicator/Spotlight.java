package com.khapilov.battery.indicator;

import com.khapilov.battery.indicator.controllers.SpotlightIndicatorController;
import javafx.application.Platform;

import java.io.IOException;
import java.net.*;

public class Spotlight {
    private String name;
    private String ipAddress;
    private final SpotlightIndicatorController controller;
    private boolean power; //Индикатор Включения
    private DatagramSocketClient datagramSocketClient;

    public Spotlight(String name, String ipAddress, SpotlightIndicatorController controller) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.controller = controller;
        datagramSocketClient = new DatagramSocketClient();
        controller.bindData(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public void turnOn() {
        controller.getPowerButton().setDisable(true);
        new Thread(() -> {
            try {
                datagramSocketClient.turnOn();
                power = true;
                Platform.runLater(controller::turnOn);
            } catch (IOException e) {
                e.printStackTrace();
                power = false;
                Platform.runLater(controller::turnOff);
            } finally {
                Platform.runLater(() -> controller.getPowerButton().setDisable(false));
            }
        }).start();
    }

    public void turnOff() {
        controller.getPowerButton().setDisable(true);
        new Thread(() -> {
            try {
                datagramSocketClient.turnOff();
                power = false;
                Platform.runLater(controller::turnOff);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Platform.runLater(() -> controller.getPowerButton().setDisable(false));
            }
        }).start();
    }

    public void anglePlus() {

    }

    public void angleMinus() {

    }

    private class DatagramSocketClient {
        private DatagramSocket socket;
        private InetAddress address;
        private final int port = 4002;

        private final byte[] SPOTLIGHT_ON = {(byte) 0xff, 0x01, 0x00, 0x07, 0x00, (byte) 0xfe, 0x06};
        private final byte[] SPOTLIGHT_OFF = {(byte) 0xff, 0x01, 0x00, 0x07, 0x00, (byte) 0xff, 0x07};
        private final byte[] SPOTLIGHT_ANGLE_PLUS = {(byte) 0xff, 0x01, 0x00, 0x07, 0x00, (byte) 0xfd, 0x05};
        private final byte[] SPOTLIGHT_ANGLE_MINUS = {(byte) 0xff, 0x01, 0x00, 0x07, 0x00, (byte) 0xfc, 0x04};
        private final byte[] SPOTLIGHT_STOP = {(byte) 0xff, 0x01, 0x00, 0x00, 0x00, 0x00, 0x01};

        public DatagramSocketClient() {
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3_000);
                address = InetAddress.getByName(ipAddress);
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        }

        public void turnOn() throws IOException {
            DatagramPacket packet = new DatagramPacket(SPOTLIGHT_ON, SPOTLIGHT_ON.length, address, port);
            socket.send(packet);
            socket.receive(packet);
        }

        public void turnOff() throws IOException {
            DatagramPacket packet = new DatagramPacket(SPOTLIGHT_OFF, SPOTLIGHT_OFF.length, address, port);
            socket.send(packet);
            socket.receive(packet);
        }

        public void sendAnglePlus() throws IOException {
            DatagramPacket packet = new DatagramPacket(SPOTLIGHT_ANGLE_PLUS, SPOTLIGHT_ANGLE_PLUS.length, address, port);
            socket.send(packet);
            socket.receive(packet);
        }

        public void sendAngleMinus() throws IOException {
            DatagramPacket packet = new DatagramPacket(SPOTLIGHT_ANGLE_MINUS, SPOTLIGHT_ANGLE_MINUS.length, address, port);
            socket.send(packet);
            socket.receive(packet);
        }

        public void close() {
            socket.close();
        }

    }
}
