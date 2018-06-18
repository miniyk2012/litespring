package org.litespring.beans.factory.config;

/**
 * Created by thomas_young on 18/6/2018.
 */
public interface SingletonBeanRegistry {
    void resgisterSingleton(String beanID, Object obj);
    Object getSingleton(String beanID);
}
