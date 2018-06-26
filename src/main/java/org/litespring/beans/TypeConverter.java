package org.litespring.beans;

/**
 * Created by thomas_young on 27/6/2018.
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
