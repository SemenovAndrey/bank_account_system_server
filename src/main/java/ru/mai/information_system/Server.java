package ru.mai.information_system;

import com.sun.net.httpserver.HttpServer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.mai.information_system.entity.*;
import ru.mai.information_system.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    private HttpServer server;
    private static SessionFactory sessionFactory;

    public Server() {
        try {
            this.server = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException exception) {
            System.out.println("Error in creating HttpServer");
            exception.printStackTrace();
        }

        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(BankAccount.class)
                    .addAnnotatedClass(BankAccountType.class)
                    .addAnnotatedClass(Support.class)
                    .addAnnotatedClass(Transaction.class)
                    .addAnnotatedClass(TransactionByDate.class)
                    .addAnnotatedClass(TransactionCategory.class)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Exception exception) {
            System.out.println("Error in session factory configuration");
            System.out.println(exception.getMessage());
        }
    }

    public void start() {
        try {
            server.createContext("/users", new UsersHandler());
            server.createContext("/bank_accounts", new BankAccountsHandler());
            server.createContext("/bank_account_types", new BankAccountTypesHandler());
            server.createContext("/transactions", new TransactionsHandler());
            server.createContext("/transactions_by_date", new TransactionsByDateHandler());
            server.createContext("/transaction_categories", new TransactionCategoriesHandler());
            server.createContext("/support", new SupportHandler());

            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
        } catch (Exception exception) {
            System.out.println("Error in starting server");
            System.out.println(exception.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
