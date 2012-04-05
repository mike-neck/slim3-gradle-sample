package org.mikeneck.gae.slim3.sample.service;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import org.slim3.datastore.Datastore;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 12/04/05
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */
public class GuestbookService {

    public static final String GUEST_BOOK = "Guestbook";
    public static final String MESSAGE = "message";
    public static final String CREATED_AT = "createdAt";

    public static Key saveToDatastore(String message) {
        Entity entity = new Entity(GUEST_BOOK);
        entity.setProperty(MESSAGE, message);
        entity.setProperty(CREATED_AT, new Date());

        return Datastore.put(entity);
    }

    public static List<Entity> queryFromDatastore() {
        return Datastore.query(GUEST_BOOK).sort(CREATED_AT, Query.SortDirection.DESCENDING).asList();
    }
}
