package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.TransactionCategoryDTO;
import ru.mai.information_system.entity.TransactionCategory;
import ru.mai.information_system.service.TransactionCategoryService;
import ru.mai.information_system.service.TransactionCategoryServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class TransactionCategoriesHandler implements HttpHandler {

    private final TransactionCategoryService TRANSACTION_CATEGORY_SERVICE = new TransactionCategoryServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/transaction_categories";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionCategories(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionCategoryById(exchange, path.split("/")[2]);
        } else if (path.startsWith(localPath + "/userId") && path.split("/").length == 4
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionCategoriesByUserId(exchange, path.split("/")[3]);
        } else if (path.startsWith(localPath + "/userIdAndCategory") && path.split("/").length == 5
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionCategoriesByUserIdAndCategory(exchange, path.split("/")[3],
                    path.split("/")[4]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddTransactionCategory(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateTransactionCategory(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteTransactionCategory(exchange, path.split("/")[2]);
        } else {
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            exchange.close();
        }
    }

    private void handleGetTransactionCategories(HttpExchange exchange) {
        List<TransactionCategory> transactionCategories = TRANSACTION_CATEGORY_SERVICE.getAllTransactionCategories();
        List<TransactionCategoryDTO> transactionCategoryDTOList = new ArrayList<>();

        for (TransactionCategory transactionCategory : transactionCategories) {
            transactionCategoryDTOList.add(transactionCategory.toTransactionCategoryDTO());
        }

        String response = transactionCategoryDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionCategoryById(HttpExchange exchange, String transactionCategoryId) {
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

    private void handleGetTransactionCategoriesByUserId(HttpExchange exchange, String requestUserId) {
        int userId = Integer.parseInt(requestUserId);
        List<TransactionCategory> transactionCategories = TRANSACTION_CATEGORY_SERVICE
                .getTransactionCategoryByUserId(userId);
        List<TransactionCategoryDTO> transactionCategoryDTOList = new ArrayList<>();
        for (TransactionCategory transactionCategory : transactionCategories) {
            transactionCategoryDTOList.add(transactionCategory.toTransactionCategoryDTO());
        }

        String response = transactionCategoryDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionCategoriesByUserIdAndCategory(HttpExchange exchange, String requestUserId,
                                                                   String category) {
        int userId = Integer.parseInt(requestUserId);
        TransactionCategory transactionCategory = TRANSACTION_CATEGORY_SERVICE
                .getTransactionCategoryByUserIdAndCategory(userId, category);

        String response;
        if (transactionCategory != null) {
            TransactionCategoryDTO transactionCategoryDTO = transactionCategory.toTransactionCategoryDTO();
            response = transactionCategoryDTO.toString();
        } else {
            response = "Transaction category not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddTransactionCategory(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            TransactionCategoryDTO transactionCategoryDTO = GSON.fromJson(requestBody, TransactionCategoryDTO.class);
            TransactionCategory transactionCategoryFromDB = TRANSACTION_CATEGORY_SERVICE
                    .getTransactionCategoryByUserIdAndCategory(transactionCategoryDTO.getUserId(),
                            transactionCategoryDTO.getCategory());

            if (transactionCategoryFromDB == null) {
                TransactionCategory transactionCategory = transactionCategoryDTO.toTransactionCategoryEntity();
                TRANSACTION_CATEGORY_SERVICE.saveTransactionCategory(transactionCategory);
                response = "Transaction category added successfully";
            } else {
                response = "Transaction category with this name already created";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateTransactionCategory(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            TransactionCategoryDTO transactionCategoryDTO = GSON.fromJson(requestBody, TransactionCategoryDTO.class);
            TransactionCategory transactionCategoryFromDB = TRANSACTION_CATEGORY_SERVICE
                    .getTransactionCategoryById(transactionCategoryDTO.getId());

            if (transactionCategoryFromDB == null) {
                response = "Transaction category not found";
            } else {
                TransactionCategory transactionCategory = transactionCategoryDTO.toTransactionCategoryEntity();
                TRANSACTION_CATEGORY_SERVICE.saveTransactionCategory(transactionCategory);
                response = "Transaction category added successfully";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteTransactionCategory(HttpExchange exchange, String transactionCategoryId) {
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
}
