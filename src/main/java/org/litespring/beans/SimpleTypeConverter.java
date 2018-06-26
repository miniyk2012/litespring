package org.litespring.beans;


import org.litespring.beans.propertyeditors.CustomBooleanEditor;
import org.litespring.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.ClassUtils;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas_young on 27/6/2018.
 */
public class SimpleTypeConverter implements TypeConverter {
    private Map<Class, PropertyEditor> propertyEditorMap = new HashMap<Class, PropertyEditor>(10);

    public <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException {
        if(ClassUtils.isAssignableValue(requiredType, value)){
            return (T)value;
        }
        if (!(value instanceof String)) {
            throw new RuntimeException("Todo : can't convert value for "+ value +" to class:"+requiredType);
        }
        PropertyEditor propertyEditor = findPropertyEditor(requiredType);
        try {
            propertyEditor.setAsText((String) value);
        } catch (IllegalArgumentException e) {
            throw new TypeMismatchException(value, requiredType);
        }
        return (T)propertyEditor.getValue();
    }

    private <T> PropertyEditor findPropertyEditor(Class<T> requiredType) {
        if (propertyEditorMap.size() != 0) {
            return propertyEditorMap.get(requiredType);
        }
        initiateProperties();
        return propertyEditorMap.get(requiredType);
    }

    private void initiateProperties() {
        propertyEditorMap.put(Integer.class, new CustomNumberEditor(Integer.class, true));
        propertyEditorMap.put(int.class, new CustomNumberEditor(Integer.class, false));
        propertyEditorMap.put(Boolean.class, new CustomBooleanEditor(true));
        propertyEditorMap.put(boolean.class, new CustomBooleanEditor(false));
    }
}
