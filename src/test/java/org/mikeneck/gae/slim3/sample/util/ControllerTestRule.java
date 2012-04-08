package org.mikeneck.gae.slim3.sample.util;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slim3.tester.ControllerTester;

/**
 * @author: mike
 * @since: 12/04/09
 */
public class ControllerTestRule implements TestRule {

    private Class<?> testClass;

    private ControllerTester tester;

    /**
     * {@inheritDoc}
     */
    @Override
    public Statement apply(Statement base, Description description) {
        testClass = description.getTestClass();
        tester = new ControllerTester(testClass);
        return statement(base);
    }

    /**
     * run a test.
     * @param base - test statement.
     * @return statement runs a test base statement.
     */
    private Statement statement (final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try{
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }

    /**
     * prepare test environment
     * @throws RuntimeException
     */
    private void before () throws RuntimeException {
        try {
            tester.setUp();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * finalize each test.
     * @throws RuntimeException - if something wrong happens.
     */
    private void after () throws RuntimeException {
        try {
            tester.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * get {@code ControllerTester}
     * @return ControllerTester of the test class.
     */
    public ControllerTester getTester() {
        return tester;
    }
}
