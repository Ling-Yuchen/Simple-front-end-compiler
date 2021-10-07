package inter;

import lexer.Token;
import symbols.Type;

public class Op extends Expr {
    public Op(Token token, Type type) {
        super(token, type);
    }
}
