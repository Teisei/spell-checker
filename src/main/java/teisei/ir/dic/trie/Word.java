package teisei.ir.dic.trie;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class Word {
    /*
    the str of this word.
     */
    String str;

    /**
     * count of the word
     */
    int count;

    /*
    part of speech of this word.
     */
    String pos;

    /*
    description of this word.
     */
    String dec;

    public Word(String str, int count) {
        this.str = str;
        this.count = count;
    }

    public Word(String str, String pos, String dec) {
        this.str = str;
        this.pos = pos;
        this.dec = dec;
    }

    public String getStr() {

        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return str;
    }
}
