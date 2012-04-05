package org.mikeneck.gae.slim3.sample;

import com.google.appengine.api.datastore.*;
import org.junit.Before;
import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

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

    @Test
    public void testQueryFromDatastore () {
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i < 5 + 1; i += 1) {
            Entity entity = new Entity("Guestbook");
            entity.setProperty("message", "test" + i);
            entity.setProperty("createdAt", calendar.getTime());
            service.put(entity);
            calendar.add(Calendar.DATE, -1);
        }

        Query query = new Query("Guestbook");
        List<Entity> list = service.prepare(query).asList(FETCH_OPTIONS);
        assertThat(list.size(), is(5));

        Date before = null;
        Iterable<Entity> data = GuestBookServlet.getData();
        List<Entity> entities = setList(data);
        assertThat(entities.size(), is(5));
        for (Entity entity : entities) {
            Date createdAt = (Date) entity.getProperty("createdAt");
            if (before != null) {
                assertThat(createdAt.compareTo(before), is(greaterThan(0)));
            }
        }
    }

    List<Entity> setList(Iterable<Entity> iterable) {
        List<Entity> list = new ArrayList<Entity>(5);
        for (Entity entity : iterable) {
            list.add(entity);
        }
        return list;
    }
}
