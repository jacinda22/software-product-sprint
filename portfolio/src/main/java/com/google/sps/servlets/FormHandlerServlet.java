package com.google.sps.servlets;

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
    String textValue = request.getParameter("text-input");
    boolean contactEmail = Boolean.parseBoolean(getParameter(request, "contact-email", "false"));
    boolean contactPhone = Boolean.parseBoolean(getParameter(request, "contact-phone", "false"));
    boolean contactGit = Boolean.parseBoolean(getParameter(request, "contact-git", "false"));

    //Print what the user submitted/if they requested info in the server logs.
    if(contactEmail || contactPhone || contactGit)
        System.out.println("Submitted: " + textValue + "\nContact information was requested.");
    else
        System.out.println("Submitted: " + textValue);

    //Print what the user submitted/if they requested info
    if(contactEmail || contactPhone || contactGit)
        response.getWriter().println("You submitted: " + textValue + "\nYou requested to receive contact informtation.");
    else
        response.getWriter().println("You submitted: " + textValue);

    //redirect user to the main page: figure out how user can see above info & then be redirected
    //response.getWriter().println("You are now being redirected to the main page");
    //response.sendRedirect("http://www.jsoto-sps-spring21.appspot.com/");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}