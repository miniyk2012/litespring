package org.litespring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.litespring.test.v2.*;

@RunWith(Suite.class)
@SuiteClasses({ApplicationContextTestV3.class, BeanDefinitionTestV3.class})
public class V3AllTests {

}