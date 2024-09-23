import obj.CRUDCliente;
import obj.Usuario;

import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static Scanner sc = new Scanner(System.in);
    public static ArrayList<Usuario> listaClientes = new ArrayList<>();
    public static void main(String[] args) {
        int opt;
        do{
            try {
                System.out.println("""
                    Elige qué opción quieres llevar a cabo:
                    1. Ingresar Usuario
                    2. Buscar Usuario
                    3. Ver ingresos
                    0. Salir""");
                opt = Integer.parseInt(sc.nextLine());
                switch (opt){ //El switch sirve para acceder a métodos de la clase main
                    case 1:
                        ingresarUsuario();
                        break;
                    case 2:
                        buscarUsuario();
                        break;
                    case 3:
                        System.out.println(ingresos());
                        break;
                    case 0:
                        System.out.println("Vuelve pronto.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
                }
            }catch (NumberFormatException e){
                System.out.println("La opción solo puede ser un valor numérico");
                opt = -1; //Para controlar la excepción dentro del bucle, cuando salte, le doy un valor a la variable que no cumpla con la condición del switch.
                // Con esto controlo la excepción y no permito que el programa se cierre porque metan una letra sin querer, sino que pidan de vuelta otro número.
            }
            System.out.println();
        }while (opt != 0);
        System.out.println("Hasta luego!");
    }

    private static String ingresos() {
        return String.format("De los usuarios, se percibe un total de %.02f euros", CRUDCliente.totalIngresos(listaClientes)); //Se accede a la clase estática y saca el total de ingresos
        //El String format sirve para ajustar los decimales del valor dado, ya que a veces puede dar resultados raros como 0.1000234 por algún motivo
    }

    private static void buscarUsuario() {
        System.out.println("Ingresa el identificador del usuario: ");
        String identificador = sc.nextLine();
        Usuario u = CRUDCliente.buscarUsuario(listaClientes,identificador);
        if (u != null){
            System.out.println(u.toString()); //Si existe, muestra los datos del usuario que ha encontrado
        } else {
            System.out.println("El usuario no existe");
        }
    }

    private static void ingresarUsuario() {
        String id = "";
        do {
            System.out.println("Ingresa el identificador del usuario: ");
            id = sc.nextLine();
            if (CRUDCliente.buscarUsuario(listaClientes,id) != null){
                System.out.println("El usuario ya existe");
                id = ""; //Si existe el usuario, se vacía el campo del identificador, por lo que se repite el bucle
            }
        }while (id.isEmpty());
        System.out.println("Escribe su contraseña: ");
        String passwd = sc.nextLine();
        String opcion;
        boolean premium = false;
        do {
            System.out.println("El usuario es premium? s/n");
            opcion = sc.nextLine();
            if (opcion.equals("n") || opcion.equals("s")){
                premium = opcion.equals("s");
                System.out.println("Comprendido, el usuario es " + (premium ? "":"no ") + "premium."); //El operador ternario sive para hacer la diferencia entre premium y no premium.
            } else {
                System.out.println("Tienes que elegir entre s y n");
            }
            System.out.println(); //Un espacio, esto es mera estética
        }while (!opcion.equals("n") && !opcion.equals("s")); //Se repite el bucle mientras sea distinto a n y a s
        double descuento;
        double tarifa = premium ? 35.5 : 20.5; //Esta variable cambia en función de si el usuario es o no premium. Si es premium su valor será 35.5, mientras que si no lo es, será 20.5
        do{
            try {
                System.out.println("Elige el descuento (Al ser un usuario " + (premium ? "" : "no ") + "premium, su tarifa es de " + tarifa + " euros)");
                descuento = Double.parseDouble(sc.nextLine());
                if (descuento < 0){
                    System.out.println("El descuento no puede ser un número negativo");
                } else if (descuento >= tarifa){
                    System.out.println("El descuento no puede ser mayor o igual a la tarifa");
                }
            }catch (NumberFormatException e){
                System.out.println("El descuento solo puede tener un valor numerico");
                descuento = -1; //Para controlar la excepción dentro del bucle y que no se salga, al saltar la excepción, da un valor a la variable que no entra dentro de la condición del bucle
            }
        }while (descuento<0 || descuento>=tarifa); //Se repite mientras el valor del descuento sea menor a 0 (no tiene sentido un descuento negativo) o igual o mayor a la tarifa de su suscripción.
        Usuario u = new Usuario(id, passwd, descuento, premium);
        listaClientes.add(u);
        System.out.println("El usuario se ha ingresado exitosamente");
    }
}