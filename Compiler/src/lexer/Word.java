package lexer;
/**
 * word类 继承自 Token类，
 * 增加了 lexeme 字段
 * 用于保存关键字、标识符和复合词法单元的词素
 * 也可以用于管理在中间代码中运算符的书写形式，如单目减号"-2"
 * */
public class Word extends Token {
    public String lexeme = "";
    public Word(String lexeme, int tag) {
        super(tag);
        this.lexeme = lexeme;
    }
    public String toString() {
        return lexeme;
    }
    public static final Word
        and = new Word("&&", Tag.AND),
        or = new Word("||", Tag.OR),
        eq = new Word("==", Tag.EQ),
        ge = new Word(">=", Tag.GE),
        le = new Word("<=", Tag.LE),
        ne = new Word("!=", Tag.NE),
        minus = new Word("minus", Tag.MINUS),
        True = new Word("true", Tag.TRUE),
        False = new Word("false", Tag.FALSE),
        temp = new Word("t", Tag.TEMP);
}
