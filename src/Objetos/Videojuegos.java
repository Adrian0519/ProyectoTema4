package Objetos;

public class Videojuegos {
    private int id;
    private String titulo;
    private String descripcion;
    private double precio;
    private int disponibilidad;
    private String genero;
    private String desarrolladores;
    private int edadMinimaRecomendada;
    private String plataforma;

    public Videojuegos(int id, String titulo, String descripcion, double precio, int disponibilidad, String genero, String desarrolladores, int edadMinimaRecomendada, String plataforma) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.disponibilidad = disponibilidad;
        this.genero = genero;
        this.desarrolladores = desarrolladores;
        this.edadMinimaRecomendada = edadMinimaRecomendada;
        this.plataforma = plataforma;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDecripcion() {
        return descripcion;
    }

    public void setDecripcion(String decripcion) {
        this.descripcion = decripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesarrolladores() {
        return desarrolladores;
    }

    public void setDesarrolladores(String desarrolladores) {
        this.desarrolladores = desarrolladores;
    }

    public int getEdadMinimaRecomendada() {
        return edadMinimaRecomendada;
    }

    public void setEdadMinimaRecomendada(int edadMinimaRecomendada) {
        this.edadMinimaRecomendada = edadMinimaRecomendada;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public String toString() {
        return "<videojuego>\n" +
                "    <id>" + id + "</id>\n" +
                "    <titulo>" + titulo + "</titulo>\n" +
                "    <descripcion>" + descripcion + "</descripcion>\n" +
                "    <precio>" + precio + "</precio>\n" +
                "    <disponibilidad>" + disponibilidad + "</disponibilidad>\n" +
                "    <genero>" + genero + "</genero>\n" +
                "    <desarrolladores>" + desarrolladores + "</desarrolladores>\n" +
                "    <edad_minima_recomendada>" + edadMinimaRecomendada + "</edad_minima_recomendada>\n" +
                "    <plataforma>" + plataforma + "</plataforma>\n" +
                "</videojuego>";
    }
}
