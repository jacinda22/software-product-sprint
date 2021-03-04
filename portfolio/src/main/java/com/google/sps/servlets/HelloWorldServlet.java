package com.google.sps.servlets;

import java.util.ArrayList;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //converting arraylist to JSON
    ArrayList<String> messages = new ArrayList<String>();  
    fillingArrList(messages);
    Gson gson = new Gson();
    String convertedList = gson.toJson(messages);
    
    response.setContentType("application/json;");
    response.getWriter().println(convertedList);
  }

  public void fillingArrList(ArrayList<String> list){
    list.add("I love java");
    list.add("Music is amazing");
    list.add("Chocolate is delicious");
  }

}
