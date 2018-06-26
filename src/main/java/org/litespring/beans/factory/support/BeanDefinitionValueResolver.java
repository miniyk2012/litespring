package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * Created by thomas_young on 26/6/2018.
 */
public class BeanDefinitionValueResolver {
    private BeanFactory factory;

    public BeanDefinitionValueResolver(BeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            return factory.getBean(((RuntimeBeanReference) value).getBeanName());
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            throw new RuntimeException("the value " + value +" has not implemented");
        }
    }
}
