package tme4;

public class TwoTuple<A, B> {
    public final A key;
    public final B value;

    public TwoTuple(A key, B value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}