package ConsultasXml;

import org.basex.examples.api.BaseXClient;

import java.io.IOException;

public class ConXml {

    public void remplazar(BaseXClient conexion,int id,String remplazo, String datoRemplazo){
        try {
            String consulta = String.format("""
                for $v in db:get('videojuegos')//videojuegos/videojuego[id=%d]
                return replace value of node $v/%s with '%s'
            """, id, remplazo, datoRemplazo);
            conexion.execute("xquery " + consulta);
            System.out.println("Cambios aplicados");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
