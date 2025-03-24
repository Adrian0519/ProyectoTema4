package ConsultasJson;

import ConsultasXml.ConXml;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasJson {
    private static MongoClient session= null;
    private static MongoDatabase mongoDatabase=null;
    private static Scanner scanner=new Scanner(System.in);
    ConXml conXml=new ConXml();
    String correo = null;

    public ConsultasJson() {
        try {
            Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
            session=MongoClients.create("mongodb://localhost:27017");
            mongoDatabase=session.getDatabase("comercio");
        } catch (Exception e) {
            System.out.println("Error en la creacion");
        }
    }

    public static MongoDatabase getConexion(){
        if (session==null){
            new ConsultasJson();
        }
        return mongoDatabase;
    }

    public static void closeConection(){
        if (session!=null){
            session.close();
            System.out.println("Conexion cerrada");
        }
    }

    public void  crearUsuario(String correo,String nombre, int edad, String direccion){
        MongoCollection<Document>coleccionUsuarios=mongoDatabase.getCollection("usuarios");
        if (coleccionUsuarios.find(Filters.eq("correo",correo)).first()!=null){
            System.out.println("El correo ya esta registrado");
            return;
        }
        Document nuevoUsuario=new Document("_id",correo)
                .append("nombre",nombre)
                .append("email",correo)
                .append("edad",edad)
                .append("direccion",direccion);
        coleccionUsuarios.insertOne(nuevoUsuario);
        System.out.println("Se inserto correctamente el usuario " + nombre);

    }

    public void comprobarUsuario(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Dime tu correo ");
        correo= scanner.nextLine();
        MongoCollection<Document>collectionUsuarios= mongoDatabase.getCollection("usuarios");
        Document documenCorreos=collectionUsuarios.find(Filters.eq("_id",correo)).first();
        if (documenCorreos!=null){
            System.out.println("Se inicio sesion exitosamente");
        }else {
            System.out.println("El correo no pertenece a ninguna cuenta");
        }
    }

    public void borrarUsuario(){
      if (correo == null){
          System.out.println("tienes que iniciar sesion");
          comprobarUsuario();
      }
      MongoCollection<Document>collectionUsuarios=mongoDatabase.getCollection("usuarios");
      MongoCollection<Document>collectionCarrito=mongoDatabase.getCollection("carritos");
      Document documentCorreos=collectionUsuarios.find(Filters.eq("_id",correo)).first();
      if (documentCorreos!=null){
          collectionUsuarios.deleteOne(Filters.eq("_id",correo));
          collectionCarrito.deleteOne(Filters.eq("_id",correo));
          System.out.println("Eliminado de forma exitosa");
      }
      correo = null;
    }

    public void modificar(){
    comprobarUsuario();
    if (correo==null){
        return;
    }else {

    }
    }
//Todo acabar esta cosa
    public void AgregarAlcarrito(){
        comprobarUsuario();
        if (correo==null){
            return;
        }else {
            String videojuego;
            MongoCollection<Document>collectionUsuarios= mongoDatabase.getCollection("usuarios");
            MongoCollection<Document>collectionCarrito=mongoDatabase.getCollection("carritos");
            Document documentCuenta=collectionUsuarios.find(Filters.eq("_id",correo)).first();
            int edad=documentCuenta.getInteger("edad");
            System.out.println(edad);
            conXml.videojuegosEdadMenor(edad);
            Document documentCarritoCuenta=collectionCarrito.find(Filters.eq("_id",correo)).first();
            if (documentCarritoCuenta==null){
                Document nuevoCarrito=new Document()
                        .append("_id",correo);
                collectionCarrito.insertOne(nuevoCarrito);
                System.out.println("Que juego quieres agregar al carrito");
            }else {
                System.out.println(documentCarritoCuenta);
            }
        }
    }
    public void mostrarCarro(){
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
        }
        MongoCollection<Document>mongoCollection=mongoDatabase.getCollection("carritos");
        Document document=mongoCollection.find(Filters.eq("_id",correo)).first();
        System.out.println(document);
    }

    public void mostrarCompras(){
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
        }
        MongoCollection<Document>mongoCollection=mongoDatabase.getCollection("compras");
        Document document = mongoCollection.find(Filters.eq("_id",correo)).first();
        System.out.println(document);
    }
}
