package inter;

import lexer.Token;
import symbols.Type;

/**
 * Unary类实现了单目运算符
 * */
public class Unary extends Op {
    public Expr expr;
    public Unary(Token token, Expr expr) {
        super(token, null);
        this.expr = expr;
        this.type = Type.maxType(Type.Int, expr.type);
        if (this.type == null) throw new Error("type error");
    }

    public Expr gen() {
        return new Unary(this.op, expr.reduce());
    }

    public String toString() {
        return this.op.toString() + " " + expr.toString();
    }
}
