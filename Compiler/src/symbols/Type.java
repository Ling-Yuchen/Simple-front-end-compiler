package symbols;

import lexer.*;

public class Type extends Word {
    public int width = 0;   // 用于储存分配
    public Type(String lexeme, int tag, int width) {
        super(lexeme, tag);
        this.width = width;
    }
    public static final Type
        Int = new Type("int", Tag.BASIC, 4),
        Float = new Type("float", Tag.BASIC, 8),
        Char = new Type("char", Tag.BASIC, 1),
        Bool = new Type("bool", Tag.BASIC, 1);

    /** 可用于类型转换 */
    public static boolean numeric(Type type) {
        return type == Type.Char || type == Type.Float || type == Type.Int;
    }
    public static Type maxType(Type type1, Type type2) {
        if (!numeric(type1) || !numeric(type2)) return null;
        if (type1 == Type.Float || type2 == Type.Float) return Type.Float;
        if (type1 == Type.Int || type2 == Type.Int) return Type.Int;
        return Type.Char;
    }
}
