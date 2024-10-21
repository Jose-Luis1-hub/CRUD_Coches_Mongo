package com.example.crud_coches_mongo.Clases;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.FileInputStream;
import java.util.Properties;

public class ConnectionDB {

    public static com.mongodb.MongoClient conectar() {
        Properties prop = new Properties();
        String host = "";
        String port = "";
        String author = "";
        String username = "";
        String password = "";
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/com/example/crud_coches_mongo/Database.properties");
            prop.load(fis);

            host=String.valueOf(prop.get("host"));
            port=String.valueOf(prop.get("port"));
            author=String.valueOf(prop.get("author"));
            username=String.valueOf(prop.get("username"));
            password=String.valueOf(prop.get("password"));

            final com.mongodb.MongoClient conexion = new com.mongodb.MongoClient(new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=" + author));
            System.out.println("Conectada correctamente a la BD");
            return conexion;
        } catch (Exception e) {
            System.out.println("Conexion Fallida");
            System.out.println(e);
            return null;
        }
    }

    public static void desconectar(MongoClient con) {
        con.close();
    }

}