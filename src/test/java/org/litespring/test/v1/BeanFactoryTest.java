package org.litespring.test.v1;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {

	DefaultBeanFactory factory = null;
	XmlBeanDefinitionReader reader = null;
    ApplicationContext ctx = null;

	@Before
    public void setUp() {
	    factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
    }

	@Test
	public void testGetSingletonBean() {
		Resource classPathResource = new ClassPathResource("petstore-v1.xml");
		reader.loadBeanDefinitions(classPathResource);
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        assertTrue(bd.isSingleton());
        assertFalse(bd.isPrototype());
        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());
		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
		
		PetStoreService petStore1 = (PetStoreService)factory.getBean("petStore");
		
		assertNotNull(petStore1);

        PetStoreService petStore2 = (PetStoreService)factory.getBean("petStore");

        assertSame(petStore1, petStore2);


        // ctx测试一下
        petStore1 = (PetStoreService)ctx.getBean("petStore");

        assertNotNull(petStore1);

        petStore2 = (PetStoreService)ctx.getBean("petStore");

        assertSame(petStore1, petStore2);

    }

    @Test
    public void testGetPrototypeBean() {

        PetStoreService petStore1 = (PetStoreService)ctx.getBean("petStore2");

        assertNotNull(petStore1);

        PetStoreService petStore2 = (PetStoreService)ctx.getBean("petStore2");

        assertNotSame(petStore1, petStore2);
    }

	@Test
	public void testInvalidBean(){
		Resource classPathResource = new ClassPathResource("petstore-v1.xml");
        reader.loadBeanDefinitions(classPathResource);
        try{
			factory.getBean("invalidBean");
		}catch(BeanCreationException e){
			return;
		}
		Assert.fail("expect BeanCreationException ");
	}
	@Test
	public void testInvalidXML(){
		try{
			Resource classPathResource = new ClassPathResource("xxx.xml");
            reader.loadBeanDefinitions(classPathResource);
        }catch(BeanDefinitionStoreException e){
			return;
		}
		Assert.fail("expect BeanDefinitionStoreException ");
	}



}
