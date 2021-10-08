package inter;

import symbols.Type;

public class Else extends Stmt {
    Expr expr;
    Stmt stmt1, stmt2;

    public Else(Expr expr, Stmt stmt1, Stmt stmt2) {
        this.expr = expr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        if (expr.type != Type.Bool)
            throw new Error("boolean required in if");
    }

    @Override
    public void gen(int b, int a) {
        int label1 = newLabel();  // 用于语句stmt1
        int label2 = newLabel();  // 用于语句stmt2
        expr.jumping(0, label2);  // 为真时控制流穿越到stmt1

        emitLabel(label1);
        stmt1.gen(label1, a);
        emit("goto L" + a);

        emitLabel(label2);
        stmt2.gen(label2, a);
    }
}
