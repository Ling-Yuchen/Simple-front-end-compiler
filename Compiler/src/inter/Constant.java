package inter;

import lexer.Token;
import symbols.Type;

public class Constant extends Expr {
    public Constant(Token token, Type type) {
        super(token, type);
    }
}
