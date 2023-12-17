package ru.mai.information_system.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.entity.BankAccountType;
import ru.mai.information_system.service.BankAccountTypeService;
import ru.mai.information_system.service.BankAccountTypeServiceImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BankAccountTypesHandler implements HttpHandler {

    private final BankAccountTypeService BANK_ACCOUNT_TYPE_SERVICE = new BankAccountTypeServiceImpl();
    private final Gson GSON = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/bank_account_types";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountTypes(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountTypeById(exchange, path.split("/")[2]);
        } else {
            exchange.sendResponseHeaders(404, 0);
            exchange.close();
        }
    }

    private void handleGetBankAccountTypes(HttpExchange exchange) throws  IOException {
        List<BankAccountType> bankAccountTypes = BANK_ACCOUNT_TYPE_SERVICE.getAllBankAccountTypes();
        String response = bankAccountTypes.toString();
        sendResponse(exchange, response);
    }

    private void handleGetBankAccountTypeById(HttpExchange exchange, String bankAccountTypeId) throws IOException {
        int id = Integer.parseInt(bankAccountTypeId);
        BankAccountType bankAccountType = BANK_ACCOUNT_TYPE_SERVICE.getBankAccountTypeById(id);
        String response;
        if (bankAccountType != null) {
            response = bankAccountType.toString();
        } else {
            response = "Bank account type not found";
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
