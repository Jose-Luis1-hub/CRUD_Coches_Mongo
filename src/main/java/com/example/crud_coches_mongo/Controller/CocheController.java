package com.example.crud_coches_mongo.Controller;

import com.example.crud_coches_mongo.CRUD.CocheCRUD;
import com.example.crud_coches_mongo.Clases.Alerta;
import com.example.crud_coches_mongo.Clases.Coche;
import com.example.crud_coches_mongo.Clases.ConnectionDB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CocheController implements Initializable {
    @FXML
    private Button InsertarIDBoton;

    @FXML
    private TableView<Coche> TablaID;

    @FXML
    private Button eliminarIDBoton;

    @FXML
    private Button guardarIDBoton;

    @FXML
    private TextField marcaID;

    @FXML
    private TableColumn<Coche,String> marcaIDTabla;

    @FXML
    private TextField matriculaID;

    @FXML
    private TableColumn<Coche,String> matriculaIDTabla;

    @FXML
    private TextField modeloID;

    @FXML
    private TableColumn<Coche,String> modeloIDTabla;

    @FXML
    private Button modificarIDBoton;

    @FXML
    private ComboBox<String> tipoID;

    @FXML
    private TableColumn<Coche,String> tipoIDTabla;

    CocheCRUD cocheCRUD;

    Coche cocheSeleccionado;

    @FXML
    void onCancelarButtonClick(ActionEvent event) {
        limpiarCampos();
    }



    @FXML
    void onEliminarButtonClick(ActionEvent event) {
        if(cocheSeleccionado == null) {
            Alerta.Error("No se ha seleccionado ningun coche");
        } else {
            cocheCRUD.eliminar(cocheSeleccionado.getMatricula());
            cargarCoches();
            limpiarCampos();
            Alerta.Info("Coche eliminado correctamente", "ÉXITO!!");
        }
    }

    @FXML
    void onModificarButtonClick(ActionEvent event) {
        if(cocheSeleccionado == null) {
            Alerta.Error("No se ha seleccionado ningun coche");
        } else{
            cocheSeleccionado.setMatricula(matriculaID.getText());
            cocheSeleccionado.setModelo(modeloID.getText());
            cocheSeleccionado.setMarca(marcaID.getText());
            cocheSeleccionado.setTipo(tipoID.getValue());

            cocheCRUD.modificarCoche(cocheSeleccionado);
            cargarCoches();
            Alerta.Info("Coche modificado correctamente", "ÉXITO!!");
        }
    }

    @FXML
    void onInsertarButtonClick(ActionEvent event) {
        if (matriculaID.getText().isEmpty() || modeloID.getText().isEmpty() || marcaID.getText().isEmpty() || tipoID.getValue() == null) {
            Alerta.Error("Completa todos los campos");
        } else {
            String matricula = matriculaID.getText();
            if(cocheCRUD.comprobarMatricula(matricula)) {
                Alerta.Error("La matricula ya existe");
            } else {
                String modelo = modeloID.getText();
                String marca = marcaID.getText();
                String tipo = tipoID.getValue();
                Coche coche = new Coche(matricula, modelo, marca, tipo);
                cocheCRUD.insertarCoche(coche);
                limpiarCampos();
            }
        }
        cargarCoches();
    }

    public void cargarCoches() {
        ArrayList<Coche> listadoCoches = cocheCRUD.obtenerCoches();
        TablaID.getItems().setAll(listadoCoches);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cocheCRUD = new CocheCRUD();
        cocheCRUD.conectarBD();
        String[] listaCoches = {"SUV", "Deportivo", "Todoterreno", "Familiar", "Monovolumen"};
        ObservableList<String> observableList = FXCollections.observableArrayList(listaCoches);
        tipoID.setItems(observableList);

        matriculaIDTabla.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        modeloIDTabla.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaIDTabla.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tipoIDTabla.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        cargarCoches();
    }

    public void seleccionarFila(javafx.scene.input.MouseEvent mouseEvent) {
        cocheSeleccionado = TablaID.getSelectionModel().getSelectedItem();
        if (cocheSeleccionado != null) {
            String matricula = cocheSeleccionado.getMatricula();
            String marca = cocheSeleccionado.getMarca();
            String modelo = cocheSeleccionado.getModelo();
            String tipo = cocheSeleccionado.getTipo();

            matriculaID.setText(matricula);
            modeloID.setText(modelo);
            marcaID.setText(marca);
            tipoID.setValue(tipo);
        }
    }

    public void limpiarCampos() {
        tipoID.setValue(null);
        modeloID.clear();
        matriculaID.clear();
        marcaID.clear();
    }
}

