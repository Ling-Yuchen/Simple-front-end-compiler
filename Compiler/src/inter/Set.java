package inter;

import symbols.Type;

public class Set extends Stmt {
    public Id id;
    public Expr expr;

    public Set(Id id, Expr expr) {
        this.id = id;
        this.expr = expr;
        if (check(id.type, expr.type) == null)
            throw new Error("type error");
    }

    public Type check(Type type1, Type type2) {
        if (Type.numeric(type1) && Type.numeric(type2)) return type2;
        if (type1 == Type.Bool && type2 == Type.Bool) return Type.Bool;
        return null;
    }

    public void gen(int b, int a) {
        emit(id.toString() + " = " + expr.gen().toString());
    }
}
