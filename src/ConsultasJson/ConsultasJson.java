package ConsultasJson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasJson {
    private static MongoClient session= null;
    private static MongoDatabase mongoDatabase=null;

    private ConsultasJson() {
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
}
