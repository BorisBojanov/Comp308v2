/**
 * A generic tuple class that holds two values: a key and a value.
 * This class is used in the greenhouse system for storing state variables.
 * 
 * @param <A> The type of the key.
 * @param <B> The type of the value.
 * 
 * @author Boris Bojanov
 * @version 1.0
 */
public class TwoTuple<A, B> {
    /** The key of the tuple. */
    public final A key;
    
    /** The value of the tuple. */
    public final B value;

    /**
     * Constructs a TwoTuple with a key-value pair.
     *
     * @param key   The key of the tuple.
     * @param value The value of the tuple.
     */
    public TwoTuple(A key, B value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns a string representation of the tuple.
     *
     * @return A string displaying the key and value pair.
     */
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
