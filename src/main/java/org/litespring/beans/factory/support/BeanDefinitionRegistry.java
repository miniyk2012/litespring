package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * Created by thomas_young on 16/6/2018.
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String beanID, BeanDefinition bd);
}
