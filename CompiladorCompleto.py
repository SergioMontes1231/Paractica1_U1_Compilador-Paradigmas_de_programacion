import re
from enum import Enum
from dataclasses import dataclass
from typing import List

# =======================================================
# DEFINICIÓN DE TIPOS DE TOKENS
# =======================================================

class TokenType(Enum):
    """
    Enumeración que define los diferentes tipos de tokens
    que puede reconocer el analizador léxico.
    Incluye identificadores, literales, operadores, delimitadores,
    separadores y palabras reservadas de Python.
    """

    # Identificadores y literales
    IDENTIFIER = "IDENTIFICADOR"
    NUMBER = "NÚMERO"
    STRING = "CADENA"

    # Operadores aritméticos
    PLUS = "SUMA"
    MINUS = "RESTA"
    MULTIPLY = "MULTIPLICACIÓN"
    DIVIDE = "DIVISIÓN"
    MODULO = "MÓDULO"
    POWER = "POTENCIA"

    # Operadores de asignación
    ASSIGN = "ASIGNACIÓN"
    PLUS_ASSIGN = "SUMA_ASIGNACIÓN"
    MINUS_ASSIGN = "RESTA_ASIGNACIÓN"

    # Operadores de comparación
    EQUAL = "IGUAL"
    NOT_EQUAL = "NO_IGUAL"
    LESS_THAN = "MENOR_QUE"
    GREATER_THAN = "MAYOR_QUE"
    LESS_EQUAL = "MENOR_IGUAL"
    GREATER_EQUAL = "MAYOR_IGUAL"

    # Operadores lógicos
    AND = "Y_LÓGICO"
    OR = "O_LÓGICO"
    NOT = "NO_LÓGICO"

    # Delimitadores
    LPAREN = "PARÉNTESIS_IZQ"
    RPAREN = "PARÉNTESIS_DER"
    LBRACKET = "CORCHETE_IZQ"
    RBRACKET = "CORCHETE_DER"
    LBRACE = "LLAVE_IZQ"
    RBRACE = "LLAVE_DER"

    # Separadores
    COMMA = "COMA"
    SEMICOLON = "PUNTO_COMA"
    DOT = "PUNTO"
    COLON = "DOS_PUNTOS"

    # Palabras reservadas de Python
    IF = "SI"
    ELSE = "SINO"
    ELIF = "SINO_SI"
    WHILE = "MIENTRAS"
    FOR = "PARA"
    DEF = "FUNCIÓN"
    CLASS = "CLASE"
    RETURN = "RETORNO"
    PRINT = "IMPRIMIR"
    IMPORT = "IMPORTAR"
    FROM = "DESDE"
    AS = "COMO"
    TRY = "INTENTAR"
    EXCEPT = "EXCEPCIÓN"
    FINALLY = "FINALMENTE"
    WITH = "CON"
    PASS = "PASAR"
    BREAK = "ROMPER"
    CONTINUE = "CONTINUAR"
    TRUE = "VERDADERO"
    FALSE = "FALSO"
    NONE = "NULO"
    IN = "EN"
    IS = "ES"

    # Otros
    NEWLINE = "NUEVA_LÍNEA"
    WHITESPACE = "ESPACIO"
    COMMENT = "COMENTARIO"
    UNKNOWN = "DESCONOCIDO"


# =======================================================
# DEFINICIÓN DE LA CLASE TOKEN
# =======================================================

@dataclass
class Token:
    """
    Representa un token en el código fuente.

    Atributos:
        type (TokenType): tipo del token (ej. IDENTIFIER, NUMBER, PLUS).
        value (str): valor exacto encontrado en el código.
        line (int): número de línea donde aparece.
        column (int): número de columna donde empieza.
    """
    type: TokenType
    value: str
    line: int
    column: int

    def __str__(self):
        """Devuelve una representación legible del token."""
        return f"Token({self.type.value}, '{self.value}', {self.line}:{self.column})"


# =======================================================
# ANALIZADOR LÉXICO
# =======================================================

