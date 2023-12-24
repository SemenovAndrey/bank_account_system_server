package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.TransactionDTO;
import ru.mai.information_system.entity.Transaction;
import ru.mai.information_system.service.TransactionService;
import ru.mai.information_system.service.TransactionServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class TransactionsHandler implements HttpHandler {

    private final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/transactions";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactions(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionById(exchange, path.split("/")[2]);
        } else if (path.startsWith(localPath + "/bankAccountId") && path.split("/").length == 4
                && exchange.getRequestMethod().equals("GET")) {
            handleGetTransactionsByBankAccountId(exchange, path.split("/")[3]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddTransaction(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateTransaction(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteTransaction(exchange, path.split("/")[2]);
        } else {
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            exchange.close();
        }
    }

    private void handleGetTransactions(HttpExchange exchange) {
        List<Transaction> transactions = TRANSACTION_SERVICE.getAllTransactions();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionDTOList.add(transaction.toTransactionDTO());
        }

        String response = transactionDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetTransactionById(HttpExchange exchange, String transactionId) {
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

    private void handleGetTransactionsByBankAccountId(HttpExchange exchange, String requestBankAccountId) {
        int bankAccountId = Integer.parseInt(requestBankAccountId);
        List<Transaction> transactions = TRANSACTION_SERVICE.getTransactionsByBankAccountId(bankAccountId);
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDTOList.add(transaction.toTransactionDTO());
        }

        String response = transactionDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleAddTransaction(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            TransactionDTO transactionDTO = GSON.fromJson(requestBody, TransactionDTO.class);
            Transaction transaction = transactionDTO.toTransactionEntity();
            TRANSACTION_SERVICE.saveTransaction(transaction);
            response = "Transaction added successfully";
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateTransaction(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            TransactionDTO transactionDTO = GSON.fromJson(requestBody, TransactionDTO.class);
            Transaction transactionFromDB = TRANSACTION_SERVICE.getTransactionById(transactionDTO.getId());

            if (transactionFromDB == null) {
                response = "Transaction not found";
            } else {
                Transaction transaction = transactionDTO.toTransactionEntity();
                TRANSACTION_SERVICE.saveTransaction(transaction);
                response = "Transaction updated successfully";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteTransaction(HttpExchange exchange, String transactionId) {
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
}
