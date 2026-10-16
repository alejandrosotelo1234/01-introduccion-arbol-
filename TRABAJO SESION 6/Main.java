import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== EJERCICIO 1: 50,30,70,20,40,60,80 =====");
        ArbolBST ejercicio = new ArbolBST();
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int v : valores) ejercicio.insertar(v);
        ejercicio.imprimir();
        System.out.println("Inorden: ");
        ejercicio.inorden();

        System.out.println("===== EJERCICIO 2: CAMINOS DE BUSQUEDA =====");
        System.out.println("Buscar 60: " + ejercicio.buscarConCamino(60));
        System.out.println("Buscar 65: " + ejercicio.buscarConCamino(65));

        int[] aleatorios = {4500, 2300, 6700, 1200, 3400, 5600, 7800, 900, 1800, 2900, 3900, 5100, 6100, 7200, 8900};
        int[] ordenados = aleatorios.clone();
        Arrays.sort(ordenados);
        int[] busquedas = {900, 3900, 7200, 8900, 5100};

        ArbolBST arbolAleatorio = new ArbolBST();
        for (int c : aleatorios) arbolAleatorio.insertar(c);

        ArbolBST arbolOrdenado = new ArbolBST();
        for (int c : ordenados) arbolOrdenado.insertar(c);

        System.out.println();
        System.out.println("===== ARBOL CON INSERCION ALEATORIA =====");
        arbolAleatorio.imprimir();
        System.out.println();
        System.out.println("===== ARBOL CON INSERCION ORDENADA =====");
        arbolOrdenado.imprimir();

        System.out.println();
        System.out.println("===== BUSQUEDAS (cantidad de nodos visitados) =====");
        System.out.printf("%-10s %-12s %-12s%n", "Codigo", "Aleatorio", "Ordenado");
        for (int b : busquedas) {
            arbolAleatorio.reiniciarComparaciones();
            arbolOrdenado.reiniciarComparaciones();
            boolean ra = arbolAleatorio.buscar(b);
            boolean ro = arbolOrdenado.buscar(b);
            System.out.printf("%-10d %-12d %-12d (encontrado: %b / %b)%n", b, arbolAleatorio.getComparaciones(), arbolOrdenado.getComparaciones(), ra, ro);
        }

        int totalA = 0;
        int totalO = 0;
        for (int b : busquedas) {
            arbolAleatorio.reiniciarComparaciones();
            arbolOrdenado.reiniciarComparaciones();
            arbolAleatorio.buscar(b);
            arbolOrdenado.buscar(b);
            totalA += arbolAleatorio.getComparaciones();
            totalO += arbolOrdenado.getComparaciones();
        }

        System.out.println();
        System.out.println("===== TABLA COMPARATIVA =====");
        System.out.printf("%-22s %-12s %-12s%n", "Metrica", "Aleatorio", "Ordenado");
        System.out.printf("%-22s %-12d %-12d%n", "Altura (niveles)", arbolAleatorio.altura(), arbolOrdenado.altura());
        System.out.printf("%-22s %-12d %-12d%n", "Comparaciones totales", totalA, totalO);
        System.out.printf("%-22s %-12.2f %-12.2f%n", "Promedio por busqueda", totalA / 5.0, totalO / 5.0);
    }
}
