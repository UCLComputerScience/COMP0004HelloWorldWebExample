<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Hello from the JSP page</title>
</head>
<body>
  <h1>Hello from the Java Server Page!</h1>
  <%
    String message = "This is a message in a Java String";
    var now = java.time.ZonedDateTime.now();
    var formatter = java.time.format.DateTimeFormatter.
      ofLocalizedDateTime(java.time.format.FormatStyle.MEDIUM).
      withLocale(request.getLocale());
  %>
  <p><%=message%></p>
  <hr>
  <p>Today's date is: <%=formatter.format(now)%></p>
  <hr>
  <a href='index.html'>Back to the default index.html</a>
</body>
</html>
