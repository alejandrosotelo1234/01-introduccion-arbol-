import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// ===================== NODO GENERAL =====================
class NodoGeneral<T> {
    private T dato;
    private NodoGeneral<T> padre;
    private final List<NodoGeneral<T>> hijos = new ArrayList<>();

    public NodoGeneral(T dato) {
        this.dato = dato;
    }

    public T getDato() { return dato; }
    public void setDato(T dato) { this.dato = dato; }
    public NodoGeneral<T> getPadre() { return padre; }
    public List<NodoGeneral<T>> getHijos() { return Collections.unmodifiableList(hijos); }

    /** Agrega un hijo y lo enlaza con este nodo como padre. */
    public NodoGeneral<T> agregarHijo(NodoGeneral<T> hijo) {
        hijo.padre = this;
        hijos.add(hijo);
        return hijo;
    }

    public boolean esRaiz() { return padre == null; }
    public boolean esHoja() { return hijos.isEmpty(); }

    /** Grado del nodo = número de hijos. */
    public int grado() { return hijos.size(); }

    /** Profundidad (= nivel) = aristas desde la raíz hasta este nodo. */
    public int profundidad() {
        int d = 0;
        NodoGeneral<T> actual = this;
        while (actual.padre != null) {
            d++;
            actual = actual.padre;
        }
        return d;
    }

    /** Altura = aristas del camino más largo hasta una hoja. Una hoja vale 0. */
    public int altura() {
        int max = 0;
        for (NodoGeneral<T> h : hijos) {
            max = Math.max(max, 1 + h.altura());
        }
        return max;
    }

    @Override
    public String toString() { return String.valueOf(dato); }
}

// ===================== ÁRBOL GENERAL =====================
class ArbolGeneral<T> {
    private NodoGeneral<T> raiz;

    public ArbolGeneral(T datoRaiz) {
        this.raiz = new NodoGeneral<>(datoRaiz);
    }

    public NodoGeneral<T> getRaiz() { return raiz; }

    /** Agrega un hijo con el dato dado bajo el nodo cuyo dato es 'datoPadre'. */
    public NodoGeneral<T> agregar(T datoPadre, T datoHijo) {
        NodoGeneral<T> padre = buscar(datoPadre);
        if (padre == null) {
            throw new IllegalArgumentException("No existe el padre: " + datoPadre);
        }
        if (buscar(datoHijo) != null) {
            throw new IllegalArgumentException("El nodo ya existe: " + datoHijo);
        }
        return padre.agregarHijo(new NodoGeneral<>(datoHijo));
    }

    /** Búsqueda en preorden. */
    public NodoGeneral<T> buscar(T dato) {
        return buscar(raiz, dato);
    }

    private NodoGeneral<T> buscar(NodoGeneral<T> nodo, T dato) {
        if (nodo.getDato().equals(dato)) return nodo;
        for (NodoGeneral<T> h : nodo.getHijos()) {
            NodoGeneral<T> r = buscar(h, dato);
            if (r != null) return r;
        }
        return null;
    }

    private NodoGeneral<T> exigir(T dato) {
        NodoGeneral<T> n = buscar(dato);
        if (n == null) throw new IllegalArgumentException("No existe el nodo: " + dato);
        return n;
    }

    // ---------- Relaciones ----------
    public NodoGeneral<T> padreDe(T dato) { return exigir(dato).getPadre(); }

    public List<NodoGeneral<T>> hijosDe(T dato) { return exigir(dato).getHijos(); }

    public List<NodoGeneral<T>> hermanosDe(T dato) {
        NodoGeneral<T> n = exigir(dato);
        List<NodoGeneral<T>> hermanos = new ArrayList<>();
        if (n.getPadre() != null) {
            for (NodoGeneral<T> h : n.getPadre().getHijos()) {
                if (h != n) hermanos.add(h);
            }
        }
        return hermanos;
    }

    // ---------- Clasificación de nodos ----------
    public List<NodoGeneral<T>> hojas() {
        List<NodoGeneral<T>> r = new ArrayList<>();
        recolectarHojas(raiz, r);
        return r;
    }

    private void recolectarHojas(NodoGeneral<T> n, List<NodoGeneral<T>> r) {
        if (n.esHoja()) r.add(n);
        for (NodoGeneral<T> h : n.getHijos()) recolectarHojas(h, r);
    }

