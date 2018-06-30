package org.litespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.V1AllTests;
import org.litespring.test.v2.V2AllTests;

/**
 * Created by thomas_young on 26/6/2018.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTests.class,V2AllTests.class})
public class AllTests {
}