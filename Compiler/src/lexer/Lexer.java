package lexer;

import java.io.*;
import symbols.*;
import inter.*;
import java.util.*;

/**
 * 词法分析器
 * */
public class Lexer {
    public static int line = 1;
    public static char peek = ' ';
    Hashtable words = new Hashtable();
    void reserve(Word word) {
        words.put(word.lexeme, word);
    }
    public Lexer() {
        // 保留选定的关键字和其他地方定义的对象的词素
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("break", Tag.BREAK));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Int);
        reserve(Type.Float);
        reserve(Type.Bool);
        reserve(Type.Char);
    }
    void readNextChar() throws IOException {
        peek = (char) System.in.read();
    }

    // 用于识别复合语法单元，如 “>=”,"&&"
    boolean readNextChar(char c) throws IOException {
        readNextChar();
        if (peek != c) return false;
        peek = ' ';
        return true;
    }

    void skipBlank() throws IOException {
        for ( ; ; readNextChar()) {
            if (peek == ' ' || peek == '\t') continue;
            if (peek == '\n') ++line;
            else break;
        }
        if (peek == '/') skipAnnotation();
    }
    void skipAnnotation() throws IOException {
        if (readNextChar('/')) {
            for ( ; ; readNextChar())
                if (peek == '\n') {
                    ++line; break;
                }
        }
        if (peek == '*') {
            readNextChar();
            while (true) {
                if (peek == '\n') ++line;
                if (peek == '*') {
                    if (readNextChar('/')) break;
                } else readNextChar();
            }
        }
        skipBlank();
    }
    /**
     * Lexer类的主方法
     * 识别数字、标识符和保留字
     * */
    public Token scan() throws IOException {
        skipBlank();
        switch (peek) {
            case '&':
                if (readNextChar('&')) return Word.and;
                return new Token('&');
            case '|':
                if (readNextChar('|')) return Word.or;
                return new Token('|');
            case '>':
                if(readNextChar('=')) return Word.ge;
                return new Token('>');
            case '<':
                if (readNextChar('=')) return Word.le;
                return new Token('<');
            case '=':
                if (readNextChar('=')) return Word.eq;
                return new Token('=');
            case '!':
                if (readNextChar('=')) return Word.ne;
                return new Token('!');
        }
        if (Character.isDigit(peek)) {
            int val = 0;
            do {
                val = val*10 + Character.digit(peek, 10);
                readNextChar();
            } while (Character.isDigit(peek));
            if (peek != '.')
                return new Num(val);
            float f = val, d = 1;
            readNextChar();
            do {
                d *= 10;
                f += Character.digit(peek, 10) / d;
                readNextChar();
            } while (Character.isDigit(peek));
            return new Real(f);
        }
        if (Character.isLetter(peek)) {
            StringBuilder buffer = new StringBuilder();
            do {
                buffer.append(peek);
                readNextChar();
            } while (Character.isLetter(peek));
            String str = buffer.toString();
            Word word = (Word) words.get(str);
            if (word != null)
                return word;
            word = new Word(str, Tag.ID);
            words.put(str, word);
            return word;
        }
        Token token = new Token(peek);
        peek = ' ';
        return token;
    }
}
