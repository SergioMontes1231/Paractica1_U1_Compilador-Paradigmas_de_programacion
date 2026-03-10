class LexicalAnalyzer {
    /**
    * Analizador lexico que convierte el codigo fuente en una secuencia de tokens.
    * Utiliza patrones regex para identificar los diferentes tipos de tokens.
    * 
    * Atributos:
    * tokenPatterns (List<TokenPattern>): lista de patrones y sus tipos de token asociados.
    * keywords (Map<String, TokenType>): mapa de palabras clave reservadas del lenguaje.
    */
    
    private static class TokenPattern {
        /**
        * Clase interna que asocia un patron regex con un tipo de token.
        * 
        * Atributos:
        * pattern (Pattern): patron regex compilado.
        * type (TokenType): tipo de token asociado al patrón.
        */
        Pattern pattern;
        TokenType type;

        /**
        * Constructor de TokenPattern.
        * 
        * @param regex expresion regular para identificar el token.
        * @param type tipo de token correspondiente.
        */
        TokenPattern(String regex, TokenType type) {
            this.pattern = Pattern.compile(regex);
            this.type = type;
        }
    }

    private List<TokenPattern> tokenPatterns;
    private Map<String, TokenType> keywords;

    /**
    * Constructor del analizador lexico.
    * Inicializa las listas de patrones y palabras clave.
    */
    public LexicalAnalyzer() {
        initializeTokenPatterns();
        initializeKeywords();
    }

    /**
    * Inicializa la lista de patrones para reconocer tokens.
    * Los patrones se definen en orden de prioridad (de especifico a general).
    * 
    * Tipos de patrones:
    * - Comentarios: # hasta fin de linea.
    * - Numeros: enteros y decimales.
    * - Strings: entre comillas dobles o simples.
    * - Operadores de asignacion compuesta: +=, -=.
    * - Operadores relacionales: ==, !=, <=, >=, <, >.
    * - Operadores aritmeticos: ** (potencia), +, -, *, /, %, =.
    * - Delimitadores: (), [], {}, ,, ;, ., :.
    * - Palabras clave: if, else, while, etc. (inicialmente null).
    * - Identificadores: secuencias alfanumericas que empiezan con letra o _.
    * - Espacios en blanco y nuevas lineas.
    */
    private void initializeTokenPatterns() {
        tokenPatterns = new ArrayList<>();

        tokenPatterns.add(new TokenPattern("#.*", TokenType.COMMENT));

        tokenPatterns.add(new TokenPattern("\\d+\\.\\d+", TokenType.NUMBER));
        tokenPatterns.add(new TokenPattern("\\d+", TokenType.NUMBER));

        tokenPatterns.add(new TokenPattern("\"[^\"]*\"", TokenType.STRING));
        tokenPatterns.add(new TokenPattern("'[^']*'", TokenType.STRING));

        tokenPatterns.add(new TokenPattern("\\+=", TokenType.PLUS_ASSIGN));
        tokenPatterns.add(new TokenPattern("-=", TokenType.MINUS_ASSIGN));

        tokenPatterns.add(new TokenPattern("==", TokenType.EQUAL));
        tokenPatterns.add(new TokenPattern("!=", TokenType.NOT_EQUAL));
        tokenPatterns.add(new TokenPattern("<=", TokenType.LESS_EQUAL));
        tokenPatterns.add(new TokenPattern(">=", TokenType.GREATER_EQUAL));
        tokenPatterns.add(new TokenPattern("<", TokenType.LESS_THAN));
        tokenPatterns.add(new TokenPattern(">", TokenType.GREATER_THAN));

        tokenPatterns.add(new TokenPattern("\\*\\*", TokenType.POWER));
        tokenPatterns.add(new TokenPattern("\\+", TokenType.PLUS));
        tokenPatterns.add(new TokenPattern("-", TokenType.MINUS));
        tokenPatterns.add(new TokenPattern("\\*", TokenType.MULTIPLY));
        tokenPatterns.add(new TokenPattern("/", TokenType.DIVIDE));
        tokenPatterns.add(new TokenPattern("%", TokenType.MODULO));
        tokenPatterns.add(new TokenPattern("=", TokenType.ASSIGN));

        tokenPatterns.add(new TokenPattern("\\(", TokenType.LPAREN));
        tokenPatterns.add(new TokenPattern("\\)", TokenType.RPAREN));
        tokenPatterns.add(new TokenPattern("\\[", TokenType.LBRACKET));
        tokenPatterns.add(new TokenPattern("\\]", TokenType.RBRACKET));
        tokenPatterns.add(new TokenPattern("\\{", TokenType.LBRACE));
        tokenPatterns.add(new TokenPattern("\\}", TokenType.RBRACE));

        tokenPatterns.add(new TokenPattern(",", TokenType.COMMA));
        tokenPatterns.add(new TokenPattern(";", TokenType.SEMICOLON));
        tokenPatterns.add(new TokenPattern("\\.", TokenType.DOT));
        tokenPatterns.add(new TokenPattern(":", TokenType.COLON));

        tokenPatterns.add(new TokenPattern("\\b(if|elif|else|while|for|def|class|return|print|import|from|as|try|except|finally|with|pass|break|continue|True|False|None|in|is|and|or|not)\\b", null));

        tokenPatterns.add(new TokenPattern("[a-zA-Z_][a-zA-Z0-9_]*", TokenType.IDENTIFIER));

        tokenPatterns.add(new TokenPattern("\n", TokenType.NEWLINE));
        tokenPatterns.add(new TokenPattern("[ \t]+", TokenType.WHITESPACE));
    }

    /**
    * Inicializa el mapa de palabras clave del lenguaje.
    * Convierte identificadores especificos en tokens de palabras clave.
    * 
    * Palabras clave incluidas:
    * - Estructuras de control: if, elif, else, while, for
    * - Definiciones: def, class
    * - Control de flujo: return, break, continue, pass
    * - Manejo de excepciones: try, except, finally
    * - Importaciones: import, from, as
    * - Valores booleanos: True, False
    * - Valor nulo: None
    * - Operadores logicos: in, is, and, or, not
    * - Funcion de salida: print
    */
    private void initializeKeywords() {
        keywords = new HashMap<>();
        keywords.put("if", TokenType.IF);
        keywords.put("elif", TokenType.ELIF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("for", TokenType.FOR);
        keywords.put("def", TokenType.DEF);
        keywords.put("class", TokenType.CLASS);
        keywords.put("return", TokenType.RETURN);
        keywords.put("print", TokenType.PRINT);
        keywords.put("import", TokenType.IMPORT);
        keywords.put("from", TokenType.FROM);
        keywords.put("as", TokenType.AS);
        keywords.put("try", TokenType.TRY);
        keywords.put("except", TokenType.EXCEPT);
        keywords.put("finally", TokenType.FINALLY);
        keywords.put("with", TokenType.WITH);
        keywords.put("pass", TokenType.PASS);
        keywords.put("break", TokenType.BREAK);
        keywords.put("continue", TokenType.CONTINUE);
        keywords.put("True", TokenType.TRUE);
        keywords.put("False", TokenType.FALSE);
        keywords.put("None", TokenType.NONE);
        keywords.put("in", TokenType.IN);
        keywords.put("is", TokenType.IS);
        keywords.put("and", TokenType.AND);
        keywords.put("or", TokenType.OR);
        keywords.put("not", TokenType.NOT);
    }
}
