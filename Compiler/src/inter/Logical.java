package inter;

import lexer.Token;
import symbols.Type;

public class Logical extends Expr {
    public Expr expr1, expr2;
    public Logical(Token token, Expr expr1, Expr expr2) throws Exception {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.type = check(expr1.type, expr2.type);
        if (type == null) throw new Exception("type error");
    }
    public Type check(Type type1, Type type2) {
        if (type1 == Type.Bool && type2 == Type.Bool)
            return Type.Bool;
        return null;
    }
}
