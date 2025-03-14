package ConsultasXml;

import org.basex.examples.api.BaseXClient;

import java.io.IOException;

public class ConXml {

    public static void remplazar(BaseXClient conexion,int id,String remplazo, String datoRemplazo){
        try {
            conexion.execute("""
                    for $id in /videojuegos/videojuego[id= '%d']
                    return(replace value of node $id/%s with '%s')""".formatted(id,remplazo,datoRemplazo));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
