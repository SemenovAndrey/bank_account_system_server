package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.dto.BankAccountDTO;
import ru.mai.information_system.entity.BankAccount;
import ru.mai.information_system.service.BankAccountService;
import ru.mai.information_system.service.BankAccountServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class BankAccountsHandler implements HttpHandler {

    private final BankAccountService BANK_ACCOUNT_SERVICE = new BankAccountServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/bank_accounts";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccounts(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountById(exchange, path.split("/")[2]);
        } else if (path.startsWith(localPath + "/userId") && path.split("/").length == 4
                && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountByUserId(exchange, path.split("/")[3]);
        } else if (path.startsWith(localPath + "/userIdAndName") && path.split("/").length == 5
                && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountByUserIdAndName(exchange, path.split("/")[3], path.split("/")[4]);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("POST")) {
            handleAddBankAccount(exchange);
        } else if (path.equals(localPath) && exchange.getRequestMethod().equals("PUT")) {
            handleUpdateBankAccount(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("DELETE")) {
            handleDeleteBankAccount(exchange, path.split("/")[2]);
        } else {
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage() + "\n");
            }
            exchange.close();
        }
    }

    private void handleGetBankAccounts(HttpExchange exchange) {
        List<BankAccount> bankAccounts = BANK_ACCOUNT_SERVICE.getAllBankAccounts();
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<>();

        for (BankAccount bankAccount : bankAccounts) {
            bankAccountDTOList.add(bankAccount.toBankAccountDTO());
        }

        String response = bankAccountDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetBankAccountById(HttpExchange exchange, String bankAccountId) {
        int id = Integer.parseInt(bankAccountId);
        BankAccount bankAccount = BANK_ACCOUNT_SERVICE.getBankAccountById(id);

        String response;
        if (bankAccount != null) {
            BankAccountDTO bankAccountDTO = bankAccount.toBankAccountDTO();
            response = bankAccountDTO.toString();
        } else {
            response = "Bank account not found";
        }

        sendResponse(exchange, response);
    }

    private void handleGetBankAccountByUserId(HttpExchange exchange, String requestUserId) {
        int userId = Integer.parseInt(requestUserId);
        List<BankAccount> bankAccounts = BANK_ACCOUNT_SERVICE.getBankAccountsByUserId(userId);
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
        for (BankAccount bankAccount : bankAccounts) {
            bankAccountDTOList.add(bankAccount.toBankAccountDTO());
        }

        String response = bankAccountDTOList.toString();
        sendResponse(exchange, response);
    }

    private void handleGetBankAccountByUserIdAndName(HttpExchange exchange, String requestUserId,
                                                     String bankAccountName) {
        int userId = Integer.parseInt(requestUserId);
        BankAccount bankAccount = BANK_ACCOUNT_SERVICE.getBankAccountByUserIdAndName(userId, bankAccountName);

        String response;
        if (bankAccount != null) {
            BankAccountDTO bankAccountDTO = bankAccount.toBankAccountDTO();
            response = bankAccountDTO.toString();
        } else {
            response = "Bank account not found";
        }

        sendResponse(exchange, response);
    }

    private void handleAddBankAccount(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            BankAccountDTO bankAccountDTO = GSON.fromJson(requestBody, BankAccountDTO.class);
            BankAccount bankAccountFromDB = BANK_ACCOUNT_SERVICE
                    .getBankAccountByUserIdAndName(bankAccountDTO.getUserId(), bankAccountDTO.getName());

            if (bankAccountFromDB == null) {
                BankAccount bankAccount = bankAccountDTO.toBankAccountEntity();
                BANK_ACCOUNT_SERVICE.saveBankAccount(bankAccount);
                response = "Bank account added successfully";
            } else {
                response = "Bank account with this name already created";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n");
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleUpdateBankAccount(HttpExchange exchange) {
        String requestBody;
        String response;

        try {
            requestBody = new String(exchange.getRequestBody().readAllBytes());
            BankAccountDTO bankAccountDTO = GSON.fromJson(requestBody, BankAccountDTO.class);
            BankAccount bankAccountFromDB = BANK_ACCOUNT_SERVICE.getBankAccountById(bankAccountDTO.getId());

            if (bankAccountFromDB == null) {
                response = "Bank account not found";
            } else {
                BankAccount bankAccount = bankAccountDTO.toBankAccountEntity();
                BANK_ACCOUNT_SERVICE.saveBankAccount(bankAccount);
                response = "Bank account updated successfully";
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n");
            response = "Error";
        }

        sendResponse(exchange, response);
    }

    private void handleDeleteBankAccount(HttpExchange exchange, String bankAccountId) {
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
}
