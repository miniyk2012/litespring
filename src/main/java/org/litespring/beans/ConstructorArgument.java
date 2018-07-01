package org.litespring.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author thomas_young
 * @date 1/7/2018
 */
public class ConstructorArgument {
    private final List<ValueHolder> argumentValues = new LinkedList<>();

    /**
     * Create a new empty ConstructorArgumentValues object.
     */
    public ConstructorArgument() {
    }

    public List<ValueHolder> getArgumentValues() {
        return Collections.unmodifiableList(this.argumentValues);
    }

    public void addArgumentValue(ValueHolder valueHolder) {
        this.argumentValues.add(valueHolder);
    }

    public int getArgumentCount() {
        return this.argumentValues.size();
    }

    public boolean isEmpty() {
        return this.argumentValues.isEmpty();
    }

    public void clear() {
        this.argumentValues.clear();
    }

    /**
     * Holder for a constructor argument value, with an optional type
     * attribute indicating the target type of the actual constructor argument.
     */
    public static class ValueHolder {
        private Object value;

        private String type;

        private String name;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
