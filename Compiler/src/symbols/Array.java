package symbols;

import lexer.Tag;

public class Array extends Type {
    public Type of;         // 数组的元素类型
    public int size = 1;    // 元素个数
    public Array(int size, Type type) {
        super("[]", Tag.INDEX, size* type.width);
        this.size = size;
        this.of = type;
    }
}
