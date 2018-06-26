package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMismatchException;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.fail;

/**
 * Created by thomas_young on 26/6/2018.
 */
public class TypeConverterTest {

    @Test
    public void testConvertStringToInt() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3,i.intValue());

        try{
            converter.convertIfNecessary("3.1", Integer.class);
        }catch(TypeMismatchException e){
            return;
        }
        fail();
    }
    @Test
    public void testConvertStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertEquals(true, b.booleanValue());

        try{
            converter.convertIfNecessary("xxxyyyzzz", Boolean.class);
        }catch(TypeMismatchException e){
            return;
        }
        fail();
    }

    @Test
    public void testConvertFail() {
        TypeConverter converter = new SimpleTypeConverter();
        try {
            // Todo : can't convert value for org.litespring.service.v1.PetStoreService@36aa7bc2 to class:class java.lang.Boolean
            converter.convertIfNecessary(new PetStoreService(), Boolean.class);
        } catch (RuntimeException e) {
            return;
        }
        fail();
    }

    @Test
    public void testOtherTypeConvert() {
        TypeConverter converter = new SimpleTypeConverter();
        PetStoreService petStoreService = converter.convertIfNecessary(new PetStoreService(), PetStoreService.class);
        Assert.assertTrue(petStoreService instanceof PetStoreService);
    }
}
