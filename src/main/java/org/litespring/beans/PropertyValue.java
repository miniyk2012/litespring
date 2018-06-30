package org.litespring.beans;


import org.litespring.beans.factory.BeanFactory;

/**
 * Created by thomas_young on 26/6/2018.
 */
public abstract class PropertyValue {
    private String name;
    private String value;

    public abstract Object resolve(BeanFactory factory);

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PropertyValue(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
