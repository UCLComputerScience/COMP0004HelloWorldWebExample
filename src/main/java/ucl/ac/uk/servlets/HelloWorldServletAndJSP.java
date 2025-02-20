package ucl.ac.uk.servlets;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/helloworldservletandjsp.html")
public class HelloWorldServletAndJSP extends HttpServlet
{
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    // Code to use the model to process something would go here.

    // Then forward to JSP.
    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/helloworld.jsp");
    dispatch.forward(request, response);
  }
}