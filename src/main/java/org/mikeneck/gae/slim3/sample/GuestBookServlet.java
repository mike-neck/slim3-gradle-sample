package org.mikeneck.gae.slim3.sample;

import com.google.appengine.api.datastore.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 12/04/04
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
public class GuestBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html lang='ja'>");
        writer.println("<head>");
        writer.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"");
        writer.println("<title>Guest Book</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>何か書いて</h1>");
        writer.println("<p>");
        writer.println("<form action='/guestbook' method='post'>");
        writer.println("<input type='text' name='message'/>");
        writer.println("<input type='submit' />");
        writer.println("</form>");
        writer.println("</p>");

        Iterable<Entity> entities = getData();

        writer.println("<p>");
        writer.println("<ul>");

        for (Entity entity : entities) {
            writer.println(
                    "<li>" +
                            entity.getProperty("message") +
                            "(" +
                            entity.getProperty("createdAt") +
                            ")</li>"
            );
        }

        writer.println("</ul>");
        writer.println("</p>");

        writer.println("</body>");
        writer.println("</html>");
        resp.flushBuffer();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        storeData(message);
        resp.sendRedirect("/guestbook");
    }

    static void storeData(String message) {
        Entity entity = new Entity("Guestbook");
        entity.setProperty("message", message);
        entity.setProperty("createdAt", new Date());

        DatastoreService service = DatastoreServiceFactory.getDatastoreService();
        service.put(entity);
    }

    private Iterable<Entity> getData() {
        Query query = new Query("Guestbook");
        query.addSort("createdAt", Query.SortDirection.DESCENDING);

        DatastoreService service = DatastoreServiceFactory.getDatastoreService();
        return service.prepare(query).asIterable();
    }
}
