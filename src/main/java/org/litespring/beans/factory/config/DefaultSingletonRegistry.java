package org.litespring.beans.factory.config;

import org.litespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by thomas_young on 18/6/2018.
 */
public class DefaultSingletonRegistry implements SingletonBeanRegistry {
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    @Override
    public void resgisterSingleton(String beanID, Object obj) {
        Assert.notNull(beanID, "beanID is null");
        Object object = this.singletonObjects.get(beanID);
        if (object != null) {
            throw new IllegalStateException("The beanID " + beanID + " has exited, cant register.");
        }
        this.singletonObjects.put(beanID, obj);
    }

    @Override
    public Object getSingleton(String beanID) {
        return this.singletonObjects.get(beanID);
    }
}
