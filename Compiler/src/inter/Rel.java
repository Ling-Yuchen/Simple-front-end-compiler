package inter;

import lexer.*;
import symbols.*;

public class Rel extends Logical {
    public Rel(Token token, Expr expr1, Expr expr2) {
        super(token, expr1, expr2);
    }

    public Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) return null;
        if (type1 == type2) return Type.Bool;
        return null;
    }

    @Override
    public void jumping(int t, int f) {
        Expr a = expr1.reduce();
        Expr b = expr2.reduce();
        String test = a.toString() + " " + this.op.toString() + " " + b.toString();
        emitJumps(test, t, f);
    }
}

