package org.litespring.beans;

import java.util.List;

public interface BeanDefinition {
	public static final String SCOPE_SINGLETON = "singleton";
	public static final String SCOPE_PROTOTYPE = "prototype";
	public static final String SCOPE_DEFAULT = "";
	
	public boolean isSingleton();
	public boolean isPrototype();
	String getScope();
	void setScope(String scope);
	
	public String getBeanClassName();

    List<PropertyValue> getPropertyValues();

	void setPropertyValues(List<PropertyValue> propertyValues);
}
