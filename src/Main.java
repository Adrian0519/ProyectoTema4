import ConsultasJson.ConsultasJson;
import ConsultasXml.ConXml;
import com.mongodb.client.MongoDatabase;
import org.basex.examples.api.BaseXClient;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static MongoDatabase mongoDatabase;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConXml conXml = new ConXml();
        mongoDatabase= ConsultasJson.getConexion();
        ConsultasJson consultasJson=new ConsultasJson();
        int opcion = 0;
            while (opcion != 19) {
                System.out.println("1.-Modificar el valor de un elemento de un XML según un ID.\n" +
                        "2.-Eliminar un videojuego según su ID.\n" +
                        "3.-Obtener todos los videojuegos ordenados por plataforma y en segundo lugar por título (se mostrarán los siguientes campos: id, titulo, precio, disponibilidad, edad_minima_recomendada y plataforma).\n" +
                        "4.-Listar videojuegos con una edad_minima_recomendada menor o igual a X años (se mostrarán los siguientes campos: id, titulo, precio, disponibilidad, edad_minima_recomendada y plataforma). Se deberá mostrar la información ordenada según la edad_minima_recomendada.\n" +
                        "5.-Mostrar la plataforma, el titulo y el precio del videojuego más barato para cada plataforma. En el caso de haber varios se devolverá el de la primera posición.\n" +
                        "6.-Mostrar el titulo y el genero de aquellos videojuegos cuya descripcion incluya una subcadena, independientemente del uso de mayúsculas o minúsculas. Se deberá mostrar la información ordenada alfabéticamente según el genero.\n" +
                        "7.-Mostrar la cantidad total de videojuegos para cada plataforma (teniendo en cuenta el elemento disponibilidad) y calcular el porcentaje que representa respecto al total de videojuegos. Se deberá mostrar la información ordenada de forma descendente por la cantidad de videojuegos.\n" +
                        "8.-Mostrar el precio que costaría comprar todos los videojuegos disponibles (teniendo en cuenta el precio de cada videojuego y la disponibilidad de cada uno).\n" +
                        "9.-Crear un nuevo usuario (no podrá haber email repetidos).\n" +
                        "10.-Identificar usuario según el email. Dado el email se obtendrá el id del usuario de forma que las siguientes consultas se harán sobre ese usuario. Para cambiar de usuario se tendrá que volver a seleccionar esta opción.\n" +
                        "11.-Borrar un usuario.\n" +
                        "12.-Modificar el valor de un campo de la información del usuario.\n" +
                        "13.-Añadir videojuegos al carrito del usuario. Se mostrará la lista de videojuegos cuya edad_minima_recomendada sea inferior o igual a la del usuario actual y se pedirá: id del videojuego y cantidad, así como si se desea seguir introduciendo más videojuegos.\n" +
                        "14.-Mostrar el carrito del usuario. Se mostrarán los datos del carrito y el coste total.\n" +
                        "15.-Comprar el carrito del usuario. Se mostrará el contenido del carrito junto con una orden de confirmación. Si la orden es positiva se pasarán todos los videojuegos a formar parte de una nueva compra y desaparecerán del carrito.\n" +
                        "16.-Mostrar las compras del usuario, incluyendo la información de la fecha de cada compra.\n" +
                        "17.-Teniendo en cuenta todos los usuarios, calcular el coste de cada carrito y listar los resultados ordenados por el total de forma descendente.\n" +
                        "18.-Teniendo en cuenta todos los usuarios, calcular el total gastado por cada usuario en todas sus compras y listar los resultados ordenados por el total de forma ascendente.\n" +
                        "19.-Salir");
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        System.out.println("Pasame el id");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Dime el parametro que quieras remplazar");
                        System.out.println("1-titulo" +
                                "2-descripcion" +
                                "3-precio" +
                                "4-disponibilidad" +
                                "5-genero" +
                                "6-desarrollador" +
                                "7-edad minima" +
                                "8-plataforma" +
                                "0-salir");
                        int remplazoElegir = scanner.nextInt();
                        scanner.nextLine();
                        String valorElegido;
                        String nuevoDato;
                        switch (remplazoElegir) {
                            case 1:
                                valorElegido = "titulo";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar(id, valorElegido, nuevoDato);
                                break;
                            case 2:
                                valorElegido = "descripcion";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 3:
                                valorElegido = "precio";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 4:
                                valorElegido = "disponibilidad";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 5:
                                valorElegido = "genero";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 6:
                                valorElegido = "desarrollador";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 7:
                                valorElegido = "edad_minima_recomendada";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 8:
                                valorElegido = "plataforma";
                                System.out.println("Dime el valor que tendra ahora");
                                nuevoDato = scanner.nextLine();
                                conXml.remplazar( id, valorElegido, nuevoDato);
                                break;
                            case 0:
                                System.out.println("Adios");
                                break;
                            default:
                                System.out.println("Dato no correcto");
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("Inserta la id que deseas eliminar");
                        int idEliminar = scanner.nextInt();
                        scanner.nextLine();
                        conXml.eliminarXID( idEliminar);
                        break;
                    case 3:
                        conXml.filtrarVideojuegos();
                        break;
                    case 4:
                        System.out.println("Dime la edad maxima que quieras");
                        int edad= scanner.nextInt();
                        conXml.videojuegosEdadMenor(edad);
                        break;
                    case 5:
                        conXml.videojuegosBaratoPlataforma();
                        break;
                    case 6:
                        System.out.println("Dime que quieres que contenga el videojuego en su descripcion");
                        String cadenaBusqueda= scanner.nextLine().toLowerCase();
                        conXml.buscarCadenaCaracteres(cadenaBusqueda);
                        break;
                    case 7:
                        conXml.consultaJuegosPlataforma();
                        break;
                    case 8:
                        conXml.consutaPrecioTotal();
                        break;
                    case 9:
                        System.out.println("Dime el nombre de usuario");
                        String usuarioRegistro= scanner.nextLine();
                        System.out.println("Dime un correo para insertarlo");
                        String correoRegistro= scanner.nextLine();
                        System.out.println("Dime la edad");
                        int edadRegistro=scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Por ultimo dime la direccion");
                        String direccion= scanner.nextLine();
                        consultasJson.crearUsuario(correoRegistro,usuarioRegistro,edadRegistro,direccion);
                        break;
                    case 10:
                        consultasJson.comprobarUsuario();
                        break;
                    case 11:
                        consultasJson.borrarUsuario();
                        break;
                    case 12:
                        System.out.println("1.-nombre" +
                                "2.-edad" +
                                "3.-direccion");
                        break;
                    case 13:

                        break;
                    case 14:
                        consultasJson.AgregarAlcarrito();
                        break;
                    case 15:
                        consultasJson.mostrarCarro();
                        break;
                    case 16:
                        consultasJson.mostrarCompras();
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        System.out.println("Adios");
                        break;
                    default:
                        System.out.println("Numero no valido");
                        break;
                }
            }
            ConsultasJson.closeConection();
        }
    }







