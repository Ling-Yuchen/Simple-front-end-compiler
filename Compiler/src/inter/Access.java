package inter;

import lexer.Tag;
import lexer.Word;
import symbols.Type;

public class Access extends Op {
    public Id array;
    public Expr index;

    public Access(Id array, Expr index, Type type) {
        super(new Word("[]", Tag.INDEX), type);
        this.array = array;
        this.index = index;
    }

    public Expr gen() {
        return new Access(array, index.reduce(), this.type);
    }

    @Override
    public void jumping(int t, int f) {
        emitJumps(this.reduce().toString(), t, f);
    }

    public String toString() {
        return array.toString() + " [ " + index.toString() + " ]";
    }
}
