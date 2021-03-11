package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //Get the value(s) entered in the form.
    String nameValue = request.getParameter("name");
    String emailValue = request.getParameter("email");
    String reasonValue = request.getParameter("reason");
    boolean contactEmail = Boolean.parseBoolean(getParameter(request, "contact-email", "false"));
    boolean contactPhone = Boolean.parseBoolean(getParameter(request, "contact-phone", "false"));
    boolean contactGit = Boolean.parseBoolean(getParameter(request, "contact-git", "false"));

    //Print what the user submitted/if they requested info in the server logs.
    if(contactEmail || contactPhone || contactGit)
        System.out.println("Information Entered:\nName: " + nameValue + "\nEmail: "  + emailValue + "\nReason: " + reasonValue +"\nContact information was requested.");
    else
        System.out.println("Information Entered:\nName: " + nameValue + "\nEmail: "  + emailValue + "\nReason: " + reasonValue);

    //Print what the user submitted/if they requested info
    if(contactEmail || contactPhone || contactGit)
        response.getWriter().println("Information Entered:\nName: " + nameValue + "\nEmail: "  + emailValue + "\nReason: " + reasonValue + "\nYou requested to receive contact informtation.");
    else
        response.getWriter().println("Information Entered:\nName " + nameValue + "\nEmail: "  + emailValue + "\nReason: " + reasonValue);

    //redirect user to the main page: figure out how user can see above info & then be redirected
    //response.getWriter().println("You are now being redirected to the main page");
    //response.sendRedirect("http://www.jsoto-sps-spring21.appspot.com/");

    //Testing out datastore 
    String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
    String email = Jsoup.clean(request.getParameter("email"), Whitelist.none());
    String reason = Jsoup.clean(request.getParameter("reason"), Whitelist.none());
    long timestamp = System.currentTimeMillis();
    
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Task");

    FullEntity taskEntity1 =
    Entity.newBuilder(keyFactory.newKey())
        .set("name", name)
        .set("timestamp", timestamp)
        .build();
    datastore.put(taskEntity1);

    FullEntity taskEntity2 =
    Entity.newBuilder(keyFactory.newKey())
        .set("email", email)
        .set("timestamp", timestamp)
        .build();
    datastore.put(taskEntity2);

    FullEntity taskEntity3 =
    Entity.newBuilder(keyFactory.newKey())
        .set("reason", reason)
        .set("timestamp", timestamp)
        .build();
    datastore.put(taskEntity3);
        
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}