package org.litespring.beans;


/**
 * Created by thomas_young on 26/6/2018.
 */
public class PropertyValue {
    private String name;
    private Object value;

    public Object getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
