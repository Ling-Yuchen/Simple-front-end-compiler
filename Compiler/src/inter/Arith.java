package inter;

import lexer.Token;
import symbols.Type;

/**
 * Arith类实现了双目运算符
 * */

public class Arith extends Op {
    public Expr expr1, expr2;
    public Arith(Token token, Expr expr1, Expr expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.type = Type.maxType(expr1.type, expr2.type);
        if (this.type == null) throw new Error("type error");
    }
    public Expr gen() {
        return new Arith(this.op, expr1.reduce(), expr2.reduce());
    }
    public String toString() {
        return expr1.toString() + " " + this.op.toString() + " " + expr2.toString();
    }
}
