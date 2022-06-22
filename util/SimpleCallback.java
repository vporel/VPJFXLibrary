package vplibrary.util;


/**
 * @param <P> The type of the argument provided to the <code>call</code> method.
 */
@FunctionalInterface
public interface SimpleCallback<P> {
    /**
     * The <code>call</code> method is called when required, and is given a
     * single argument of type P,
     *
     * @param param The single argument upon which the returned value should be
     *      determined.
     */
    public void call(P param);
}
