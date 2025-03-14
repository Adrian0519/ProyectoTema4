package ConsultasXml;

import org.basex.examples.api.BaseXClient;

import java.io.IOException;

public class ConXml {

    public void remplazar(BaseXClient conexion,int id,String remplazo, String datoRemplazo){
        try {
            String consulta=("for $id in db:get('videojuegos')/videojuegos/videojuego[id= '"+ id +"'] " +
                    "return replace value of node $id/"+remplazo+" with '"+datoRemplazo+"'");
            conexion.execute(consulta);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
