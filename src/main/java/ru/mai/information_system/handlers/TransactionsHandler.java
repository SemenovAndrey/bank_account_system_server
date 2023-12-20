package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.TransactionDTO;
import ru.mai.information_system.entity.Transaction;
import ru.mai.information_system.service.TransactionService;
import ru.mai.information_system.service.TransactionServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TransactionsHandler implements HttpHandler {

    private final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/transaction_categories";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactions(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionById(exchange, path.split("/")[2]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddTransaction(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateTransaction(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteTransaction(exchange, path.split("/")[2]);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetTransactions(HttpExchange exchange) throws IOException {
        List<Transaction> transactions = TRANSACTION_SERVICE.getAllTransactions();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionDTOList.add(transaction.toTransactionDTO());
        }

        String response = transactionDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionById(HttpExchange exchange, String transactionId) throws IOException {
        int id = Integer.parseInt(transactionId);
        Transaction transaction = TRANSACTION_SERVICE.getTransactionById(id);

        String response;
        if (transaction != null) {
            response = transaction.toString();
        } else {
            response = "Transaction not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddTransaction(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        TransactionDTO transactionDTO = GSON.fromJson(requestBody, TransactionDTO.class);
        Transaction transaction = transactionDTO.toTransactionEntity();
        TRANSACTION_SERVICE.saveTransaction(transaction);
        String response = "Transaction added successfully";

        sendResponse(exchange, response);
    }

    private void handleUpdateTransaction(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        TransactionDTO transactionDTO = GSON.fromJson(requestBody, TransactionDTO.class);
        Transaction transactionFromDB = TRANSACTION_SERVICE.getTransactionById(transactionDTO.getId());

        String response;
        if (transactionFromDB == null) {
            response = "Transaction not found";
        } else {
            Transaction transaction = transactionDTO.toTransactionEntity();
            TRANSACTION_SERVICE.saveTransaction(transaction);
            response = "Transaction updated successfully";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteTransaction(HttpExchange exchange, String transactionId) throws IOException {
        int id = Integer.parseInt(transactionId);
        Transaction transaction = TRANSACTION_SERVICE.getTransactionById(id);

        String response;
        if (transaction == null) {
            response = "Transaction not found";
        } else {
            TRANSACTION_SERVICE.deleteTransactionById(id);
            response = "Transaction deleted successfully";
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
