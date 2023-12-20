package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.TransactionByDateDTO;
import ru.mai.information_system.entity.TransactionByDate;
import ru.mai.information_system.service.TransactionByDateService;
import ru.mai.information_system.service.TransactionByDateServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TransactionsByDateHandler implements HttpHandler {

    private final TransactionByDateService TRANSACTION_BY_DATE_SERVICE = new TransactionByDateServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/transaction_categories";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionsByDate(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionByDateById(exchange, path.split("/")[2]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddTransactionByDate(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateTransactionByDate(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteTransactionByDate(exchange, path.split("/")[2]);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetTransactionsByDate(HttpExchange exchange) throws IOException {
        List<TransactionByDate> transactionsByDate = TRANSACTION_BY_DATE_SERVICE.getAllTransactionsByDate();
        List<TransactionByDateDTO> transactionByDateDTOList = new ArrayList<>();

        for (TransactionByDate transactionByDate : transactionsByDate) {
            transactionByDateDTOList.add(transactionByDate.toTransactionByDateDTO());
        }

        String response = transactionByDateDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionByDateById(HttpExchange exchange, String transactionByDateId) throws IOException {
        int id = Integer.parseInt(transactionByDateId);
        TransactionByDate transactionByDate = TRANSACTION_BY_DATE_SERVICE.getTransactionByDateById(id);

        String response;
        if (transactionByDate != null) {
            response = transactionByDate.toString();
        } else {
            response = "Transaction by date not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddTransactionByDate(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        TransactionByDateDTO transactionByDateDTO = GSON.fromJson(requestBody, TransactionByDateDTO.class);
        TransactionByDate transactionByDate = transactionByDateDTO.toTransactionByDateEntity();
        TRANSACTION_BY_DATE_SERVICE.saveTransactionByDate(transactionByDate);
        String response = "Transaction by date added successfully";

        sendResponse(exchange, response);
    }

    private void handleUpdateTransactionByDate(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        TransactionByDateDTO transactionByDateDTO = GSON.fromJson(requestBody, TransactionByDateDTO.class);
        TransactionByDate transactionByDateFromDB = TRANSACTION_BY_DATE_SERVICE.getTransactionByDateById(transactionByDateDTO.getId());
        String response;
        if (transactionByDateFromDB == null) {
            response = "Transaction by date not found";
        } else {
            TransactionByDate transactionByDate = transactionByDateDTO.toTransactionByDateEntity();
            TRANSACTION_BY_DATE_SERVICE.saveTransactionByDate(transactionByDate);
            response = "Transaction by date updated successfully";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteTransactionByDate(HttpExchange exchange, String transactionByDateId) throws IOException {
        int id = Integer.parseInt(transactionByDateId);
        TransactionByDate transactionByDate = TRANSACTION_BY_DATE_SERVICE.getTransactionByDateById(id);
        String response;
        if (transactionByDate == null) {
            response = "Transaction by date not found";
        } else {
            TRANSACTION_BY_DATE_SERVICE.deleteTransactionByDateById(id);
            response = "Transaction by date deleted successfully";
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
