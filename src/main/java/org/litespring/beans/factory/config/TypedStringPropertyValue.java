package org.litespring.beans.factory.config;

import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanFactory;

/**
 * Created by thomas_young on 30/6/2018.
 */
public class TypedStringPropertyValue extends PropertyValue{
    public TypedStringPropertyValue(String name, String value) {
        super(name, value);
    }

    @Override
    public Object resolve(BeanFactory factory) {
        return getValue();
    }
}
