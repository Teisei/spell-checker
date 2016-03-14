package teisei.ir.util;

/**
 *
 * Created by Teisei on 2014/12.
 */
public class Tuple<E, T> {

    private E one;
    private T two;

    Tuple(E one, T two) {
        this.one = one;
        this.two = two;
    }

    public E getOne() {
        return this.one;
    }

    public T getTwo() {
        return this.two;
    }

    @Override
    public String toString() {
        return "[" + one.toString() + "," + two.toString() + "]";
    }
}
