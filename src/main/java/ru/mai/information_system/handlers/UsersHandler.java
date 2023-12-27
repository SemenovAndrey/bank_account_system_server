package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mai.information_system.entity.User;
import ru.mai.information_system.service.UserService;
import ru.mai.information_system.service.UserServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class UsersHandler implements HttpHandler {

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final Gson GSON = new Gson();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/users";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetUsers(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetUserById(exchange, path.split("/")[2]);
        } else if (path.startsWith(localPath + "/idByEmail") && path.split("/").length == 4
                && exchange.getRequestMethod().equals("GET")) {
            handleGetUserIdByEmail(exchange, path.split("/")[3]);
        } else if (path.startsWith(localPath + "/nameById") && path.split("/").length == 4
                && exchange.getRequestMethod().equals("GET")) {
            handleGetUserNameById(exchange, path.split("/")[3]);
        } else if (path.equals(localPath + "/auth") && exchange.getRequestMethod().equals("POST")) {
            handleUserAuth(exchange);
        } else if (path.equals(localPath + "/reg") && exchange.getRequestMethod().equals("POST")) {
            handleUserRegister(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateUser(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteUser(exchange, path.split("/")[2]);
        } else {
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            exchange.close();
        }
    }

    private void handleGetUsers(HttpExchange exchange) {
        List<User> users = USER_SERVICE.getAllUsers();
        String response = users.toString();
        sendResponse(exchange, response);
    }

    private void handleGetUserById(HttpExchange exchange, String userId) {
        int id = Integer.parseInt(userId);
        User user = USER_SERVICE.getUserById(id);

        String response;
        if (user != null) {
            response = user.toString();
        } else {
            response = "User not found";
        }

        sendResponse(exchange, response);
    }

    private void handleGetUserNameById(HttpExchange exchange, String userId) {
        int id = Integer.parseInt(userId);
        User user = USER_SERVICE.getUserById(id);

        String response;
        if (user != null) {
            response = user.getName();
        } else {
            response = "User not found";
        }

        sendResponse(exchange, response);
    }

    private void handleGetUserIdByEmail(HttpExchange exchange, String email) {
        User user = USER_SERVICE.getUserByEmail(email);

        String response;
        if (user != null) {
            response = user.getId() + "";
        } else {
            response = "User not found";
        }

        sendResponse(exchange, response);
    }

    private void handleUserAuth(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            User user = GSON.fromJson(requestBody, User.class);
            User userFromDB = USER_SERVICE.getUserByEmail(user.getEmail());

            System.out.println(user.getPassword());
            System.out.println(userFromDB.getPassword());
            if (passwordEncoder.matches(user.getPassword(), userFromDB.getPassword())) {
                response = "Authentication was successful";
            } else {
                response = "Authentication was unsuccessful";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleUserRegister(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            User user = GSON.fromJson(requestBody, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User userFromDB = USER_SERVICE.getUserByEmail(user.getEmail());

            if (userFromDB == null) {
                USER_SERVICE.saveUser(user);
                response = "User added successful";
            } else {
                response = "User with this email already created";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateUser(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            User user = GSON.fromJson(requestBody, User.class);
            User userFromDB = USER_SERVICE.getUserById(user.getId());

            if (userFromDB == null) {
                response = "User not found";
            } else {
                USER_SERVICE.saveUser(user);
                response = "User successfully updated";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteUser(HttpExchange exchange, String userId) {
        int id = Integer.parseInt(userId);
        User user = USER_SERVICE.getUserById(id);

        String response;
        if (user == null) {
            response = "User not found";
        } else {
            USER_SERVICE.deleteUserById(id);
            response = "User deleted successfully";
        }

        sendResponse(exchange, response);
    }
}
