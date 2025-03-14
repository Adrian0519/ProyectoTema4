package ConsultasXml;

import org.basex.examples.api.BaseXClient;

import java.io.IOException;

public class ConXml {

    public void remplazar(BaseXClient conexion,int id,String remplazo, String datoRemplazo){
        try {
            conexion.execute("for $id in /videojuegos/videojuego[id= '"+ id +"'] " +
                    "return(replace value of node $id/"+remplazo+" with '"+datoRemplazo+"')");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
