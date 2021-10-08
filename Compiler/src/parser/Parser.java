package parser;

import java.io.*;
import lexer.*;
import symbols.*;
import inter.*;

public class Parser {
    private Lexer lexer;    // 这个语法分析器的词法分析器
    private Token look;     // 向前看词法单元
    Env top = null;         // 当前或顶层的符号表
    int used = 0;           // 用于变量声明的储存位置

    public Parser(Lexer lexer) throws IOException {
        this.lexer = lexer;
        move();
    }
    void move() throws IOException {
        look = lexer.scan();
    }

    void error(String str) {
        throw new Error("near line " + Lexer.line + ": " + str);
    }

    void match(int tag) throws IOException {
        if (look.tag == tag) move();
        else error("syntax error");
    }

    public void program() throws IOException {  // program -> block
        Stmt stmt = block();
        int begin = stmt.newLabel();
        int after = stmt.newLabel();
        stmt.emitLabel(begin);
        stmt.gen(begin, after);
        stmt.emitLabel(after);
    }

    Stmt block() throws IOException {   // block -> { decls stmts }
        match('{');
        Env savedEnv = top;
        top = new Env(top);
        decls();
        Stmt stmt = stmts();
        match('}');
        top = savedEnv;
        return stmt;
    }

    void decls() throws IOException {
        while (look.tag == Tag.BASIC) {     // D -> type ID
            Type type = type();
            Token token = look;
            match(Tag.ID);
            match(';');
            Id id = new Id((Word) token, type, used);
            top.put(token, id);
            used += type.width;
        }
    }

    Type type() throws IOException {
        Type type = (Type) look;        // 期望 look.tag == Tag.BASIC
        match(Tag.BASIC);
        if (look.tag != '[') return type;       // T -> basic
        return dims(type);      // 返回数组类型
    }

    Type dims(Type type) throws IOException {
        match('[');
        Token token = look;
        match(Tag.NUM);
        match(']');
        if (look.tag == '[') type = dims(type);
        return new Array(((Num) token).value, type);
    }

    Stmt stmts() throws IOException {
        if (look.tag == '}') return Stmt.Null;
        return new Seq(stmt(), stmts());
    }

    Stmt stmt() throws IOException {
        Expr expr;
        Stmt stmt, stmt1, stmt2;
        Stmt savedStmt;     // 用于为break语句保存外层的循环语句
        switch (look.tag) {
            case ';':
                move();
                return Stmt.Null;
            case Tag.IF:
                match(Tag.IF);
                match('(');   expr = bool();  match(')');
                stmt1 = stmt();
                if (look.tag != Tag.ELSE) return new If(expr, stmt1);
                match(Tag.ELSE);
                stmt2 = stmt();
                return new Else(expr, stmt1, stmt2);
            case Tag.WHILE:
                While whileNode = new While();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = whileNode;
                match(Tag.WHILE);
                match('('); expr = bool();  match(')');
                stmt1 = stmt();
                whileNode.init(expr, stmt1);
                Stmt.Enclosing = savedStmt;     // 重置 Stmt.Enclosing
                return whileNode;
            case Tag.DO:
                Do doNode = new Do();
                savedStmt = Stmt.Enclosing;
                Stmt.Enclosing = doNode;
                match(Tag.DO);
                stmt1 = stmt();
                match(Tag.WHILE);
                match('('); expr = bool();  match(')'); match(';');
                doNode.init(expr, stmt1);
                Stmt.Enclosing = savedStmt;     // 重置 Stmt.Enclosing
                return doNode;
            case Tag.BREAK:
                match(Tag.BREAK);   match(';');
                return new Break();
            case '{':
                return block();
            default:
                return assign();
        }
    }

    Stmt assign() throws IOException {
        Stmt stmt;
        Token token = look;
        match(Tag.ID);
        Id id = top.get(token);
        if (id == null) error(token.toString() + "undeclared");
        if (look.tag == '=') {
            move();
            stmt = new Set(id, bool());
        } else {
            Access x = offset(id);
            match('=');
            stmt = new SetElem(x, bool());
        }
        match(';');
        return stmt;
    }

    Expr bool() throws IOException {
        Expr x = join();
        while (look.tag == Tag.OR) {
            Token token = look;
            move();
            x = new Or(token, x, join());
        }
        return x;
    }
    Expr join() throws IOException {
        Expr x = equality();
        while (look.tag == Tag.AND) {
            Token token = look;
            move();
            x = new And(token, x, equality());
        }
        return x;
    }
    Expr equality() throws IOException {
        Expr x = rel();
        while (look.tag == Tag.EQ || look.tag == Tag.NE) {
            Token token = look;
            move();
            x = new Rel(token, x, rel());
        }
        return x;
    }
    Expr rel() throws IOException {
        Expr x = expr();
        switch (look.tag) {
            case '>':
            case '<':
            case Tag.LE:
            case Tag.GE:
                Token token = look;
                move();
                return new Rel(token, x, expr());
            default:
                return x;
        }
    }
    Expr expr() throws IOException {
        Expr x = term();
        while (look.tag == '+' || look.tag == '-') {
            Token token = look;
            move();
            x = new Arith(token, x, term());
        }
        return x;
    }
    Expr term() throws IOException {
        Expr x = unary();
        while (look.tag == '*' || look.tag == '/') {
            Token token = look;
            move();
            x = new Arith(token, x, unary());
        }
        return x;
    }
    Expr unary() throws IOException {
        if (look.tag == '-') {
            move();
            return new Unary(Word.minus, unary());
        } else if (look.tag == '!') {
            Token token = look;
            move();
            return new Not(token, unary());
        }
        return factor();
    }
    Expr factor() throws IOException {
        Expr x = null;
        switch (look.tag) {
            case '(':
                move();
                x = bool();
                match(')');
                return x;
            case Tag.NUM:
                x = new Constant(look, Type.Int);
                move();
                return x;
            case Tag.REAL:
                x = new Constant(look, Type.Float);
                move();
                return x;
            case Tag.FALSE:
                x = Constant.False;
                move();
                return x;
            case Tag.TRUE:
                x = Constant.True;
                move();
                return x;
            case Tag.ID:
                Id id = top.get(look);
                if (id == null) error(look.toString() + "undeclared");
                move();
                if (look.tag != '[') return id;
                return offset(id);
            default:
                error("syntax error");
                return x;
        }
    }
    Access offset(Id id) throws IOException {
        Expr i, w, t1, t2, loc;
        Type type = id.type;
        match('['); i = bool(); match(']');
        type = ((Array) type).of;
        w = new Constant(type.width);
        t1 = new Arith(new Token('*'), i, w);
        loc = t1;
        while (look.tag == '[') {
            match('[');  i = bool();  match(']');
            type = ((Array) type).of;
            w = new Constant(type.width);
            t1 = new Arith(new Token('*'), i, w);
            t2 = new Arith(new Token('+'), loc, t1);
            loc = t2;
        }
        return new Access(id, loc, type);
    }
}
