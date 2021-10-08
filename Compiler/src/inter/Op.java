package inter;

import lexer.Token;
import symbols.Type;

public class Op extends Expr {
    public Op(Token token, Type type) {
        super(token, type);
    }
    public Expr reduce() {
        Expr x = gen();
        Temp t = new Temp(type);
        emit(t.toString() + " = " + x.toString());
        return t;
    }
}
