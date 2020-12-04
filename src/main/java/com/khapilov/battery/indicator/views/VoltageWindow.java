package com.khapilov.battery.indicator.views;

import com.khapilov.battery.indicator.App;
import com.khapilov.battery.indicator.Battery;
import com.khapilov.battery.indicator.controllers.VoltageWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;

public class VoltageWindow extends Stage {

    public VoltageWindow(Battery battery) {

        initStyle(StageStyle.TRANSPARENT);
        try {
            FXMLLoader loader = App.loadFXML("voltage_window");
            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);

            VoltageWindowController controller = loader.getController();
            controller.setStage(this);
            controller.setBattery(battery);

            setResizable(false);

            //Позиционируем окно по мышке
            Point p = MouseInfo.getPointerInfo().getLocation();
            setX(p.getX());
            setY(p.getY());

            setScene(scene);
            show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
