package com.example.studentregistration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentRegistrationServlet extends HttpServlet {
    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/student_registration?serverTimezone=UTC";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String course = request.getParameter("course");

        if (isBlank(fullName) || isBlank(email) || isBlank(phone) || isBlank(course)) {
            showMessage(response, "Missing information", "Please complete every field.");
            return;
        }

        String url = System.getenv().getOrDefault("DB_URL", DEFAULT_URL);
        String user = System.getenv().getOrDefault("DB_USER", "root");
        String password = System.getenv().getOrDefault("DB_PASSWORD", "");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ServletException("MySQL Connector/J is not available to Tomcat.", ex);
        }

        String sql = "INSERT INTO students (full_name, email, phone, course) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fullName.trim());
            statement.setString(2, email.trim());
            statement.setString(3, phone.trim());
            statement.setString(4, course.trim());
            statement.executeUpdate();

            showMessage(response, "Registration successful",
                    "The student was saved to the database.");

        } catch (SQLException ex) {
            getServletContext().log("Could not save student registration.", ex);

            if (ex.getErrorCode() == 1062) {
                showMessage(response, "Email already registered",
                        "That email address is already in the database.");
            } else {
                showMessage(response, "Could not save registration",
                        "Check the MySQL connection settings.");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void showMessage(HttpServletResponse response, String title, String message)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(
                "<!doctype html><html lang=\"en\"><meta charset=\"UTF-8\">" +
                "<title>" + title + "</title>" +
                "<body style=\"font-family:Arial;margin:40px\">" +
                "<h1>" + title + "</h1><p>" + message + "</p>" +
                "<a href=\"index.html\">Back to registration</a>" +
                "</body></html>");
    }
}