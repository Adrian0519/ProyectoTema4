package ConsultasJson;

import ConsultasXml.ConXml;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
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
        if (coleccionUsuarios.find(Filters.eq("email",correo)).first()!=null){
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
//Si existe y le cambias el correo peta.
    public void comprobarUsuario(){
        System.out.println("Dime tu correo ");
        correo= scanner.nextLine();
        MongoCollection<Document>collectionUsuarios= mongoDatabase.getCollection("usuarios");
        Document documenCorreos=collectionUsuarios.find(Filters.eq("email",correo)).first();
        if (documenCorreos!=null){
            System.out.println("Se inicio sesion exitosamente");
        }else {
            System.out.println("El correo no pertenece a ninguna cuenta");
            correo=null;
        }
    }

    public void borrarUsuario(){
      if (correo == null){
          System.out.println("tienes que iniciar sesion");
          comprobarUsuario();
          return;
      }
      MongoCollection<Document>collectionUsuarios=mongoDatabase.getCollection("usuarios");
      MongoCollection<Document>collectionCarrito=mongoDatabase.getCollection("carritos");
      Document documentCorreos=collectionUsuarios.find(Filters.eq("email",correo)).first();
      String id= documentCorreos.getString("_id");
      if (documentCorreos!=null){
          collectionUsuarios.deleteOne(Filters.eq("email",correo));
          collectionCarrito.deleteOne(Filters.eq("_id",id));
          System.out.println("Eliminado de forma exitosa");
      }
      correo = null;
    }

    public void modificar(){
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
            return;
        }
            String campo, valor;
            int pick, edad;
            edad=0;
            valor="";
            System.out.println("1.-nombre" +
                    "2.-edad  " +
                    "3.-direccion  " +
                    "4.-correo  " +
                    "5.-cancelar  ");
            pick=scanner.nextInt();
            scanner.nextLine();
            switch (pick){
                case 1:
                    campo="nombre";
                    System.out.println("Introduzca el nombre");
                    valor=scanner.nextLine();
                    break;
                case 2:
                    campo="edad";
                    System.out.println("Introduzca la edad");
                    edad=scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 3:
                    campo="direccion";
                    System.out.println("Introduzca la direccion");
                    valor=scanner.nextLine();
                    break;
                case 4:
                    campo="email";
                    System.out.println("Introduzca el mail");
                    valor=scanner.nextLine();
                    break;
                case 5:
                    System.out.println("Operacion cancelada");
                    return;
                default:
                    System.out.println("Dato incorrecto");
                    return;
            }
            MongoCollection<Document>collectionListaUsuarios=mongoDatabase.getCollection("usuarios");
            if (campo.equalsIgnoreCase("edad")){
                collectionListaUsuarios.updateOne(Filters.eq("email",correo), Updates.set(campo,edad));
                System.out.println("Se actualizo la edad  ");

            }
            else if (campo.equalsIgnoreCase("email")){
            collectionListaUsuarios.updateOne(Filters.eq("email",correo), Updates.set(campo,valor));
            System.out.println("Se actualizo el correo, se cerrara la sesion por seguridad");
            correo=null;
            }
            else{
                collectionListaUsuarios.updateOne(Filters.eq("email",correo), Updates.set(campo,valor));
                System.out.println("Se actualizo el campo "+ campo);
            }

        }
//Todo acabar esta cosa
    public void AgregarAlcarrito(){
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
            return;
        }
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
    public void mostrarCarro(){
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
            return;
        }
        MongoCollection<Document>mongoCollection=mongoDatabase.getCollection("carritos");
        Document document=mongoCollection.find(Filters.eq("_id",correo)).first();
        System.out.println(document);
    }

    public void mostrarCompras(){
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
            return;
        }
        MongoCollection<Document>mongoCollection=mongoDatabase.getCollection("compras");
        Document document = mongoCollection.find(Filters.eq("_id",correo)).first();
        System.out.println(document);
    }
}
