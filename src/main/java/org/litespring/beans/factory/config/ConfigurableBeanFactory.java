package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * Created by thomas_young on 18/6/2018.
 * classLoader用在两个地方，一个是用在Resource的路径上，另一个是用在创建bean实例时
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader classLoader);
    ClassLoader getBeanClassLoader();
}
