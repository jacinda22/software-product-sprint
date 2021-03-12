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
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;


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

    String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
    String email = Jsoup.clean(request.getParameter("email"), Whitelist.none());
    String reason = Jsoup.clean(request.getParameter("reason"), Whitelist.none());
    long timestamp1 = System.currentTimeMillis();
    
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Task");

    FullEntity taskEntity1 =
    Entity.newBuilder(keyFactory.newKey())
        .set("name", name)
        .set("timestamp", timestamp1)
        .build();
    datastore.put(taskEntity1);

    FullEntity taskEntity2 =
    Entity.newBuilder(keyFactory.newKey())
        .set("email", email)
        .set("timestamp", timestamp1)
        .build();
    datastore.put(taskEntity2);

    FullEntity taskEntity3 =
    Entity.newBuilder(keyFactory.newKey())
        .set("reason", reason)
        .set("timestamp", timestamp1)
        .build();
    datastore.put(taskEntity3);

    Query<Entity> query = 
        Query.newEntityQueryBuilder().setKind("Recom").setOrderBy(OrderBy.desc("timestamp")).build();
    QueryResults<Entity> results = datastore.run(query);

    ArrayList<String> recommendation = new ArrayList<String>();
    while (results.hasNext()) {
        Entity entity = results.next();
        
        //long id = entity.getKey().getId();
        String msg1 = entity.getString("msg1");
        //long timestamp2 = entity.getLong("timestamp2");

        recommendation.add(msg1);
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(recommendation));  
       
    }

    
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}