package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.BankAccountDTO;
import ru.mai.information_system.entity.BankAccount;
import ru.mai.information_system.service.BankAccountService;
import ru.mai.information_system.service.BankAccountServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BankAccountsHandler implements HttpHandler {

    private final BankAccountService BANK_ACCOUNT_SERVICE = new BankAccountServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/bank_accounts";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccounts(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountById(exchange, path.split("/")[2]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddBankAccount(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateBankAccount(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteBankAccount(exchange, path.split("/")[2]);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetBankAccounts(HttpExchange exchange) throws IOException {
        List<BankAccount> bankAccounts = BANK_ACCOUNT_SERVICE.getAllBankAccounts();
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<>();

        for (BankAccount bankAccount : bankAccounts) {
            bankAccountDTOList.add(bankAccount.toBankAccountDTO());
        }

        String response = bankAccountDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetBankAccountById(HttpExchange exchange, String bankAccountId) throws IOException {
        int id = Integer.parseInt(bankAccountId);
        BankAccount bankAccount = BANK_ACCOUNT_SERVICE.getBankAccountById(id);

        String response;
        if (bankAccount != null) {
            response = bankAccount.toString();
        } else {
            response = "Bank account not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddBankAccount(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        BankAccountDTO bankAccountDTO = GSON.fromJson(requestBody, BankAccountDTO.class);
        BankAccount bankAccountFromDB = BANK_ACCOUNT_SERVICE
                .getBankAccountByNameAndUserId(bankAccountDTO.getUserId(), bankAccountDTO.getName());

        String response;
        if (bankAccountFromDB == null) {
            BankAccount bankAccount = bankAccountDTO.toBankAccountEntity();
            BANK_ACCOUNT_SERVICE.saveBankAccount(bankAccount);
            response = "Bank account added successfully";
        } else {
            response = "Bank account with this name already created";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateBankAccount(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        BankAccountDTO bankAccountDTO = GSON.fromJson(requestBody, BankAccountDTO.class);
        BankAccount bankAccountFromDB = BANK_ACCOUNT_SERVICE.getBankAccountById(bankAccountDTO.getId());

        String response;
        if (bankAccountFromDB == null) {
            response = "Bank account not found";
        } else {
            BankAccount bankAccount = bankAccountDTO.toBankAccountEntity();
            BANK_ACCOUNT_SERVICE.saveBankAccount(bankAccount);
            response = "Bank account updated successfully";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteBankAccount(HttpExchange exchange, String bankAccountId) throws IOException {
        int id = Integer.parseInt(bankAccountId);
        BankAccount bankAccount = BANK_ACCOUNT_SERVICE.getBankAccountById(id);

        String response;
        if (bankAccount == null) {
            response = "Bank account not found";
        } else {
            BANK_ACCOUNT_SERVICE.deleteBankAccountById(id);
            response = "Bank account deleted successfully";
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
