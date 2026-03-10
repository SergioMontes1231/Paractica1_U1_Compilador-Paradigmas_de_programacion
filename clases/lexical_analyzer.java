      public Map<String, List<Token>> analyzeandClassify(String code){
      // Clasifica los tokens generados en categorías:
      // identificadores, números, cadenas, operadores, delimitadores, palabras reservadas, etc.
  
      //Returns:
      //Map: diccionario de categorías con listas de tokens.
  
      List<Token> tokens = tokenize(code);
  
      // Diccionario de clasificación
      Map<String, List<Token>> classification = new HashMap<>();
  
      classification.put("identificadores", new ArrayList<>());
      classification.put("numeros", new ArrayList<>());
      classification.put("cadenas", new ArrayList<>());
      classification.put("operadores", new ArrayList<>());
      classification.put("delimitadores", new ArrayList<>());
      classification.put("palabras_reservadas", new ArrayList<>());
      classification.put("comentarios", new ArrayList<>());
      classification.put("otros", new ArrayList<>());
  
      // Conjuntos de tipos por categoria
      Set<TokenType> operatorTypes = Set.of(
          TokenType.PLUS, TokenType.MINUS, TokenType.MULTIPLY, TokenType.DIVIDE,
          TokenType.MODULO, TokenType.POWER, TokenType.ASSIGN, TokenType.PLUS_ASSIGN,
          TokenType.MINUS_ASSIGN, TokenType.EQUAL, TokenType.NOT_EQUAL,
          TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.LESS_EQUAL,
          TokenType.GREATER_EQUAL, TokenType.AND, TokenType.OR, TokenType.NOT
      );
  
      Set<TokenType> delimiterTypes = Set.of(
          TokenType.LPAREN, TokenType.RPAREN, TokenType.LBRACKET, TokenType.RBRACKET,
          TokenType.LBRACE, TokenType.RBRACE, TokenType.COMMA, TokenType.SEMICOLON,
          TokenType.DOT, TokenType.COLON
      );
  
      Set<TokenType> keywordTypes = new HashSet<>(keywords.values());
  
      // Clasificar tokens
      for(Token token : tokens){
  
          if(token.type == TokenType.IDENTIFIER){
              classification.get("identificadores").add(token);
          }
  
          else if(token.type == TokenType.NUMBER){
              classification.get("numeros").add(token);
          }
  
          else if(token.type == TokenType.STRING){
              classification.get("cadenas").add(token);
          }
  
          else if(operatorTypes.contains(token.type)){
              classification.get("operadores").add(token);
          }
  
          else if(delimiterTypes.contains(token.type)){
              classification.get("delimitadores").add(token);
          }
  
          else if(keywordTypes.contains(token.type)){
              classification.get("palabras_reservadas").add(token);
          }
  
          else if(token.type == TokenType.COMMENT){
              classification.get("comentarios").add(token);
          }
  
          else{
              classification.get("otros").add(token);
          }
      }
  
      return classification;
    }

