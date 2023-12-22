package ru.mai.information_system.handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseSender {

    public static void sendResponse(HttpExchange exchange, String response) {
        try {
            exchange.sendResponseHeaders(200, response.length());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response.getBytes());
        } catch (Exception e) {
            System.err.println("Error");
            System.out.println(e.getMessage());
        }
    }

    public static void sendResponseForDTO(HttpExchange exchange, String response) {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        try {
            exchange.sendResponseHeaders(200, responseBytes.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        } catch (Exception e) {
            System.err.println("Error");
            System.out.println(e.getMessage());
        }
    }
}
