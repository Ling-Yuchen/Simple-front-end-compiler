package inter;

import lexer.Word;
import symbols.Type;

/**
 * 一个标识符就是一个地址，故 Id类继承了 Expr类中 gen和 reduce的默认实现
 * */
public class Id extends  Expr {
    public int offset;      // 相对地址
    public Id(Word word, Type type, int offset) {
        super(word, type);
        this.offset = offset;
    }
}
