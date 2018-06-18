package org.litespring.context.support;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/**
 * Created by thomas_young on 16/6/2018.
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String path) {
        super(path);
    }

    @Override
    protected Resource getResouceByPath(String path) {
        return new ClassPathResource(path, this.getBeanClassLoader());
    }
}
