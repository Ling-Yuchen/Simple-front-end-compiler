package inter;

import lexer.Word;
import symbols.Type;

public class Temp extends Expr {
    public static int count = 0;
    public int number = 0;
    public Temp(Type type) {
        super(Word.temp, type);
        number = ++count;
    }
    public String toString() {
        return "t" + number;
    }
}
