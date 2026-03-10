package clases;

public class Token {
    /**
    * Representa un token en el código fuente.
    * 
    * Atributos:
    * type (TokenType): tipo del token (ej. IDENTIFIER, NUMBER, PLUS).
    * value (String): valor exacto encontrado en el código.
    * line (int): número de línea donde aparece.
    * column (int): número de columna donde empieza.
    */

        TokenType type;
        String value;
        int line;
        int column;

        public Token(TokenType type, String value, int line, int column){
            this.type = type;
            this.value = value;
            this.line = line;
            this.column = column;
        }
        @Override
        public String toString(){
            /* Devuelve una representación legible del token. */
            return "Token(" + type + ", '" + value + "', " + line + ":" + column + ")";
        }
}
