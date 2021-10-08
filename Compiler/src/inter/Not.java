package inter;

import lexer.*;

public class Not extends Logical {
    public Not(Token token, Expr expr2) {
        super(token, expr2, expr2);
    }

    @Override
    public void jumping(int t, int f) {
        this.expr2.jumping(f, t);
    }

    public String toString() {
        return this.op.toString() + " " + this.expr2.toString();
    }
}
