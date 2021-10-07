package lexer;

/**
 * Token类 有一个 tag 字段，用于做出语法分析决定
 * */

public class Token {
    public final int tag;
    public Token(int tag) {
        this.tag = tag;
    }
    public String toString() {
        return "" + (char) tag;
    }
}
