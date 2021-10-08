package inter;

import symbols.Type;

public class Do extends Stmt {
    Expr expr;
    Stmt stmt;

    public Do() {
        expr = null;
        stmt = null;
    }

    public void init(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (expr.type != Type.Bool)
            throw new Error("boolean required in do");
    }

    @Override
    public void gen(int b, int a) {
        this.after = a;
        int label = newLabel();
        stmt.gen(b, label);
        emitLabel(label);
        stmt.gen(label, b);
        expr.jumping(b, 0);
    }
}
