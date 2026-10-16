# Guia 4 - BST I: insercion y busqueda

Asignatura: Estructura de Datos II
Lenguaje: Java

## Archivos

- NodoArbol.java: nodo generico del arbol
- ArbolBST.java: arbol binario de busqueda con insercion y busqueda recursivas, altura y camino de busqueda
- Main.java: pruebas con datos aleatorios y ordenados

## Como ejecutar

    javac *.java
    java Main

## Convencion

Menores a la izquierda, mayores o iguales a la derecha.

## Datos de prueba

Aleatorio: 4500, 2300, 6700, 1200, 3400, 5600, 7800, 900, 1800, 2900, 3900, 5100, 6100, 7200, 8900

Ordenado: los mismos 15 codigos de menor a mayor.

Busquedas: 900, 3900, 7200, 8900, 5100

## Tabla comparativa

| Metrica | Aleatorio | Ordenado |
|---|---|---|
| Altura (niveles) | 4 | 15 |
| Comparaciones totales (5 busquedas) | 20 | 45 |
| Promedio por busqueda | 4.00 | 9.00 |

## Diagrama insercion aleatoria

                4500
              /      \
          2300        6700
         /    \      /    \
      1200   3400  5600   7800
      /  \   /  \  /  \   /  \
    900 1800 2900 3900 5100 6100 7200 8900

## Diagrama insercion ordenada

    900
      \
      1200
        \
        1800
          \
          2300
            \
            ... (continua hacia la derecha hasta 8900)

El arbol se convierte en una lista enlazada de 15 niveles.

## Analisis O(log n) frente a O(n)

En el arbol con insercion aleatoria los datos se reparten a ambos lados y la altura es cercana a log2(n). Para 15 nodos la altura es 4, por lo que cada busqueda recorre como maximo 4 nodos. La complejidad es O(log n).

Al insertar datos ya ordenados cada nuevo valor es mayor que todos los anteriores y siempre va a la derecha. El arbol se degenera en una lista de altura n, y buscar el ultimo valor obliga a recorrer los 15 nodos. La complejidad es O(n).

Con n = 15 se pasa de 4 a 15 niveles y de 20 a 45 comparaciones en las cinco busquedas. Si n crece la diferencia se vuelve enorme: con un millon de datos seria cercano a 20 comparaciones contra un millon.

## Ejercicios

- Insercion 50,30,70,20,40,60,80: arbol completo de 3 niveles con 50 en la raiz.
- Busqueda de 60 (existe): 50 -> 70 -> 60
- Busqueda de 65 (no existe): 50 -> 70 -> 60 -> llega a null
