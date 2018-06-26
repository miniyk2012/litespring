package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v2.AccountDao;
import org.litespring.dao.v2.ItemDao;
import org.litespring.service.v2.PetStoreService;
import org.junit.contrib.java.lang.system.SystemOutRule;
/**
 * Created by thomas_young on 26/6/2018.
 */
public class ApplicationContextTestV2 {

    @Rule
    public final SystemOutRule log = new SystemOutRule().enableLog();

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService)ctx.getBean("petStore");

        AccountDao accountDao = petStoreService.getAccountDao();
        ItemDao itemDao = petStoreService.getItemDao();

        Assert.assertNotNull(accountDao);
        Assert.assertNotNull(itemDao);

        Assert.assertTrue(accountDao instanceof AccountDao);
        Assert.assertTrue(itemDao instanceof ItemDao);


        Assert.assertEquals("liuxin",petStoreService.getOwner());
        Assert.assertEquals(2, petStoreService.getVersion());

        System.out.println(petStoreService);
        Assert.assertEquals("PetStoreService{accountDao=org.litespring.dao.v2.AccountDao," +
                " itemDao=org.litespring.dao.v2.ItemDao, owner='liuxin', version=2}\n", log.getLog());
    }
}
