/**
 * Tipos de tokens que puede reconocer el analizador léxico para Java
 */
package clases;

public enum TokenType {

    // Identificadores y literales
    IDENTIFIER,
    NUMBER,
    STRING,

    // Operadores aritméticos
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    MODULO,
    INCREMENT,
    DECREMENT,

    // Operadores de asignación
    ASSIGN,
    PLUS_ASSIGN,
    MINUS_ASSIGN,

    // Comparación
    EQUAL,
    NOT_EQUAL,
    LESS_THAN,
    GREATER_THAN,
    LESS_EQUAL,
    GREATER_EQUAL,

    // Lógicos
    AND,
    OR,
    NOT,

    // Delimitadores
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    LBRACE,
    RBRACE,

    // Separadores
    COMMA,
    SEMICOLON,
    DOT,
    COLON,

    // Palabras reservadas de Java
    IF,
    ELSE,
    WHILE,
    FOR,
    CLASS,
    RETURN,
    IMPORT,
    PACKAGE,
    PUBLIC,
    PRIVATE,
    PROTECTED,
    STATIC,
    VOID,
    NEW,
    TRUE,
    FALSE,
    NULL,
    TRY,
    CATCH,
    FINALLY,
    BREAK,
    CONTINUE,

    // Otros
    NEWLINE,
    WHITESPACE,
    COMMENT,
    UNKNOWN
}
