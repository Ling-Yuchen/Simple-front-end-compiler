package inter;

import lexer.*;
import symbols.*;

public class Logical extends Expr {
    public Expr expr1, expr2;
    public Logical(Token token, Expr expr1, Expr expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.type = check(expr1.type, expr2.type);
        if (type == null) throw new Error("type error");
    }
    public Type check(Type type1, Type type2) {
        if (type1 == Type.Bool && type2 == Type.Bool)
            return Type.Bool;
        return null;
    }

    public Expr gen() {
        int f = newLabel();
        int a = newLabel();
        Temp temp = new Temp(this.type);
        this.jumping(0, f);
        emit(temp.toString() + " = true");
        emitLabel(f);
        emit(temp.toString() + " = false");
        emitLabel(a);
        return temp;
    }

    public String toString() {
        return expr1.toString() + " " + this.op.toString() + " " + expr2.toString();
    }
}
