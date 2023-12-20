package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.TransactionCategoryDTO;
import ru.mai.information_system.entity.TransactionCategory;
import ru.mai.information_system.service.TransactionCategoryService;
import ru.mai.information_system.service.TransactionCategoryServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TransactionCategoriesHandler implements HttpHandler {

    private final TransactionCategoryService TRANSACTION_CATEGORY_SERVICE = new TransactionCategoryServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/transaction_categories";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionCategories(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionCategoryById(exchange, path.split("/")[2]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddTransactionCategory(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateTransactionCategory(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteTransactionCategory(exchange, path.split("/")[2]);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetTransactionCategories(HttpExchange exchange) throws IOException {
        List<TransactionCategory> transactionCategories = TRANSACTION_CATEGORY_SERVICE.getAllTransactionCategories();
        List<TransactionCategoryDTO> transactionCategoryDTOList = new ArrayList<>();

        for (TransactionCategory transactionCategory : transactionCategories) {
            transactionCategoryDTOList.add(transactionCategory.transactionCategoryDTO());
        }

        String response = transactionCategoryDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionCategoryById(HttpExchange exchange, String transactionCategoryId) throws IOException {
        int id = Integer.parseInt(transactionCategoryId);
        TransactionCategory transactionCategory = TRANSACTION_CATEGORY_SERVICE.getTransactionCategoryById(id);

        String response;
        if (transactionCategory != null) {
            response = transactionCategory.toString();
        } else {
            response = "User not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddTransactionCategory(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        TransactionCategoryDTO transactionCategoryDTO = GSON.fromJson(requestBody, TransactionCategoryDTO.class);
        TransactionCategory transactionCategoryFromDB = TRANSACTION_CATEGORY_SERVICE
                .getTransactionCategoryByUserIdAndCategory(transactionCategoryDTO.getUserId(),
                                                            transactionCategoryDTO.getCategory());

        String response;
        if (transactionCategoryFromDB == null) {
            TransactionCategory transactionCategory = transactionCategoryDTO.toTransactionCategoryEntity();
            TRANSACTION_CATEGORY_SERVICE.saveTransactionCategory(transactionCategory);
            response = "Transaction category added successfully";
        } else {
            response = "Transaction category with this name already created";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateTransactionCategory(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        TransactionCategoryDTO transactionCategoryDTO = GSON.fromJson(requestBody, TransactionCategoryDTO.class);
        TransactionCategory transactionCategoryFromDB = TRANSACTION_CATEGORY_SERVICE
                .getTransactionCategoryById(transactionCategoryDTO.getId());

        String response;
        if (transactionCategoryFromDB == null) {
            response = "Transaction category not found";
        } else {
            TransactionCategory transactionCategory = transactionCategoryDTO.toTransactionCategoryEntity();
            TRANSACTION_CATEGORY_SERVICE.saveTransactionCategory(transactionCategory);
            response = "Transaction category added successfully";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteTransactionCategory(HttpExchange exchange, String transactionCategoryId) throws IOException {
        int id = Integer.parseInt(transactionCategoryId);
        TransactionCategory transactionCategory = TRANSACTION_CATEGORY_SERVICE.getTransactionCategoryById(id);

        String response;
        if (transactionCategory == null) {
            response = "Transaction category not found";
        } else {
            TRANSACTION_CATEGORY_SERVICE.deleteTransactionCategoryById(id);
            response = "Transaction category deleted successfully";
        }

        sendResponse(exchange, response);
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(200, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        } catch (Exception e) {
            System.err.println("Error");
            System.out.println(e.getMessage());
        }
    }
}
