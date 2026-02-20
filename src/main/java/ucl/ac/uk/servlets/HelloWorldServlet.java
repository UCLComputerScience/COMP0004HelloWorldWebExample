package ucl.ac.uk.servlets;

// Annotation used to declare the URL mapping for this servlet.
import jakarta.servlet.annotation.WebServlet;
// Base class for HTTP servlets; provides doGet/doPost hooks.
import jakarta.servlet.http.HttpServlet;
// Represents the incoming HTTP request (headers, parameters, locale, etc.).
import jakarta.servlet.http.HttpServletRequest;
// Represents the outgoing HTTP response (status code, headers, body, etc.).
import jakarta.servlet.http.HttpServletResponse;

// Exception type for input/output problems (e.g., writing to the response stream).
import java.io.IOException;
// Writer used to send character (text) data back to the client.
import java.io.PrintWriter;

// This servlet is registered using the @WebServlet annotation.
// The string value is the URL path that will be mapped to this servlet.
// When a browser requests http://<host>:<port>/helloworldservlet, the container calls doGet().
@WebServlet("/helloworldservlet")
// A servlet is a Java class managed by a servlet container (e.g., Tomcat).
// Extending HttpServlet lets us handle HTTP requests such as GET and POST by overriding methods like doGet().
public class HelloWorldServlet extends HttpServlet
{
  // doGet() is called by the container for HTTP GET requests.
  // We declare `throws IOException` because writing to the HTTP response involves I/O operations
  // (for example, the client disconnecting can cause an IOException).
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException
  {
    // Tell the container we will write UTF-8 text. This affects how characters are encoded into bytes.
    response.setCharacterEncoding("UTF-8");
    // Tell the client (browser) what kind of content we are returning: HTML encoded as UTF-8.
    response.setContentType("text/html; charset=UTF-8");
    // Capture the current date/time. ZonedDateTime includes a time-zone.
    var now = java.time.ZonedDateTime.now();
    // Build a date/time formatter for displaying the date in a human-friendly way.
    // FormatStyle.MEDIUM chooses a reasonable default format for the user's locale.
    // We use request.getLocale() so the output matches the browser's preferred language/region.
    var formatter = java.time.format.DateTimeFormatter.
                    ofLocalizedDateTime(java.time.format.FormatStyle.MEDIUM).
                    withLocale(request.getLocale());

    // Get a character writer for the HTTP response body.
    // try-with-resources ensures the writer is closed automatically at the end of the block,
    // which also helps flush any buffered output.
    try (PrintWriter out = response.getWriter())
    {
      // Write a minimal HTML document. In real applications you would typically use templates such as JSP or Thymeleaf.
      out.println("<html>");
      out.println("<head><title>Hello, World</title></head>");
      out.println("<body>");
      // A heading for the page.
      out.println("<h1>Hello, from the Servlet World!</h1>");
      out.println("<hr>");
      // Interpolate a formatted date/time string into the HTML.
      out.println("<p>Today's date is: " + formatter.format(now) + "</p>");
      out.println("<hr>");
      // A link back to a static page in the web application.
      out.println("<a href='index.html'>Back to the default index.html</a>");
      out.println("</body></html>");
    }
    // Error handling strategy:
    //  - IOException usually indicates a low-level I/O problem (e.g., client disconnected) while writing the response.
    //    In that case there is often nothing useful we can send back to the client, so we log and rethrow.
    //  - RuntimeException indicates a programming or application error while generating the page.
    //    If we still can, we return HTTP 500 (Internal Server Error) and log the details server-side.
    catch (IOException e)
    {
      // Log the full stack trace to the server logs for debugging.
      getServletContext().log("I/O error while generating HelloWorld response", e);
      throw e;
    }
    catch (RuntimeException e)
    {
      // Log the exception so the root cause is visible in server logs.
      getServletContext().log("Unexpected error while generating HelloWorld response", e);

      // If the response has not been committed yet, we can still change headers/status and send an error.
      // Once committed (some bytes already sent), it's too late to reliably change the HTTP status code.
      if (!response.isCommitted())
      {
        // Clear any partially written headers/body so we can send a clean error response.
        response.reset();
        // Send HTTP 500 to the client. The container may render an error page based on configuration.
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
      else
      {
        // Re-throw so the container can handle/report it; we cannot change the already-started response.
        throw e;
      }
    }
  }
}