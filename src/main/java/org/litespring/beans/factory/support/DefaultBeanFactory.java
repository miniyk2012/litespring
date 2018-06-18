package org.litespring.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DefaultSingletonRegistry;
import org.litespring.util.ClassUtils;

public class DefaultBeanFactory extends DefaultSingletonRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
	private ClassLoader classLoader;

    public DefaultBeanFactory() {}

	@Override
	public BeanDefinition getBeanDefinition(String beanID) {
			
		return this.beanDefinitionMap.get(beanID);
	}

    @Override
	public void registerBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID, bd);
    }

    @Override
	public Object getBean(String beanID) {
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if(bd == null){
			throw new BeanCreationException("Bean Definition does not exist");
		}
		if (bd.isSingleton()) {
		    Object bean = getSingleton(beanID);
		    if (bean == null) {
                bean = createBean(bd);
                resgisterSingleton(beanID, bean);
            }
		    return bean;
        }
        return createBean(bd);
	}

    private Object createBean(BeanDefinition bd) {
        ClassLoader cl = getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed", e);
        }

    }

    @Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public ClassLoader getBeanClassLoader() {
		return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
	}
}
