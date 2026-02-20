package ucl.ac.uk.servlets;

// This servlet demonstrates the MVC idea using a JSP for the "View":
//  - The servlet acts as the Controller: it handles the HTTP request.
//  - The servlet may call some "Model" code (business logic / data access).
//  - Finally it forwards to a JSP which renders HTML for the browser.
//
// Forwarding to a JSP keeps HTML out of Java code and makes pages easier to maintain.

// Exception type for I/O problems (for example, the client disconnecting while we write a response).
import java.io.IOException;

// Used to forward (dispatch) a request from a servlet to another resource, such as a JSP.
import jakarta.servlet.RequestDispatcher;
// Gives access to container-managed resources for this web application.
import jakarta.servlet.ServletContext;
// Exception type for servlet/JSP errors (for example, forwarding failing).
import jakarta.servlet.ServletException;
// Annotation that declares the URL path that triggers this servlet.
import jakarta.servlet.annotation.WebServlet;
// Base class for HTTP servlets; provides doGet/doPost hooks.
import jakarta.servlet.http.HttpServlet;
// Represents the incoming HTTP request (parameters, headers, locale, etc.).
import jakarta.servlet.http.HttpServletRequest;
// Represents the outgoing HTTP response (status, headers, body, etc.).
import jakarta.servlet.http.HttpServletResponse;
// Map this servlet to a URL path.
// When a browser requests http://<host>:<port>/helloworldservletandjsp, the container will call doGet().
@WebServlet("/helloworldservletandjsp")
// The servlet container (e.g., Tomcat) instantiates this class and calls its lifecycle methods.
// Extending HttpServlet allows us to handle HTTP methods like GET by overriding doGet().
public class HelloWorldServletAndJSP extends HttpServlet
{
  // doGet() is called for HTTP GET requests.
  // We declare `throws IOException` because response forwarding/writing may involve I/O.
  // We also declare `throws ServletException` because forwarding to a JSP may fail and is represented by ServletException.
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
  {
    // We use a try/catch so we can:
    //  - log errors with a stack trace (useful for debugging)
    //  - send an HTTP 500 response if something goes wrong (when possible)
    try
    {
      // In a real application, you would typically do some work here:
      //  - call "Model" code (business logic) to compute a result
      //  - read/write data from a database
      //  - validate inputs
      //  - store data for the JSP using request attributes, e.g.:
      //      request.setAttribute("message", "Hello from the model");
      //
      // When that work is complete, we forward to a JSP which renders the HTML.
      // The ServletContext represents the web application as a whole.
      // It can be used to look up resources and to obtain a RequestDispatcher.
      ServletContext context = getServletContext();
      // A RequestDispatcher can forward this request to another server-side resource.
      // The path is relative to the web application root. Here we forward to /helloworld.jsp.
      RequestDispatcher dispatch = context.getRequestDispatcher("/helloworld.jsp");
      // Forward the SAME request/response objects to the JSP.
      // The browser URL does not change (this is server-side forwarding, not a redirect).
      // After forward() returns, you must not write further output to the response.
      dispatch.forward(request, response);
    }
    // Error handling strategy:
    //  - IOException usually indicates a low-level I/O problem (e.g., client disconnected).
    //    There is often nothing useful we can send back to the client, so we log and rethrow.
    //  - ServletException indicates a problem during forwarding/JSP processing.
    //  - RuntimeException indicates a programming or application bug.
    //    If the response is not committed yet, we attempt to send HTTP 500.
    catch (IOException e)
    {
      // Log the full stack trace to the server logs for debugging.
      getServletContext().log("I/O error while generating HelloWorld response", e);
      throw e;
    }
    // Handle servlet/JSP errors and unexpected runtime errors in one place.
    catch (ServletException | RuntimeException e)
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
  // End of servlet.
}