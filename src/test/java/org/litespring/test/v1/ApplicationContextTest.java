package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

import java.io.File;
import java.util.Properties;

/**
 * Created by thomas_young on 16/6/2018.
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("src/test/resources/petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testPath() {
        System.out.println(System.getProperty("user.dir"));  // 相对路径相是根据工程路径

        Properties properties   =   System.getProperties();
        System.out.println(properties.getProperty("user.dir"));

        File file=new File("");
        System.out.println(file.getAbsolutePath());

        System.out.println(this.getClass().getResource("/"));

    }
}
