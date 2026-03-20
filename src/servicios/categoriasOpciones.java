package servicios;

import controladores.Inicio;
import entidades.Categorias;
import entidades.Movimientos;

/**
 * Clase que gestiona las operaciones relacionadas con las categorías.
 * Permite crear nuevas categorías y mostrar las existentes.
 */
public class categoriasOpciones {

    /**
     * Crea una nueva categoría.
     * Solicita al usuario el nombre de la categoría y el tipo de movimiento
     * El tipo de movimiento(Ingreso, Gasto o Ahorro)
     * Genera un id único
     * Añade la categoría a la lista de categorías de Inicio.
     */
    public void crearCategoria() {
        int idCategoria;
        idCategoria = Inicio.ultimaCategoria++;//Se establece el id en base al ultimo que haya y este ultimo campo se modifica.
        Inicio.ultimaCategoria = idCategoria;

        System.out.println("Introduzca el nombre de la categoría que desea crear:");//Pide el nombre.
        String nombreCategoria = Inicio.sc.next();//Guarda el nombre.

        boolean pedirMovimientoDeseado = true;//Se pide el tipo de movimiento hasta que sea alguna de las opciones correctas.
        String tipoMetido = "";
        do {
            System.out.println("Introduzca el tipo de movimiento que va a tener esta categoría (Ingreso/Gasto/Ahorro): ");
            String tipoDeMovimiento = Inicio.sc.next();
            if (tipoDeMovimiento.equals("Ingreso") || tipoDeMovimiento.equals("Gasto") || tipoDeMovimiento.equals("Ahorro")) {
                tipoMetido = tipoDeMovimiento;
                pedirMovimientoDeseado = false;
            }
        } while (pedirMovimientoDeseado);

        Categorias c = new Categorias(idCategoria, nombreCategoria, tipoMetido);//Se crea la nueva categoría.

        Inicio.listaCategorias.add(c);//se guarda la nueva categoría en la lista de categorías.
    }

    /**
     * Muestra todas las categorías que tienen movimientos asociados.
     * Recorre la lista de categorías y la lista de movimientos, y
     * muestra por pantalla solo las categorías que coinciden con el tipo
     * de movimiento de algún movimiento registrado.
     */
    public void mostrarCategorias() {
        for (Categorias ct : Inicio.listaCategorias) {//Se recorre la lista de categorías.
            for (Movimientos mv : Inicio.listaMovimientos) {//Se recorre la lista de movimientos.
                if (mv.getTipoDeMovimiento().equals("Ingresos") && ct.getTipoDeMovimientoParaLaCategoria().equals("Ingresos")) { //Comprueba si el tipo de movimientos de ambos es de ingresos y después los muetra.
                    System.out.println(ct.toString());
                    break;
                }
                if (mv.getTipoDeMovimiento().equals("Gastos") && ct.getTipoDeMovimientoParaLaCategoria().equals("Gastos")) {//Comprueba si el tipo de movimientos de ambos es de gastos y después los muetra.
                    System.out.println(ct.toString());
                    break;
                }
            }
        }
    }
}