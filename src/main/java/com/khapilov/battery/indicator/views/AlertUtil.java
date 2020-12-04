package com.khapilov.battery.indicator.views;

import com.khapilov.battery.indicator.App;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class AlertUtil {
    public static void createWarningAlert(String... msg) {
        createAlertHelper(Alert.AlertType.WARNING, "Предупреждение", msg);
    }

    public static void createSuccessAlert(String... msg) {
        createAlertHelper(Alert.AlertType.INFORMATION, "", msg);
    }

    private static void createAlertHelper(Alert.AlertType type, String title, String... msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        StringBuilder str = new StringBuilder();
        for (String s : msg) str.append(s).append("\n");
        alert.setContentText(str.toString());
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("applicationStyle.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
    }
}
