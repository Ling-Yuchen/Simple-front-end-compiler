package inter;

import lexer.Token;
import symbols.Type;

/**
 * Expr类 包含字段 op 和 type
 * 分别表示一个结点上的运算符和类型
 * */

public class Expr extends Node {
    public Token op;
    public Type type;
    public Expr(Token token, Type type) {
        this.op = token;
        this.type = type;
    }

    /**
     * gen方法返回一个"项"，成为一个三地址指令的右部
     *  Expr的子类通常会重新实现 gen方法
     * */
    public Expr gen() {
        return this;
    }

    /**
     * reduce方法把一个表达式归约成为一个单一地址
     * 即，返回一个常量、标识符或临时名字
     * */
    public Expr reduce() {
        return this;
    }

    /**
     * 为布尔表达式生成跳转代码
     * @param t 表达式的true出口
     * @param f 表达式的false出口
     * */
    public void jumping(int t, int f) {
        emitJumps(toString(), t, f);
    }
    public void emitJumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if " + test + " goto L" + t);
            emit("goto L" + f);
        }
        else if (t != 0) emit("if " + test + " goto L" + t);
        else if (f != 0) emit("ifFalse " + test + " goto L" + f);
    }

    public String toString() {
        return op.toString();
    }
}
