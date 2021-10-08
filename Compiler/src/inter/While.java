package inter;

import symbols.Type;

public class While extends Stmt {
    Expr expr;
    Stmt stmt;

    public While() {
        expr = null;
        stmt = null;
    }

    public void init(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (expr.type != Type.Bool)
            throw new Error("boolean required in while");
    }

    @Override
    public void gen(int b, int a) {
        this.after = a;
        expr.jumping(0, a);
        int label = newLabel();
        emitLabel(label);
        stmt.gen(label, b);
        emit("goto L" + b);
    }
}
