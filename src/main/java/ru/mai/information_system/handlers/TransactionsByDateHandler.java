package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.TransactionByDateDTO;
import ru.mai.information_system.entity.TransactionByDate;
import ru.mai.information_system.service.TransactionByDateService;
import ru.mai.information_system.service.TransactionByDateServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class TransactionsByDateHandler implements HttpHandler {

    private final TransactionByDateService TRANSACTION_BY_DATE_SERVICE = new TransactionByDateServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
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
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            exchange.close();
        }
    }

    private void handleGetTransactionsByDate(HttpExchange exchange) {
        List<TransactionByDate> transactionsByDate = TRANSACTION_BY_DATE_SERVICE.getAllTransactionsByDate();
        List<TransactionByDateDTO> transactionByDateDTOList = new ArrayList<>();

        for (TransactionByDate transactionByDate : transactionsByDate) {
            transactionByDateDTOList.add(transactionByDate.toTransactionByDateDTO());
        }

        String response = transactionByDateDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionByDateById(HttpExchange exchange, String transactionByDateId) {
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

    private void handleAddTransactionByDate(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            TransactionByDateDTO transactionByDateDTO = GSON.fromJson(requestBody, TransactionByDateDTO.class);
            TransactionByDate transactionByDate = transactionByDateDTO.toTransactionByDateEntity();
            TRANSACTION_BY_DATE_SERVICE.saveTransactionByDate(transactionByDate);
            response = "Transaction by date added successfully";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateTransactionByDate(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            TransactionByDateDTO transactionByDateDTO = GSON.fromJson(requestBody, TransactionByDateDTO.class);
            TransactionByDate transactionByDateFromDB = TRANSACTION_BY_DATE_SERVICE
                    .getTransactionByDateById(transactionByDateDTO.getId());
            if (transactionByDateFromDB == null) {
                response = "Transaction by date not found";
            } else {
                TransactionByDate transactionByDate = transactionByDateDTO.toTransactionByDateEntity();
                TRANSACTION_BY_DATE_SERVICE.saveTransactionByDate(transactionByDate);
                response = "Transaction by date updated successfully";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteTransactionByDate(HttpExchange exchange, String transactionByDateId) {
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
}
