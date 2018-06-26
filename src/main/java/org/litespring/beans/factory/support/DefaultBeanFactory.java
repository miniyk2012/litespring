package org.litespring.beans.factory.support;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;
import java.beans.Introspector;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry 
	implements ConfigurableBeanFactory,BeanDefinitionRegistry{

	
	
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
	private ClassLoader beanClassLoader;
	
	public DefaultBeanFactory() {
		
	}

	public void registerBeanDefinition(String beanID,BeanDefinition bd){
		this.beanDefinitionMap.put(beanID, bd);
	}
	public BeanDefinition getBeanDefinition(String beanID) {
			
		return this.beanDefinitionMap.get(beanID);
	}

	public Object getBean(String beanID) {
		BeanDefinition bd = this.getBeanDefinition(beanID);
		if(bd == null){
			return null;
		}
		
		if(bd.isSingleton()){
			Object bean = this.getSingleton(beanID);
			if(bean == null){
				bean = createBean(bd);
				this.registerSingleton(beanID, bean);
			}
			return bean;
		} 
		return createBean(bd);
	}

    /**
     * 对bean做setter注入
     * @param bean
     * @param bd
     */
    private void setterInject(Object bean, BeanDefinition bd) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue propertyValue: propertyValues) {
            BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
            setOneField(bean, propertyValue, resolver);
        }
    }

    /**
     * 对一个field做set
     * @param bean
     * @param propertyValue
     * @param resolver
     */
    private void setOneField(Object bean, PropertyValue propertyValue, BeanDefinitionValueResolver resolver) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();
            if(proDescrtptors != null && proDescrtptors.length > 0){
                boolean setSuccess = false;
                for(PropertyDescriptor propDesc:proDescrtptors){
                    if(propDesc.getName().equals(propertyValue.getName())){
                        Method setMethod = propDesc.getWriteMethod();
                        Object value = resolver.resolveValueIfNecessary(propertyValue.getValue());
                        setMethod.invoke(bean, value);
                        setSuccess = true;
                        break;
                    }
                }
                if (!setSuccess) {
                    throw new BeanCreationException("set field for "+ bean.getClass().getName() + ". " + propertyValue.getName() +" failed");
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("set field for "+ bean.getClass().getName() + "." + propertyValue.getName() +" failed", e);
        }
    }

    private Object createBean(BeanDefinition bd) {
        Object bean =  instantiateBean(bd);
        setterInject(bean, bd);
        return bean;
	}

    private Object instantiateBean(BeanDefinition bd) {
        ClassLoader cl = this.getBeanClassLoader();
        String beanClassName = bd.getBeanClassName();
        try {
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
		this.beanClassLoader = beanClassLoader;
	}

    public ClassLoader getBeanClassLoader() {
		return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
	}
}
