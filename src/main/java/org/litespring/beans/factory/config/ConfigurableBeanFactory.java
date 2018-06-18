package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * Created by thomas_young on 18/6/2018.
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader classLoader);
    ClassLoader getBeanClassLoader();
}
