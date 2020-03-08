package ucl.ac.uk.servlets;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/helloworldservlet.html")
public class HelloWorldServlet extends HttpServlet
{
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    response.setContentType("text/html");
    try (PrintWriter out = response.getWriter())
    {
      Date date = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

      out.println("<html>");
      out.println("<head><title>Hello, World</title></head>");
      out.println("<body>");
      out.println("<h1>Hello, from the Servlet World!</h1>");
      out.println("<hr>");
      out.println("<p>Today's date is: " + formatter.format(date) + "</p>");
      out.println("<hr>");
      out.println("<a href='index.html'>Back to the default index.html</a>");
      out.println("</body></html>");
    }
  }
}