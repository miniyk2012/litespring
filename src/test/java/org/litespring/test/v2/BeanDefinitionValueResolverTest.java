package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.dao.v2.AccountDao;

import org.litespring.beans.factory.support.BeanDefinitionValueResolver;


/**
 * Created by thomas_young on 26/6/2018.
 */
public class BeanDefinitionValueResolverTest {

    DefaultBeanFactory factory;
    XmlBeanDefinitionReader reader;
    BeanDefinitionValueResolver resolver;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));
        resolver = new BeanDefinitionValueResolver(factory);
    }

    @Test
    public void testResolveRuntimeBeanReference() {
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);
        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        TypedStringValue value = new TypedStringValue("liuxin");
        Object convertedValue = resolver.resolveValueIfNecessary(value);

        Assert.assertNotNull(convertedValue);
        Assert.assertTrue(convertedValue instanceof String);
        Assert.assertEquals("liuxin", convertedValue);
    }

}
