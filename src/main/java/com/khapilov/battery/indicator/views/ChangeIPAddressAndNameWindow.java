package com.khapilov.battery.indicator.views;

import com.khapilov.battery.indicator.App;
import com.khapilov.battery.indicator.Battery;
import com.khapilov.battery.indicator.controllers.ChangeIPAddressAndNameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;

public class ChangeIPAddressAndNameWindow extends Stage {
    public ChangeIPAddressAndNameWindow(Battery battery) {
        initStyle(StageStyle.TRANSPARENT);
        try {
            FXMLLoader loader = App.loadFXML("change_ip_address_and_name");
            Scene scene = new Scene(loader.load());
            scene.setFill(Color.TRANSPARENT);

            ChangeIPAddressAndNameController controller = loader.getController();
            controller.setStage(this);
            controller.setBattery(battery);

            setResizable(false);

            //Позиционируем окно по мышке
            Point p = MouseInfo.getPointerInfo().getLocation();
            setX(p.getX());
            setY(p.getY());

            setScene(scene);
            show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
