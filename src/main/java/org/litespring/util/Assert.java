package org.litespring.util;

/**
 * Created by thomas_young on 18/6/2018.
 */
public abstract class Assert {

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
