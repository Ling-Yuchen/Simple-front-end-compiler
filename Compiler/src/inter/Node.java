package inter;

import lexer.*;

/**
 * Node类 有两个子类：
 *      对应于表达式结点的Expr 和 对应于语句结点的Stmt
 * 抽象语法树中的结点被实现为 Node类的对象
 * */
public class Node {
    int lexline = 0;    // 保存本结点对应的构造在源程序中的行号，便于报告错误
    Node() {
        lexline = Lexer.line;
    }
    void error(String message) {
        throw new Error("near line " + lexline + ": " + message);
    }

    // 用于生成三地址代码
    static int labels = 0;
    public int newLabel() {
        return ++labels;
    }
    public void emitLabel(int i) {
        System.out.print("L" + i + ":");
    }
    public void emit(String str) {
        System.out.println("\t" + str);
    }
}
