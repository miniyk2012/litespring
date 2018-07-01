package org.litespring.beans.factory.support;

import org.litespring.beans.*;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 *
 * @author thomas_young
 * @date 1/7/2018
 */
public class ConstructorResolver {

    private ConfigurableBeanFactory factory;

    public ConstructorResolver(ConfigurableBeanFactory factory) {
        this.factory = factory;
    }

    public Object autowireConstructor(BeanDefinition bd) {
        try {
            if (bd.getConstructorArgument().isEmpty()) {
                return factory.getBeanClassLoader().loadClass(bd.getBeanClassName()).newInstance();
            }
            ConstructorArgument constructorArgument = bd.getConstructorArgument();
            BeanDefinitionValueResolver beanDefinitionValueResolver = new BeanDefinitionValueResolver(factory);
            Object[] args = new Object[constructorArgument.getArgumentCount()];
            TypeConverter typeConverter = new SimpleTypeConverter();

                Class<?> cls = factory.getBeanClassLoader().loadClass(bd.getBeanClassName());
                Constructor<?>[] constructors = cls.getConstructors();
                Constructor<?> constructor = valueMatchTypes(typeConverter, beanDefinitionValueResolver,
                        constructorArgument, constructors, args);
                if (constructor != null) {
                    return constructor.newInstance(args);
                }
                throw new BeanCreationException("available constructor not found!");
        } catch (BeanCreationException e) {
            throw e;
        } catch (Exception e) {
            throw new BeanCreationException("autowrie constructor for " + bd.getBeanClassName() + " failed");
        }
    }

    /**
     *  搜索与constructorArgument匹配的constructor, 若存在返回该constructor, 不存在返回空
     *
     * @param typeConverter
     * @param valueResolver
     * @param constructorArgument
     * @param constructors
     * @param args
     * @return
     */
    private Constructor<?> valueMatchTypes(TypeConverter typeConverter, BeanDefinitionValueResolver valueResolver,
                                           ConstructorArgument constructorArgument, Constructor<?>[] constructors,
                                           Object[] args) {
        List<ConstructorArgument.ValueHolder> valueHolders = constructorArgument.getArgumentValues();
        for (Constructor<?> constructor: constructors) {
            if (constructor.getParameterCount() != valueHolders.size()) {
                continue;
            }
            int i = 0;
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            for (ConstructorArgument.ValueHolder valueHolder : valueHolders) {
                Class<?> parameterType = parameterTypes[i];
                Object val = valueHolder.getValue();
                Object convertedValue;
                try {
                    convertedValue = typeConverter.convertIfNecessary(valueResolver.resolveValueIfNecessary(val), parameterType);
                } catch (RuntimeException e) {
                    break;
                }
                    args[i] = convertedValue;
                if (convertedValue == null) {
                    break;
                }
                i++;
            }
            if (i == valueHolders.size()) {
                return constructor;
            }
        }
        return null;
    }
}