    /** Nodos internos = los que tienen al menos un hijo (incluye la raíz). */
    public List<NodoGeneral<T>> nodosInternos() {
        List<NodoGeneral<T>> r = new ArrayList<>();
        recolectarInternos(raiz, r);
        return r;
    }

    private void recolectarInternos(NodoGeneral<T> n, List<NodoGeneral<T>> r) {
        if (!n.esHoja()) r.add(n);
        for (NodoGeneral<T> h : n.getHijos()) recolectarInternos(h, r);
    }

    // ---------- Subárbol ----------
    /** Nodos del subárbol cuya raíz es 'dato' (en preorden). */
    public List<NodoGeneral<T>> subarbol(T dato) {
        List<NodoGeneral<T>> r = new ArrayList<>();
        preorden(exigir(dato), r);
        return r;
    }

    private void preorden(NodoGeneral<T> n, List<NodoGeneral<T>> r) {
        r.add(n);
        for (NodoGeneral<T> h : n.getHijos()) preorden(h, r);
    }

    // ---------- Camino ----------
    /** Camino desde la raíz hasta el nodo dado. Su longitud es (tamaño - 1) aristas. */
    public List<NodoGeneral<T>> caminoDesdeRaiz(T dato) {
        List<NodoGeneral<T>> camino = new ArrayList<>();
        NodoGeneral<T> actual = exigir(dato);
        while (actual != null) {
            camino.add(actual);
            actual = actual.getPadre();
        }
        Collections.reverse(camino);
        return camino;
    }

    // ---------- Medidas ----------
    public int grado(T dato) { return exigir(dato).grado(); }

    /** Grado del árbol = mayor grado entre todos sus nodos. */
    public int gradoArbol() { return gradoArbol(raiz); }

    private int gradoArbol(NodoGeneral<T> n) {
        int max = n.grado();
        for (NodoGeneral<T> h : n.getHijos()) max = Math.max(max, gradoArbol(h));
        return max;
    }

    public int nivel(T dato) { return exigir(dato).profundidad(); }
    public int profundidad(T dato) { return exigir(dato).profundidad(); }
    public int altura(T dato) { return exigir(dato).altura(); }
    public int alturaArbol() { return raiz.altura(); }

    /** Nodos agrupados por nivel (índice 0 = nivel 0). */
    public List<List<NodoGeneral<T>>> porNiveles() {
        List<List<NodoGeneral<T>>> niveles = new ArrayList<>();
        List<NodoGeneral<T>> actual = new ArrayList<>();
        actual.add(raiz);
        while (!actual.isEmpty()) {
            niveles.add(actual);
            List<NodoGeneral<T>> siguiente = new ArrayList<>();
            for (NodoGeneral<T> n : actual) siguiente.addAll(n.getHijos());
            actual = siguiente;
        }
        return niveles;
    }

    // ---------- Impresión ----------
    public void imprimir() { imprimir(raiz, "", true); }

    private void imprimir(NodoGeneral<T> n, String prefijo, boolean ultimo) {
        if (n.esRaiz()) {
            System.out.println(n);
        } else {
            System.out.println(prefijo + (ultimo ? "└── " : "├── ") + n);
        }
        String nuevo = n.esRaiz() ? "" : prefijo + (ultimo ? "    " : "│   ");
        List<NodoGeneral<T>> hs = n.getHijos();
        for (int i = 0; i < hs.size(); i++) {
            imprimir(hs.get(i), nuevo, i == hs.size() - 1);
        }
    }

