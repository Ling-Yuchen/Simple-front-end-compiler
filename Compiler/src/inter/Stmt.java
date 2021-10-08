package inter;

public class Stmt extends Node {
    /**
     *  Stmt类的构造方法不做任何事情，相关处理工作在子类中完成
     * */
    public Stmt() { }

    public static Stmt Null = new Stmt();

    /**
     * @param b 语句开始处的标号
     * @param a 语句的下一条指令的标号
     * */
    public void gen(int b, int a) { }

    int after = 0; //保存语句的下一条指令的标号

    public static Stmt Enclosing = Stmt.Null;  // 用于break语句
}
