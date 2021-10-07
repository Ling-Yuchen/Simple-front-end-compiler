package lexer;

/**
 * Num类 继承自 Token类
 * 用于处理整形常量词法单元
 * */
public class Num extends Token {
    public final int value;
    public Num(int value) {
        super(Tag.NUM);
        this.value = value;
    }
    public String toString() {
        return "" + value;
    }
}
