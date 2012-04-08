package org.mikeneck.gae.slim3.sample.util;

import org.junit.rules.ExternalResource;
import org.slim3.tester.AppEngineTester;

/**
 * @author : mike
 * @since : 12/04/09
 */
public class AppengineResource extends ExternalResource {

    AppEngineTester tester = new AppEngineTester();

    /**
     * shuts down {@code AppEngineTester}.
     * @throws RuntimeException
     */
    @Override
    protected void after() throws RuntimeException {
        try {
            tester.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * prepares {@code AppEngineTester}
     * @throws Throwable
     */
    @Override
    protected void before() throws Throwable {
        tester.setUp();
    }
}
