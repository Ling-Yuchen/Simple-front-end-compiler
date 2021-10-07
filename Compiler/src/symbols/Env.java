package symbols;

import inter.*;
import lexer.*;
import java.util.*;

/**
 * Env类 实现了符号链接表
 * 实现语句块的最近嵌套规则时，可以将符号表链接起来，使得内嵌语句块的符号表指向外围语句块的符号表
 * Env类 支持三种操作：
 *      1、创建一个新符号表
 *      2、在当前表中添加一个新条目
 *      3、得到一个标识符的条目
 * Env类把字符串词法单元映射为 Id类的对象
 * */
public class Env {
    private Hashtable table;
    protected Env prev;

    public Env(Env prev) {
        this.table = new Hashtable();
        this.prev = prev;
    }

    public void put(Token token, Id id) {
        table.put(token, id);
    }

    public Id get(Token token) {
        for (Env e = this; e != null; e = e.prev) {
            Id target = (Id) e.table.get(token);
            if (target != null) return target;
        }
        return null;
    }
}
