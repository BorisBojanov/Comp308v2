

public class TwoTuple<A, B> {
    public final A key;
    public final B value;

    public TwoTuple(A key, B value) {
        this.key = key;
        this.value = value;
    }
    public B get(Object var1){
        return value;
    }
    //V put(K var1, V var2);
    public B put(A var1, B var2){
        return value;
    }

    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}