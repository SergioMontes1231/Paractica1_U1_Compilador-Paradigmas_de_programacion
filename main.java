

public class Main {
    /**
    * Clase principal que demuestra el funcionamiento del analizador lexico.
    * Contiene el metodo main que ejecuta pruebas con diferentes ejemplos de codigo.
    * 
    * Atributos: No posee atributos de clase.
    */

    public static void main(String[] args) {
        /**
        * Método principal que ejecuta la demostración del analizador léxico.
        * Crea una instancia del analizador y procesa una lista de ejemplos predefinidos.
        * 
        * @param args argumentos de línea de comandos (no utilizados).
        */

        LexicalAnalyzer analyzer = new LexicalAnalyzer();

        /**
        * Lista de ejemplos de código fuente para probar el analizador léxico.
        * Cada ejemplo representa diferentes construcciones del lenguaje:
        * - Ejemplo 1: Expresiones aritméticas con variables y operadores.
        * - Ejemplo 2: Estructura condicional if-else con strings.
        * - Ejemplo 3: Definición de función recursiva factorial.
        * - Ejemplo 4: Lista y bucle for con operador de potencia.
        * - Ejemplo 5: Comentarios y operadores de asignación compuesta.
        */
        List<String> ejemplos = Arrays.asList(
            "resultado = (a + b) * 2 - c / 3",
            "if x > 0:\n    print('Positivo')\nelse:\n    print('No positivo')",
            "def factorial(n):\n    if n <= 1:\n        return 1\n    return n * factorial(n-1)",
            "lista = [1, 2, 3]\nfor i in lista:\n    print(i ** 2)",
            "# Esto es un comentario\nx += 5  # Incrementar x"
        );

        /**
        * Itera sobre cada ejemplo de código para realizar el análisis léxico.
        * Por cada ejemplo, muestra el código original y los resultados del análisis.
        */
        for (int i = 0; i < ejemplos.size(); i++) {
            String codigo = ejemplos.get(i);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("EJEMPLO " + (i + 1));
            System.out.println("=".repeat(60));
            System.out.println("Codigo fuente:");
            System.out.println(codigo.replace("\n", "\\n"));
            System.out.println("\nCodigo formateado:");
            System.out.println(codigo);

            /**
            * Realiza el análisis léxico del código fuente.
            * El método analyzeAndClassify devuelve un mapa de tokens clasificados por categorías.
            */
            Map<String, List<Token>> classification = analyzer.analyzeAndClassify(codigo);

            System.out.println("\n" + "=".repeat(40));
            System.out.println("ANALISIS LEXICO COMPLETO");
            System.out.println("=".repeat(40));
            
            /**
            * Calcula y muestra el total de tokens encontrados en el código.
            */
            int totalTokens = 0;
            for (List<Token> tokens : classification.values()) {
                totalTokens += tokens.size();
            }
            System.out.println("\nTOTAL DE TOKENS: " + totalTokens);

            System.out.println("\nTodos los tokens:");
            /**
            * Itera sobre todas las categorías y muestra cada token individualmente.
            * Utiliza el método toString() de la clase Token para formatear la salida.
            */
            for (List<Token> tokens : classification.values()) {
                for (Token token : tokens) {
                    System.out.println("  " + token);
                }
            }

            System.out.println("\n" + "=".repeat(40));
            System.out.println("CLASIFICACION POR CATEGORIAS");
            System.out.println("=".repeat(40));

            /**
            * Muestra los tokens agrupados por categorías (operadores, palabras clave, etc.).
            * Para cada categoría no vacía, muestra:
            * - Nombre de la categoría y cantidad de tokens.
            * - Cada token con su valor, tipo, línea y columna.
            */
            for (Map.Entry<String, List<Token>> entry : classification.entrySet()) {
                List<Token> tokens = entry.getValue();
                if (!tokens.isEmpty()) {
                    System.out.println("\n" + entry.getKey().toUpperCase() + 
                        " (" + tokens.size() + "):");
                    for (Token token : tokens) {
                        System.out.printf("  • '%s' -> %s (linea %d, columna %d)%n",
                            token.getValue(), token.getType().getValue(),
                            token.getLine(), token.getColumn());
                    }
                }
            }

            System.out.println("\n" + "-".repeat(60));
        }
    }
}
