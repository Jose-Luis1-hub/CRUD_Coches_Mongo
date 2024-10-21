package com.example.crud_coches_mongo.Clases;

import javafx.scene.control.Alert;

public class Alerta {

    public static void Error(String mensaje){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje );
        alert.showAndWait();
    }
    public static void Info(String mensaje, String titulo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje );
        alert.showAndWait();
    }
}