class LexicalAnalyzer:
    """
    Implementa un analizador léxico básico para código Python.
    Utiliza expresiones regulares para dividir el código fuente en tokens.
    """

    def __init__(self):
        # Lista de patrones regex asociados a tipos de tokens
        # El orden importa: patrones más específicos deben ir primero
        self.token_patterns = [
            # Comentarios
            (r'#.*', TokenType.COMMENT),

            # Números (decimales antes que enteros)
            (r'\d+\.\d+', TokenType.NUMBER),
            (r'\d+', TokenType.NUMBER),

            # Cadenas
            (r'"[^"]*"', TokenType.STRING),
            (r"'[^']*'", TokenType.STRING),

            # Operadores de asignación compuesta
            (r'\+=', TokenType.PLUS_ASSIGN),
            (r'-=', TokenType.MINUS_ASSIGN),

            # Operadores de comparación
            (r'==', TokenType.EQUAL),
            (r'!=', TokenType.NOT_EQUAL),
            (r'<=', TokenType.LESS_EQUAL),
            (r'>=', TokenType.GREATER_EQUAL),
            (r'<', TokenType.LESS_THAN),
            (r'>', TokenType.GREATER_THAN),

            # Operadores aritméticos (** antes que *)
            (r'\*\*', TokenType.POWER),
            (r'\+', TokenType.PLUS),
            (r'-', TokenType.MINUS),
            (r'\*', TokenType.MULTIPLY),
            (r'/', TokenType.DIVIDE),
            (r'%', TokenType.MODULO),
            (r'=', TokenType.ASSIGN),

            # Delimitadores
            (r'\(', TokenType.LPAREN),
            (r'\)', TokenType.RPAREN),
            (r'\[', TokenType.LBRACKET),
            (r'\]', TokenType.RBRACKET),
            (r'\{', TokenType.LBRACE),
            (r'\}', TokenType.RBRACE),

            # Separadores
            (r',', TokenType.COMMA),
            (r';', TokenType.SEMICOLON),
            (r'\.', TokenType.DOT),
            (r':', TokenType.COLON),

            # Palabras reservadas (manejo especial con self.keywords)
            (r'\b(if|elif|else|while|for|def|class|return|print|import|from|as|try|except|finally|with|pass|break|continue|True|False|None|in|is|and|or|not)\b', None),

            # Identificadores
            (r'[a-zA-Z_][a-zA-Z0-9_]*', TokenType.IDENTIFIER),

            # Espacios y saltos de línea
            (r'\n', TokenType.NEWLINE),
            (r'[ \t]+', TokenType.WHITESPACE),
        ]

        # Diccionario de palabras reservadas → tipo de token
        self.keywords = {
            'if': TokenType.IF, 'elif': TokenType.ELIF, 'else': TokenType.ELSE,
            'while': TokenType.WHILE, 'for': TokenType.FOR, 'def': TokenType.DEF,
            'class': TokenType.CLASS, 'return': TokenType.RETURN, 'print': TokenType.PRINT,
            'import': TokenType.IMPORT, 'from': TokenType.FROM, 'as': TokenType.AS,
            'try': TokenType.TRY, 'except': TokenType.EXCEPT, 'finally': TokenType.FINALLY,
            'with': TokenType.WITH, 'pass': TokenType.PASS, 'break': TokenType.BREAK,
            'continue': TokenType.CONTINUE, 'True': TokenType.TRUE, 'False': TokenType.FALSE,
            'None': TokenType.NONE, 'in': TokenType.IN, 'is': TokenType.IS,
            'and': TokenType.AND, 'or': TokenType.OR, 'not': TokenType.NOT,
        }

    def tokenize(self, code: str, include_whitespace: bool = False) -> List[Token]:
        """
        Convierte el código fuente en una lista de tokens.

        Args:
            code (str): código fuente.
            include_whitespace (bool): si True, incluye espacios y saltos de línea.

        Returns:
            List[Token]: lista de tokens generados.
        """
        tokens = []
        lines = code.split('\n')

        for line_num, line in enumerate(lines, 1):
            column = 1
            pos = 0

            while pos < len(line):
                matched = False

                for pattern, token_type in self.token_patterns:
                    regex = re.compile(pattern)
                    match = regex.match(line, pos)

                    if match:
                        value = match.group(0)

                        # Si es palabra reservada, usar token especial
                        if token_type is None and value in self.keywords:
                            token_type = self.keywords[value]
                        elif token_type is None:
                            token_type = TokenType.IDENTIFIER

                        # Ignorar espacios y saltos si no se piden
                        if include_whitespace or token_type not in [TokenType.WHITESPACE, TokenType.NEWLINE]:
                            tokens.append(Token(token_type, value, line_num, column))

                        # Avanzar el cursor
                        pos = match.end()
                        column += len(value)
                        matched = True
                        break

                if not matched:
                    # Si no coincide con ningún patrón, es un token desconocido
                    tokens.append(Token(TokenType.UNKNOWN, line[pos], line_num, column))
                    pos += 1
                    column += 1

        return tokens

    def analyze_and_classify(self, code: str) -> dict:
        """
        Clasifica los tokens generados en categorías: identificadores,
        números, cadenas, operadores, delimitadores, palabras reservadas, etc.

        Returns:
            dict: diccionario de categorías con listas de tokens.
        """
        tokens = self.tokenize(code)

        # Diccionario de clasificación
        classification = {
            'identificadores': [],
            'números': [],
            'cadenas': [],
            'operadores': [],
            'delimitadores': [],
            'palabras_reservadas': [],
            'comentarios': [],
            'otros': []
        }

        # Conjuntos de tipos por categoría
        operator_types = {TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE,
                         TokenType.MODULO, TokenType.POWER, TokenType.ASSIGN, TokenType.PLUS_ASSIGN,
                         TokenType.MINUS_ASSIGN, TokenType.EQUAL, TokenType.NOT_EQUAL,
                         TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.LESS_EQUAL,
                         TokenType.GREATER_EQUAL, TokenType.AND, TokenType.OR, TokenType.NOT}

        delimiter_types = {TokenType.LPAREN, TokenType.RPAREN, TokenType.LBRACKET, TokenType.RBRACKET,
                          TokenType.LBRACE, TokenType.RBRACE, TokenType.COMMA, TokenType.SEMICOLON,
                          TokenType.DOT, TokenType.COLON}

        keyword_types = set(self.keywords.values())

        # Clasificar tokens
        for token in tokens:
            if token.type == TokenType.IDENTIFIER:
                classification['identificadores'].append(token)
            elif token.type == TokenType.NUMBER:
                classification['números'].append(token)
            elif token.type == TokenType.STRING:
                classification['cadenas'].append(token)
            elif token.type in operator_types:
                classification['operadores'].append(token)
            elif token.type in delimiter_types:
                classification['delimitadores'].append(token)
            elif token.type in keyword_types:
                classification['palabras_reservadas'].append(token)
            elif token.type == TokenType.COMMENT:
                classification['comentarios'].append(token)
            else:
                classification['otros'].append(token)

        return classification, tokens


