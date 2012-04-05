package org.mikeneck.gae.slim3.sample;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import org.junit.Before;
import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 12/04/04
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
public class GuestbookServletTest extends AppEngineTestCase {

    private static final FetchOptions FETCH_OPTIONS = FetchOptions.Builder.withDefaults();

    private DatastoreService service;

    @Override
    @Before
    public void setUp () throws Exception {
        super.setUp();
        service = DatastoreServiceFactory.getDatastoreService();
    }

    @Test
    public void testSaveToDatastore () {
        Query query = new Query("Guestbook");
        int before = service.prepare(query).countEntities(FETCH_OPTIONS);

        GuestBookServlet.storeData("test");

        int after = service.prepare(query).countEntities(FETCH_OPTIONS);

        assertThat(after, is(before + 1));
    }
}
