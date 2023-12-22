package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.entity.User;
import ru.mai.information_system.service.UserService;
import ru.mai.information_system.service.UserServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class UsersHandler implements HttpHandler {

    private final UserService USER_SERVICE = new UserServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
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

    private void handleGetUserByEmail(HttpExchange exchange, String email) {
        User user = USER_SERVICE.getUserByEmail(email);

        String response;
        if (user != null) {
            response = user.toString();
        } else {
            response = "User not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddUser(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            User user = GSON.fromJson(requestBody, User.class);
            User userFromDB = USER_SERVICE.getUserByEmail(user.getEmail());

            if (userFromDB == null) {
                USER_SERVICE.saveUser(user);
                response = "User added successfully";
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
