package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.entity.User;
import ru.mai.information_system.service.UserService;
import ru.mai.information_system.service.UserServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UsersHandler implements HttpHandler {

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/users";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetUsers(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetUserById(exchange, path.split("/")[2]);
        } else if (path.startsWith(localPath + "/email") && path.split("/").length == 4
                && exchange.getRequestMethod().equals("GET")) {
            handleGetUserByEmail(exchange, path.split("/")[3]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddUser(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateUser(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteUser(exchange, path.split("/")[2]);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetUsers(HttpExchange exchange) throws IOException {
        List<User> users = USER_SERVICE.getAllUsers();
        String response = users.toString();
        sendResponse(exchange, response);
    }

    private void handleGetUserById(HttpExchange exchange, String userId) throws IOException {
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

    private void handleGetUserByEmail(HttpExchange exchange, String email) throws IOException {
        User user = USER_SERVICE.getUserByEmail(email);

        String response;
        if (user != null) {
            response = user.toString();
        } else {
            response = "User not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddUser(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = GSON.fromJson(requestBody, User.class);
        User userFromDB = USER_SERVICE.getUserByEmail(user.getEmail());

        String response;
        if (userFromDB == null) {
            USER_SERVICE.saveUser(user);
            response = "User added successfully";
        } else {
            response = "User with this email already created";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateUser(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = GSON.fromJson(requestBody, User.class);
        User userFromDB = USER_SERVICE.getUserById(user.getId());

        String response;
        if (userFromDB == null) {
            response = "User not found";
        } else {
            USER_SERVICE.saveUser(user);
            response = "User successfully updated";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteUser(HttpExchange exchange, String userId) throws IOException {
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

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response.getBytes());
        } catch (Exception e) {
            System.err.println("Error");
            System.out.println(e.getMessage());
        }
    }
}
