package ru.mai.information_system.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.mai.information_system.entity.BankAccountType;
import ru.mai.information_system.service.BankAccountTypeService;
import ru.mai.information_system.service.BankAccountTypeServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.mai.information_system.handlers.ResponseSender.sendResponse;

public class BankAccountTypesHandler implements HttpHandler {

    private final BankAccountTypeService BANK_ACCOUNT_TYPE_SERVICE = new BankAccountTypeServiceImpl();

    @Override
    public void handle(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String localPath = "/bank_account_types";

        if (path.equals(localPath) && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountTypes(exchange);
        } else if (path.startsWith(localPath + "/") && path.split("/").length == 3
                && exchange.getRequestMethod().equals("GET")) {
            handleGetBankAccountTypeById(exchange, path.split("/")[2]);
        } else {
            try {
                exchange.sendResponseHeaders(404, 0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            exchange.close();
        }
    }

    private void handleGetBankAccountTypes(HttpExchange exchange) {
        List<BankAccountType> bankAccountTypes = BANK_ACCOUNT_TYPE_SERVICE.getAllBankAccountTypes();
        String response = bankAccountTypes.toString();
        sendResponse(exchange, response);
    }

    private void handleGetBankAccountTypeById(HttpExchange exchange, String bankAccountTypeId) {
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
}
