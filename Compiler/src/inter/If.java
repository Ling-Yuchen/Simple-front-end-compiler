package inter;

import symbols.Type;

public class If extends Stmt {
    Expr expr;
    Stmt stmt;

    public If(Expr expr, Stmt stmt) {
        this.expr = expr;
        this.stmt = stmt;
        if (expr.type != Type.Bool)
            throw new Error("boolean required in if");
    }

    @Override
    public void gen(int b, int a) {
        int label = newLabel();  // stmt的代码的标号
        expr.jumping(0, a);   // 为真时控制流穿越，为假时转向a
        emitLabel(label);
        stmt.gen(label, a);
    }
}
