# Java Web App (Servlet) Starter

A minimal Maven-based Java web application used for COMP0004. This project is aimed at junior developers who are learning how a basic Java web app is structured and how to run it locally.

## What you are looking at

- **Java + Servlet API**: A simple servlet handles HTTP requests.
- **Maven**: Manages dependencies, compilation, and packaging.
- **WAR file**: The web app is packaged as a `.war` for deployment to a servlet container (like Tomcat).

## Prerequisites

- **JDK 8+** installed and `java`/`javac` on your PATH
- **Maven 3+** installed and `mvn` on your PATH

Check versions:

```bash
java -version
mvn -version
```

## Project layout (high level)

```
.
├── pom.xml                  # Maven build config
├── src/
│   └── main/
│       ├── java/             # Java source code
│       └── webapp/           # Web resources (WEB-INF, web.xml, etc.)
└── target/                   # Build output (generated)
```

## Build (compile + package)

From the project root:

```bash
mvn clean package
```

This will:

- Compile your Java code
- Run tests (if any)
- Create a WAR file in `target/`

## Run locally (preferred: embedded Tomcat via Maven)

The easiest way to run this project is through Maven using the embedded Tomcat setup. This is the **preferred** method because Maven will download and manage the required dependencies automatically.

From the project root:

```bash
mvn clean package
mvn exec:exec
```

Then open:

```
http://localhost:8080/
```

> Tip: Keep the terminal running while the server is up. Stop it with `Ctrl+C`.

## Run locally (using an external container)

This project packages a **WAR**, so you run it in a servlet container such as **Tomcat**.

1) Build the WAR:

```bash
mvn clean package
```

2) Copy the WAR to Tomcat’s `webapps/` folder:

```bash
cp target/*.war /path/to/tomcat/webapps/
```

3) Start Tomcat (example):

```bash
/path/to/tomcat/bin/startup.sh
```

4) Open your browser:

```
http://localhost:8080/
```

> Tip: Tomcat will deploy the WAR automatically when it appears in `webapps/`.

## Useful Maven commands

- `mvn clean` — remove old build output
- `mvn compile` — compile the code only
- `mvn test` — run tests
- `mvn package` — build the WAR

## Common issues

- **Port already in use**: Another service is using `8080`. Stop it or change Tomcat’s port.
- **Missing Java**: Ensure `JAVA_HOME` is set and `java -version` works.
- **Maven not found**: Install Maven and ensure `mvn` is on your PATH.

## Next steps for learning

- Add another servlet and map it in `web.xml`
- Explore request parameters and responses
- Try a simple HTML form and process it in a servlet

---

If you’re new to Java web apps, focus on the build/run cycle first: **edit → `mvn package` → deploy WAR → refresh browser**.
