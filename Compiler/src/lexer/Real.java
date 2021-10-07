package lexer;

/**
 * Real类 继承自 Token类
 * 用于处理浮点数
 * */
public class Real extends Token {
    public final float value;
    public Real(float value) {
        super(Tag.REAL);
        this.value = value;
    }
    public String toString() {
        return "" + value;
    }
}
