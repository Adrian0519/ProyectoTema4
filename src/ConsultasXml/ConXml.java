package ConsultasXml;

import org.basex.examples.api.BaseXClient;

import java.io.IOException;

public class ConXml {

    private static BaseXClient session;

    public void remplazar(int id,String remplazo, String datoRemplazo){
        try {
            String consulta = String.format("""
                for $v in /videojuegos/videojuego[id=%d]
                return replace value of node $v/%s with '%s'
            """, id, remplazo, datoRemplazo);
            session.execute("xquery" + consulta);
            System.out.println("Cambios aplicados");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void eliminarXID(int id){
        try {
            String consulta = String.format("""
                for $videojuegos in /videojuegos/videojuego[id=%d]
                return delete node $videojuegos
                """, id);
            session.execute("xquery " + consulta);
            System.out.println("Juego eliminado");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filtrarVideojuegos(){
        try {
        String consulta= String.format("""
                  for $videojuego in /videojuegos/videojuego
                            order by $videojuego/plataforma, $videojuego/titulo
                            return <videojuego>
                                { $videojuego/id }
                                { $videojuego/titulo }
                                { $videojuego/precio }
                                { $videojuego/disponibilidad }
                                { $videojuego/edad_minima_recomendada }
                                { $videojuego/plataforma }
                            </videojuego>
                """);
        String datos=session.execute("xquery " + consulta);
        System.out.println(datos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConXml() {
        try {
            BaseXClient baseXClient = new BaseXClient("localhost", 1984, "admin", "abc123");
            baseXClient.execute("open videojuegos");
            this.session=baseXClient;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
