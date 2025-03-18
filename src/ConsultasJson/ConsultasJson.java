package ConsultasJson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;


import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasJson {
    private static MongoClient session= null;
    private static MongoDatabase mongoDatabase=null;

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


}
