package org.mikeneck.gae.slim3.sample.controller;

import org.mikeneck.gae.slim3.sample.service.GuestbookService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: mike
 * @since: 12/04/09
 */
public class GuestbookController extends Controller {

    private static final String TYPE = "text/html";
    private static final String UTF_8 = "utf-8";

    @Override
    protected Navigation run() throws Exception {
        if (isGet()) {
            return doGet();
        } else {
            return doPost();
        }
    }

    private Navigation doPost() {
        String message = asString("message");
        GuestbookService.saveToDatastore(message);
        return redirect("/guestbook");
    }

    private Navigation doGet() throws IOException {
        response.setContentType(TYPE);
        response.setCharacterEncoding(UTF_8);

        PrintWriter writer = response.getWriter();
        println(writer, "<html>")
                .println(writer, "<head>")
                .println(writer, "<title>")
                .println(writer, "ゲストブック")
                .println(writer, "</title>")
                .println(writer, "</head>")
                .println(writer, "<body>")
                .println(writer, "<h1>なにかよろ！</h1>")
                .println(writer, "<div>")
                .println(writer, "<form action='/guestbook' method='post'>")
                .println(writer, "<p>")
                .println(writer, "<input type='text' name='message' id='message' size='50'/>")
                .println(writer, "<input type='submit'/>")
                .println(writer, "</p>")
                .println(writer, "</form>")
                .println(writer, "</div>")
                .println(writer, "</body>")
                .println(writer, "</html>");
        response.flushBuffer();
        return null;
    }

    private GuestbookController println(PrintWriter writer, String text) {
        writer.println(text);
        return this;
    }
}
