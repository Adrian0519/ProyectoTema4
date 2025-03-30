package ConsultasJson;

import ConsultasXml.ConXml;
import Objetos.Videojuegos;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.basex.BaseX;
import org.basex.examples.api.BaseXClient;
import org.bson.Document;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasJson {
    private static MongoClient session= null;
    private static MongoDatabase mongoDatabase=null;
    private static Scanner scanner=new Scanner(System.in);
    String correo = null;
    private static BaseXClient sessionBX;
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

    public void AgregarAlCarrito() throws IOException {
        if (correo == null){
            System.out.println("tienes que iniciar sesion");
            comprobarUsuario();
            return;
        }
            MongoCollection<Document>collectionUsuarios= mongoDatabase.getCollection("usuarios");
            MongoCollection<Document>collectionCarrito=mongoDatabase.getCollection("carritos");
            Document documentCuenta=collectionUsuarios.find(Filters.eq("email",correo)).first();
            int edad=documentCuenta.getInteger("edad");
            String id= documentCuenta.getString("_id");
            Document documentCarritoCuenta=collectionCarrito.find(Filters.eq("_id",correo)).first();
        String consulta= String.format("""
                for $v in //videojuego
                where number($v/edad_minima_recomendada) <= %d
                return 
                  data($v/id)  ","  
                  data($v/titulo)  ","  
                  data($v/precio)  ","  
                  data($v/disponibilidad)
            """, edad);
       BaseXClient.Query query=sessionBX.query(consulta);
       ArrayList<Videojuegos>listaVideojuegos=new ArrayList<>();
       while (query.more()){
           String videojuego= query.next();
           String[]datos=videojuego.split(",");
           int idV=Integer.parseInt(datos[0]);
           String titulo=datos[1];
           double precio=Double.parseDouble(datos[2]);
           int disponiblidad=Integer.parseInt(datos[3]);
           Videojuegos videojuegos=new Videojuegos(idV,titulo,"",precio,disponiblidad,"","",0,"");
           listaVideojuegos.add(videojuegos);
           System.out.println("----------------------------------");
           System.out.println("id: "+videojuegos.getId());
           System.out.println("titulo: "+videojuegos.getTitulo());
           System.out.println("precio: "+videojuegos.getPrecio());
           System.out.println("disponibilidad:" +videojuegos.getDisponibilidad());
           System.out.println("----------------------------------");
       }query.close();
            if (documentCarritoCuenta==null){
                Document nuevoCarrito=new Document()
                        .append("_id",id);
                collectionCarrito.insertOne(nuevoCarrito);
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



    public void mostrarCompras() {
        if (correo == null) {
            System.out.println("Tienes que iniciar sesión");
            comprobarUsuario();
            return;
        }

        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("compras");

        List<Document> compras = mongoCollection.find(Filters.eq("email", correo)).into(new ArrayList<>());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Document compra : compras) {
            Date fechaCompra = compra.getDate("fecha_compra");
            String fechaFormateada = (fechaCompra != null) ? dateFormat.format(fechaCompra) : "Fecha no disponible";

            compra.put("fecha_compra", fechaFormateada);
            System.out.println(compra.toJson());
        }
    }



    public void RealizarCompra() {
        if (correo == null) {
            System.out.println("Tienes que iniciar sesión");
            comprobarUsuario();
            return;
        }

        double total = 0;
        MongoCollection<Document> usuarios = mongoDatabase.getCollection("usuarios");
        MongoCollection<Document> carritos = mongoDatabase.getCollection("carritos");
        MongoCollection<Document> compras = mongoDatabase.getCollection("compras");

        Document document = usuarios.find(Filters.eq("email", correo)).first();
        if (document == null) {
            System.out.println("No se encontró un usuario con el correo: " + correo);
            return;
        }

        Document document1 = carritos.find(Filters.eq("_id", correo)).first();
        if (document1 == null) {
            System.out.println("No hay nada en el carrito");
            return;
        }

        ArrayList<Document> arrayList = (ArrayList<Document>) document1.get("productos");
        if (arrayList == null || arrayList.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        for (Document document2 : arrayList) {
            Integer cantidad = document2.getInteger("cantidad", 0);
            String titulo = document2.getString("nombre");
            Double precio = document2.getDouble("precio_unitario");

            total += cantidad * precio;
            System.out.println("El videojuego: " + titulo + " tiene un precio de: " + precio + " y quieres comprar " + cantidad);
        }

        System.out.println("Total a pagar: " + total);

        Document nuevaCompra = new Document()
                .append("email", correo)
                .append("videojuegos", arrayList)
                .append("total", total)
                .append("fecha_compra", new Date());

        compras.insertOne(nuevaCompra); // Inserta una nueva compra en lugar de actualizar

        carritos.deleteOne(Filters.eq("_id", correo));

        System.out.println("Su compra se realizó exitosamente");
    }

    public void mostrarCosteDCarritos() {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("carritos");

        ArrayList<Document> carritosList = mongoCollection.find().into(new ArrayList<>());

        for (Document carrito : carritosList) {
            double totalCarrito = 0;

            ArrayList<Document> productos = (ArrayList<Document>) carrito.get("productos");

            if (productos != null) {
                for (Document producto : productos) {
                    double precio = producto.getDouble("precio_unitario");
                    int cantidad = producto.getInteger("cantidad");
                    totalCarrito += precio * cantidad;
                }
            }

            carrito.append("totalCarrito", totalCarrito);
        }

        // Ordenar carritos de mayor a menor por "totalCarrito"
        carritosList.sort((c1, c2) -> Double.compare(c2.getDouble("totalCarrito"), c1.getDouble("totalCarrito")));

        // Mostrar los carritos ordenados
        for (Document carrito : carritosList) {
            System.out.println("Carrito de id_usuario " + carrito.getString("email") +
                    " tiene un coste total de: " + carrito.getDouble("totalCarrito"));
        }
    }







}
