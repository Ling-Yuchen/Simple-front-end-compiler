package inter;

import lexer.Token;
import symbols.Type;

public class Expr extends Node {
    public Token op;
    public Type type;
    public Expr(Token token, Type type) {
        this.op = token;
        this.type = type;
    }

}
