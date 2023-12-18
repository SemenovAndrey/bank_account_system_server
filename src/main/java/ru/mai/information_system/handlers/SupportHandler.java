package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.entity.Support;
import ru.mai.information_system.service.SupportService;
import ru.mai.information_system.service.SupportServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SupportHandler implements HttpHandler {

    private final SupportService SUPPORT_SERVICE = new SupportServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
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
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetSupports(HttpExchange exchange) throws IOException {
        List<Support> supports = SUPPORT_SERVICE.getAllSupports();
        String response = supports.toString();
        sendResponse(exchange, response);
    }

    private void handleGetSupportById(HttpExchange exchange, String supportId) throws IOException {
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

    private void handleAddSupport(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Support support = GSON.fromJson(requestBody, Support.class);
        SUPPORT_SERVICE.saveSupport(support);

        String response = "Support message added successfully";
        sendResponse(exchange, response);
    }

    private void handleDeleteSupport(HttpExchange exchange, String supportId) throws IOException {
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