# =======================================================
# FUNCIÓN PRINCIPAL DE DEMOSTRACIÓN
# =======================================================

def main():
    """Ejecuta ejemplos de análisis léxico sobre fragmentos de código Python."""
    analyzer = LexicalAnalyzer()

    ejemplos = [
        "resultado = (a + b) * 2 - c / 3",
        "if x > 0:\n    print('Positivo')\nelse:\n    print('No positivo')",
        "def factorial(n):\n    if n <= 1:\n        return 1\n    return n * factorial(n-1)",
        "lista = [1, 2, 3]\nfor i in lista:\n    print(i ** 2)",
        "# Esto es un comentario\nx += 5  # Incrementar x"
    ]

    for i, codigo in enumerate(ejemplos, 1):
        print(f"\n{'='*60}")
        print(f"EJEMPLO {i}")
        print(f"{'='*60}")
        print("Código fuente:")
        print(repr(codigo))
        print("\nCódigo formateado:")
        print(codigo)

        classification, all_tokens = analyzer.analyze_and_classify(codigo)

        print(f"\n{'='*40}")
        print("ANÁLISIS LÉXICO COMPLETO")
        print(f"{'='*40}")
        print(f"\nTOTAL DE TOKENS: {len(all_tokens)}")

        print("\nTodos los tokens:")
        for token in all_tokens:
            print(f"  {token}")

        print(f"\n{'='*40}")
        print("CLASIFICACIÓN POR CATEGORÍAS")
        print(f"{'='*40}")

        for categoria, tokens in classification.items():
            if tokens:
                print(f"\n{categoria.upper()} ({len(tokens)}):")
                for token in tokens:
                    print(f"  • '{token.value}' -> {token.type.value} (línea {token.line}, columna {token.column})")

        print(f"\n{'-'*60}")


if __name__ == "__main__":
    main()
