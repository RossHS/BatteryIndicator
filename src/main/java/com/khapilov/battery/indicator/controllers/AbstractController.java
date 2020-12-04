package com.khapilov.battery.indicator.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class AbstractController {
    private double xOffset;
    private double yOffset;

    @FXML
    protected void closeButtonAction() {
        getStage().close();
    }

    @FXML
    protected void minimizeButtonAction() {
        getStage().setIconified(true);
    }

    @FXML
    private void mousePressedAction(MouseEvent event) {
        xOffset = getStage().getX() - event.getScreenX();
        yOffset = getStage().getY() - event.getScreenY();
    }

    @FXML
    private void mouseDraggedAction(MouseEvent event) {
        getStage().setX(event.getScreenX() + xOffset);
        getStage().setY(event.getScreenY() + yOffset);
    }

    abstract protected Stage getStage();
}
