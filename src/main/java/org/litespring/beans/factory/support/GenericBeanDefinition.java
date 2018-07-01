package org.litespring.beans.factory.support;

import java.util.ArrayList;
import java.util.List;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

/**
 * @author thomas_young
 */
public class GenericBeanDefinition implements BeanDefinition {
	private String id;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;
	private ConstructorArgument constructorArgument;
	List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

	public GenericBeanDefinition(String id, String beanClassName) {
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
    public String getBeanClassName() {
		
		return this.beanClassName;
	}
	
	@Override
    public boolean isSingleton() {
		return this.singleton;
	}
	@Override
    public boolean isPrototype() {
		return this.prototype;
	}
	@Override
    public String getScope() {
		return this.scope;
	}
	@Override
    public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);
		
	}

	@Override
	public List<PropertyValue> getPropertyValues(){
		return this.propertyValues;
	}

	@Override
	public ConstructorArgument getConstructorArgument() {
		return this.constructorArgument;
	}

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setConstructorArgument(ConstructorArgument constructorArgument) {
        this.constructorArgument = constructorArgument;
    }
}
