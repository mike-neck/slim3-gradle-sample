package org.mikeneck.gae.slim3.sample.service;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.common.base.Flag;
import org.junit.Test;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 12/04/05
 * Time: 15:51
 * To change this template use File | Settings | File Templates.
 */
public class GuestbookServiceTest extends AppEngineTestCase {

    private static final String GUEST_BOOK = "Guestbook";

    @Test
    public void saveToDatastore () {
        int before = tester.count(GUEST_BOOK);
        GuestbookService.saveToDatastore("test");
        int after = tester.count(GUEST_BOOK);
        assertThat(after, is(before + 1));
    }

    @Test
    public void testSaveToDatastore () {
        Key key = GuestbookService.saveToDatastore("Test message");

        Entity entity = Datastore.get(key);
        String message = (String) entity.getProperty(GuestbookService.MESSAGE);
        assertThat(message, is("Test message"));

        Date property = (Date) entity.getProperty(GuestbookService.CREATED_AT);
        assertThat(property, is(notNullValue(Date.class)));
    }

    @Test
    public void queryFromDatastore () {
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 5; i += 1) {
            Entity entity = new Entity(GuestbookService.GUEST_BOOK);
            entity.setProperty(GuestbookService.MESSAGE, "message" + 1);
            entity.setProperty(GuestbookService.CREATED_AT, calendar.getTime());
            Datastore.put(entity);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }

        Date before = null;
        List<Entity> entities = GuestbookService.queryFromDatastore();
        assertThat(entities.size(), is(5));
        for (Entity entity : entities) {
            Date createdAt = (Date) entity.getProperty(GuestbookService.CREATED_AT);

            if (before != null) {
                assertThat(before.compareTo(createdAt), is(greaterThan(0)));
            }

            before = createdAt;
        }
    }
}
