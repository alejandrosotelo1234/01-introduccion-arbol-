public class ArbolBST {
    private NodoArbol<Integer> raiz;
    private int comparaciones;

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private NodoArbol<Integer> insertarRec(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null) return new NodoArbol<>(valor);
        if (valor < nodo.getDato()) nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), valor));
        else nodo.setDerecho(insertarRec(nodo.getDerecho(), valor));
        return nodo;
    }

    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null) return false;
        comparaciones++;
        if (valor == nodo.getDato()) return true;
        return valor < nodo.getDato() ? buscarRec(nodo.getIzquierdo(), valor) : buscarRec(nodo.getDerecho(), valor);
    }

    public String buscarConCamino(int valor) {
        StringBuilder camino = new StringBuilder();
        boolean encontrado = caminoRec(raiz, valor, camino);
        return camino + (encontrado ? " -> ENCONTRADO" : " -> NO EXISTE");
    }

    private boolean caminoRec(NodoArbol<Integer> nodo, int valor, StringBuilder camino) {
        if (nodo == null) return false;
        if (camino.length() > 0) camino.append(" -> ");
        camino.append(nodo.getDato());
        if (valor == nodo.getDato()) return true;
        return valor < nodo.getDato() ? caminoRec(nodo.getIzquierdo(), valor, camino) : caminoRec(nodo.getDerecho(), valor, camino);
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(NodoArbol<Integer> nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(alturaRec(nodo.getIzquierdo()), alturaRec(nodo.getDerecho()));
    }

    public int getComparaciones() {
        return comparaciones;
    }

    public void reiniciarComparaciones() {
        comparaciones = 0;
    }

    public void imprimir() {
        imprimirRec(raiz, 0);
    }

    private void imprimirRec(NodoArbol<Integer> nodo, int nivel) {
        if (nodo == null) return;
        imprimirRec(nodo.getDerecho(), nivel + 1);
        System.out.println("    ".repeat(nivel) + nodo.getDato());
        imprimirRec(nodo.getIzquierdo(), nivel + 1);
    }

    public void inorden() {
        inordenRec(raiz);
        System.out.println();
    }

    private void inordenRec(NodoArbol<Integer> nodo) {
        if (nodo == null) return;
        inordenRec(nodo.getIzquierdo());
        System.out.print(nodo.getDato() + " ");
        inordenRec(nodo.getDerecho());
    }
}
