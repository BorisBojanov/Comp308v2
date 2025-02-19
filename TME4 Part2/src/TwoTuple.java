/**
 * A generic class representing a key-value pair.
 * This class is used to store two related objects together in GreenhouseControls.
 * 
 * @param <A> The type of the key.
 * @param <B> The type of the value.
 * 
 * @author Boris B
 * @version 1.0 Jan 31, 2025
 */
public class TwoTuple<A, B> {

    /** The key of the tuple. */
    public final A key;

    /** The value associated with the key. */
    public final B value;

    /**
     * Constructs a TwoTuple with the given key and value.
     *
     * @param key The key of the tuple.
     * @param value The associated value.
     */
    public TwoTuple(A key, B value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the stored value.
     *
     * @param var1 An unused parameter.
     * @return The value associated with the key.
     */
    public B get(Object var1) {
        return value;
    }

    /**
     * Returns the stored value.
     * This method appears to be incorrectly implemented and may need revision.
     *
     * @param var1 The key.
     * @param var2 The value to be put.
     * @return The current value stored.
     */
    public B put(A var1, B var2) {
        return value;
    }

    /**
     * Returns a string representation of the TwoTuple.
     *
     * @return A string in the format (key, value).
     */
    @Override
    public String toString() {
        return "(" + key + ", " + value + ")";
    }
}
