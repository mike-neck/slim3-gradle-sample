package org.mikeneck.gae.slim3.sample.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mikeneck.gae.slim3.sample.util.ControllerTestRule;
import org.slim3.tester.ControllerTester;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}
