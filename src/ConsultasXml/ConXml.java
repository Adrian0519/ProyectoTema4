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

    public void videojuegosEdadMenor(int id){
        try {
            String consulta= String.format("""
                    for $videojuego in /videojuegos/videojuego[edad_minima_recomendada<=%d]
                    order by number ($videojuego/edad_minima_recomendada) ascending
                    return $videojuego
                    """,id);
            String resultado=session.execute("xquery " + consulta);
            System.out.println(resultado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void videojuegosBaratoPlataforma(){
        try {
            String consulta=String.format("""
                    for $videojuegoPlata in distinct-values(/videojuegos/videojuego/plataforma)
                    let $juegosPlataforma := /videojuegos/videojuego[plataforma = $videojuegoPlata]
                    let $videojuegoBarato :=\s
                        for $juego in $juegosPlataforma
                        let $precio := $juego/precio
                        order by $precio ascending
                        return $juego
                    return
                        <result>
                           {$videojuegoPlata}
                            <videojuegoMasBarato>{$videojuegoBarato[1]/titulo}</videojuegoMasBarato>
                            <precio>{$videojuegoBarato[1]/precio}</precio>
                        </result>
                    """);
            String resultado= session.execute("xquery " + consulta);
            System.out.println(resultado);
        } catch (Exception e) {
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
