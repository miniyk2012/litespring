package org.litespring.beans;


/**
 * Created by thomas_young on 26/6/2018.
 */
public class PropertyValue {
    private String name;
    private Object value;
    private Object convertedValue;
    private boolean isConverted;

    public Object getConvertedValue() {
        return convertedValue;
    }

    public void setConvertedValue(Object convertedValue) {
        this.convertedValue = true;
        this.convertedValue = convertedValue;
    }

    public boolean isConverted() {
        return isConverted;
    }

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
