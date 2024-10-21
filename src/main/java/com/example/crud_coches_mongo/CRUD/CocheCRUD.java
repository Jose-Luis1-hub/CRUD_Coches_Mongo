package com.example.crud_coches_mongo.CRUD;

import com.example.crud_coches_mongo.Clases.Alerta;
import com.example.crud_coches_mongo.Clases.Coche;
import com.example.crud_coches_mongo.Clases.ConnectionDB;
import com.example.crud_coches_mongo.Controller.CocheController;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import javafx.scene.control.Alert;

import java.util.ArrayList;


public class CocheCRUD {
    MongoCollection<Document> collection = null;
    String json;
    Document doc;

    public void insertarCoche(Coche coche) {
        if(coche == null) {
            System.out.println("El coche es nulo");
        } else {
            Gson gson = new Gson();
            json = gson.toJson(coche);
            doc = Document.parse(json);
            collection.insertOne(doc);
            Alerta.Info("Coche agregado correctamente", "ERROR");
        }
    }

    public ArrayList<Coche> obtenerCoches() {
        ArrayList<Coche> coches = new ArrayList<>();
        Gson gson = new Gson();
        for (Document doc : collection.find()) {
            Coche coche = gson.fromJson(doc.toJson(), Coche.class);
            coches.add(coche);
        }
        return coches;
    }


    public void conectarBD() {
        MongoClient con;
        try {
            con = ConnectionDB.conectar();
            MongoDatabase database = con.getDatabase("taller");
            database.createCollection("taller");
            collection = database.getCollection("taller");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void eliminar(String matricula) {
        collection.deleteOne(new Document("matricula", matricula));
    }

    public void modificarCoche(Coche coche) {
        Document doc = new Document("marca", coche.getMarca()).append("tipo", coche.getTipo()).append("modelo", coche.getModelo());
        collection.updateOne(Filters.eq("matricula", coche.getMatricula()), new Document("$set", doc));
    }
}
