package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @author thomas_young
 */
public class GenericBeanDefinition implements BeanDefinition {
	private String id;
	private String beanClassName;
	private String scope;

	public GenericBeanDefinition(String id, String beanClassName, String scope) {
		this.scope = (scope == null ? "" : scope);
		this.id = id;
		this.beanClassName = beanClassName;
	}

	@Override
    public String getBeanClassName() {
		
		return this.beanClassName;
	}

	@Override
	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
	}

	@Override
	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(scope);
	}

    @Override
    public String getScope() {
        return this.scope;
    }

}
