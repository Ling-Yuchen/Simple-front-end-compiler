package lexer;

/**
 * Tag类 定义了各词法单元对应的常量
 * ASCII字符通常被转化为 0~255 之间的整数
 * 故用大于 255 的整数来表示终结符号
*/

public class Tag {
    public static final int
            AND = 256,      BASIC = 257,    BREAK = 258,    DO = 259,
            ELSE = 260,     EQ = 261,       FALSE = 262,    GE = 263,
            ID = 264,       IF = 265,       INDEX = 266,    LE = 267,
            MINUS = 268,    NE = 269,       NUM = 270,      OR = 271,
            REAL = 272,     TEMP = 273,     TRUE = 274,     WHILE = 275;
}
