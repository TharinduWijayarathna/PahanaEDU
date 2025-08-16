package edu.pahana.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for all service classes
 * Runs all service tests together
 */
@RunWith(Suite.class)
@SuiteClasses({
    UserServiceTest.class,
    CustomerServiceTest.class,
    ProductServiceTest.class,
    BillServiceTest.class
})
public class ServiceTestSuite {
    // This class remains empty, it is used only as a holder for the above annotations
}
