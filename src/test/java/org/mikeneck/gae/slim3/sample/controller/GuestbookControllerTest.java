package org.mikeneck.gae.slim3.sample.controller;

import com.google.appengine.api.datastore.Entity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mikeneck.gae.slim3.sample.util.ControllerTestRule;
import org.slim3.datastore.Datastore;
import org.slim3.tester.ControllerTester;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author : mike
 * @since : 12/04/09
 */
@RunWith(Enclosed.class)
public class GuestbookControllerTest {

    public static class Get {
        @Rule
        public static ControllerTestRule testRule = new ControllerTestRule();

        private ControllerTester tester;

        @Before
        public void testSetUp () throws IOException, ServletException {
            tester = testRule.getTester();
            tester.request.setMethod("GET");
            tester.start("/guestbook");
        }

        @Test
        public void returns200() {
            assertThat(testRule.getMethodName(), tester.response.getStatus(), is(HttpServletResponse.SC_OK));
        }

        @Test
        public void assertsGuestbookController () {
            assertThat(testRule.getMethodName(), tester.getController(), instanceOf(GuestbookController.class));
        }

        @Test
        public void contentsIsNotNullAndBlank () throws IOException {
            assertThat(testRule.getMethodName(), tester.response.getOutputAsString(), is(notNullValue()));
        }

        @Test
        public void containsHtml () throws IOException {
            assertThat(testRule.getMethodName(), tester.response.getOutputAsString(), containsString("<html"));
        }
    }

    public static class Post {

        @Rule
        public ControllerTestRule testRule = new ControllerTestRule();

        private ControllerTester tester;

        @Before
        public void testSetUp () throws IOException, ServletException {
            tester = testRule.getTester();
            tester.request.setMethod("POST");
            tester.param("message", "投稿内容");
            tester.start("/guestbook");
        }

        @Test
        public void instanceIsGuestbookController () {
            assertThat(testRule.getMethodName(), tester.getController(), is(instanceOf(GuestbookController.class)));
        }

        @Test
        public void requiresRedirect () {
            assertThat(testRule.getMethodName(), tester.isRedirect(), is(true));
        }

        @Test
        public void destinationIsSlashGuestbook () {
            assertThat(testRule.getMethodName(), tester.getDestinationPath(), is("/guestbook"));
        }
    }

    public static class List {

        @Rule
        public ControllerTestRule testRule = new ControllerTestRule();

        private ControllerTester tester;

        @Before
        public void testSetUp () throws IOException, ServletException {
            tester = testRule.getTester();
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < 5; i += 1) {
                Entity entity = new Entity("Guestbook");
                entity.setProperty("message", "メッセージ : " + i);
                entity.setProperty("createdAt", calendar.getTime());
                Datastore.put(entity);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }

            tester.request.setMethod("GET");
            tester.start("/guestbook");
        }

        @Test
        public void statusIs200 () {
            assertThat(testRule.getMethodName(), tester.response.getStatus(), is(200));
        }

        @Test
        public void instanceIsGuestbookController () {
            assertThat(testRule.getMethodName(), tester.getController(), is(instanceOf(GuestbookController.class)));
        }

        @Test
        public void contentIsNotNullNorEmpty () throws IOException {
            assertThat(testRule.getMethodName(), tester.response.getOutputAsString(), is(notNullValue()));
        }

        @Test
        public void contentContainsTag_li () throws IOException {
            String output = tester.response.getOutputAsString();
            assertThat(testRule.getMethodName(), output, containsString("<li>"));
            assertThat(testRule.getMethodName(), output, containsString("</li>"));
        }
    }
}
