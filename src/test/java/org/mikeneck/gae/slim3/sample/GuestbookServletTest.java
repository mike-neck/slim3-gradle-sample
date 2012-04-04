package org.mikeneck.gae.slim3.sample;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 12/04/04
 * Time: 20:05
 * To change this template use File | Settings | File Templates.
 */
public class GuestbookServletTest {

    private static final FetchOptions FETCH_OPTIONS = FetchOptions.Builder.withDefaults();

    private DatastoreService service;

    @Before
    public void setUp () {
        service = DatastoreServiceFactory.getDatastoreService();
    }

    @Test
    public void testSaveToDatastore () {
        Query query = new Query("Guestbook");
    }
}
