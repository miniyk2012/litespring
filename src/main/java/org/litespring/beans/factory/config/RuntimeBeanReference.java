package org.litespring.beans.factory.config;

/**
 * Created by thomas_young on 26/6/2018.
 */
public class RuntimeBeanReference {

    private final String beanName;

    public String getBeanName() {
        return beanName;
    }

    public RuntimeBeanReference(String beanName) {

        this.beanName = beanName;
    }
}