    // ---------- Reporte completo del taller ----------
    public void reporteCompleto(String titulo) {
        System.out.println("==================================================");
        System.out.println(" " + titulo);
        System.out.println("==================================================");
        imprimir();

        System.out.println("\nRaíz:            " + raiz);
        System.out.println("Hojas:           " + hojas());
        System.out.println("Nodos internos:  " + nodosInternos());
        System.out.println("Grado del árbol: " + gradoArbol());
        System.out.println("Altura del árbol:" + " " + alturaArbol());

        System.out.println("\nNiveles:");
        List<List<NodoGeneral<T>>> niveles = porNiveles();
        for (int i = 0; i < niveles.size(); i++) {
            System.out.println("  Nivel " + i + ": " + niveles.get(i));
        }

        System.out.println("\nDetalle por nodo:");
        System.out.printf("  %-28s %-24s %-6s %-6s %-6s %-6s%n",
                "Nodo", "Padre", "Grado", "Nivel", "Prof.", "Altura");
        for (List<NodoGeneral<T>> nivel : niveles) {
            for (NodoGeneral<T> n : nivel) {
                System.out.printf("  %-28s %-24s %-6d %-6d %-6d %-6d%n",
                        n, n.getPadre() == null ? "—" : n.getPadre(),
                        n.grado(), n.profundidad(), n.profundidad(), n.altura());
            }
        }

        System.out.println("\nHijos y hermanos:");
        for (List<NodoGeneral<T>> nivel : niveles) {
            for (NodoGeneral<T> n : nivel) {
                System.out.println("  " + n + " -> hijos: " + n.getHijos()
                        + " | hermanos: " + hermanosDe(n.getDato()));
            }
        }
        System.out.println();
    }
}

// ===================== PROGRAMA PRINCIPAL =====================
public class AnatomiaArbol {

    /** Árbol A del ejemplo resuelto. */
    static ArbolGeneral<String> arbolA() {
        ArbolGeneral<String> a = new ArbolGeneral<>("A");
        a.agregar("A", "B");
        a.agregar("A", "C");
        a.agregar("A", "D");
        a.agregar("B", "E");
        a.agregar("B", "F");
        a.agregar("F", "K");
        a.agregar("C", "G");
        a.agregar("D", "H");
        a.agregar("D", "I");
        a.agregar("D", "J");
        return a;
    }

    /** Árbol genealógico (descendientes del abuelo). Solo parentescos, sin nombres propios. */
    static ArbolGeneral<String> arbolGenealogico() {
        ArbolGeneral<String> f = new ArbolGeneral<>("Abuelo");

        // Hijos del abuelo: papá y tíos
        f.agregar("Abuelo", "Papá");
        f.agregar("Abuelo", "Tía");
        f.agregar("Abuelo", "Tío");

        // Hijos del papá: yo y mi hermana
        f.agregar("Papá", "Yo");
        f.agregar("Papá", "Hermana");
        f.agregar("Hermana", "Sobrino");

        // Hijos de los tíos: primos
        f.agregar("Tía", "Prima 1");
        f.agregar("Tía", "Primo");
        f.agregar("Tío", "Prima 2");
        return f;
    }

    public static void main(String[] args) {
        // ---- Parte 1: árbol A ----
        ArbolGeneral<String> a = arbolA();
        a.reporteCompleto("ÁRBOL A");

        System.out.println("Consultas puntuales sobre A:");
        System.out.println("  Padre de K:            " + a.padreDe("K"));
        System.out.println("  Hijos de D:            " + a.hijosDe("D"));
        System.out.println("  Hermanos de E:         " + a.hermanosDe("E"));
        System.out.println("  Subárbol de B:         " + a.subarbol("B"));
        System.out.println("  Camino raíz -> K:      " + a.caminoDesdeRaiz("K")
                + " (longitud " + (a.caminoDesdeRaiz("K").size() - 1) + ")");
        System.out.println("  Grado de D:            " + a.grado("D"));
        System.out.println("  Nivel / profundidad K: " + a.nivel("K"));
        System.out.println("  Altura de B:           " + a.altura("B"));
        System.out.println();

        // ---- Parte 2: árbol genealógico ----
        ArbolGeneral<String> f = arbolGenealogico();
        f.reporteCompleto("ÁRBOL GENEALÓGICO");

        System.out.println("Consultas puntuales sobre el árbol genealógico:");
        System.out.println("  Padre de Yo:        " + f.padreDe("Yo"));
        System.out.println("  Hijos del abuelo:            " + f.hijosDe("Abuelo"));
        System.out.println("  Hermanos de Yo:     " + f.hermanosDe("Yo"));
        System.out.println("  Tíos (hermanos de mi papá):  " + f.hermanosDe("Papá"));
        System.out.println("  Primos (hijos de los tíos):  "
                + f.hijosDe("Tía") + " y " + f.hijosDe("Tío"));
        System.out.println("  Subárbol de Papá:    " + f.subarbol("Papá"));
        System.out.println("  Camino hasta Sobrino: " + f.caminoDesdeRaiz("Sobrino"));
    }
}
