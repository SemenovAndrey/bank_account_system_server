package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.entity.Support;
import ru.mai.information_system.service.SupportService;
import ru.mai.information_system.service.SupportServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class SupportHandler implements HttpHandler {

    private final SupportService SUPPORT_SERVICE = new SupportServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/support";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetSupports(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetSupportById(exchange, path.split("/")[2]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddSupport(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteSupport(exchange, path.split("/")[2]);
        } else {
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            exchange.close();
        }
    }

    private void handleGetSupports(HttpExchange exchange) {
        List<Support> supports = SUPPORT_SERVICE.getAllSupports();
        String response = supports.toString();
        sendResponse(exchange, response);
    }

    private void handleGetSupportById(HttpExchange exchange, String supportId) {
        int id = Integer.parseInt(supportId);
        Support support = SUPPORT_SERVICE.getSupportById(id);

        String response;
        if (support != null) {
            response = support.toString();
        } else {
            response = "Support not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddSupport(HttpExchange exchange) {
        String requestBody;
        String response;
        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            Support support = GSON.fromJson(requestBody, Support.class);
            SUPPORT_SERVICE.saveSupport(support);
            response = "Support message added successfully";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteSupport(HttpExchange exchange, String supportId) {
        int id = Integer.parseInt(supportId);
        Support support = SUPPORT_SERVICE.getSupportById(id);

        String response;
        if (support == null) {
            response = "Support message not found";
        } else {
            SUPPORT_SERVICE.deleteSupportById(id);
            response = "Support message deleted successfully";
        }

        sendResponse(exchange, response);
    }
}
