package inter;

import symbols.Array;
import symbols.Type;

public class SetElem extends Stmt {
    public Id array;
    public Expr index;
    public Expr expr;

    public SetElem(Access access, Expr expr) {
        array = access.array;
        index = access.index;
        this.expr = expr;
        if (check(access.type, expr.type) == null)
            throw new Error("type error");
    }

    public Type check(Type type1, Type type2) {
        if (type1 instanceof Array || type2 instanceof Array) return null;
        if (type1 == type2) return type2;
        if (Type.numeric(type1) && Type.numeric(type2)) return type2;
        return null;
    }

    public void gen(int b, int a) {
        String str1 = index.reduce().toString();
        String str2 = expr.reduce().toString();
        emit(array.toString() + " [ " + str1 + " ] = " + str2);
    }
}
