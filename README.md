# Student Registration Web App

A student registration form built with HTML, a Java servlet, Apache Tomcat, and MySQL. This version does not use Maven.

## Requirements

- Eclipse IDE for Enterprise Java and Web Developers
- JDK 17 or newer
- Apache Tomcat 10.1
- MySQL Server
- MySQL Connector/J, included in `src/main/webapp/WEB-INF/lib`

## Database

Create a database named `student_registration` and a `students` table with these columns:

- `id`: auto-incrementing primary key
- `full_name`: student name
- `email`: unique email address
- `phone`: phone number
- `course`: course or program
- `registered_at`: registration time

## Run locally

1. Create a Dynamic Web Project in Eclipse and target Apache Tomcat 10.1.
2. Copy the repository’s `src/main/java` and `src/main/webapp` folders into the matching project folders.
3. In MySQL Workbench, run the database setup SQL.
4. In the Tomcat run configuration, set `DB_USER` to `root` and `DB_PASSWORD` to your MySQL root password.
5. Run the project on Tomcat and open `http://localhost:8080/StudentRegistrationWeb/`.
