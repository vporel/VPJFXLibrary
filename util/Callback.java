package vplibrary.util;


/**
 * @param <P> The type of the argument provided to the <code>call</code> method.
 * @param <R> The type of the return type of the <code>call</code> method.
 */
@FunctionalInterface
public interface Callback<P,R> {
    /**
     * The <code>call</code> method is called when required, and is given a
     * single argument of type P, with a requirement that an object of type R
     * is returned.
     *
     * @param param The single argument upon which the returned value should be
     *      determined.
     * @return An object of type R that may be determined based on the provided
     *      parameter value.
     */
    public R call(P param);
}
