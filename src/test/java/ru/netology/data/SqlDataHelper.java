package ru.netology.data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlDataHelper {
    @Value
    public static class DataBaseConn {
        String url;
        String user;
        String password;
    }

    public static DataBaseConn getDataBaseConn() {

        return new DataBaseConn(System.getProperty("url"), System.getProperty("user"), System.getProperty("password"));

    }

    public static String getStatusLastTransaction() {
        String status = null;
        try {
            val codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val statusTransaction = runner.query(conn, codeSQL, new ScalarHandler<>());
            status = (String) statusTransaction;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String getStatusLastCreditTransaction() {
        String status = null;
        try {
            val codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val statusTransaction = runner.query(conn, codeSQL, new ScalarHandler<>());
            status = (String) statusTransaction;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int getPriceLastTransaction() {
        int price = 0;
        try {
            val codeSQL = "SELECT amount FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val amount = runner.query(conn, codeSQL, new ScalarHandler<>());
            price = (int) amount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public static String getPaymentId() {
        String id = null;
        try {
            val codeSQL = "SELECT payment_id FROM order_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val paymentId = runner.query(conn, codeSQL, new ScalarHandler<>());
            id = (String) paymentId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getTransactionId() {
        String id = null;
        try {
            val codeSQL = "SELECT transaction_id FROM payment_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val transactionId = runner.query(conn, codeSQL, new ScalarHandler<>());
            id = (String) transactionId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getCreditId() {
        String id = null;
        try {
            val codeSQL = "SELECT credit_id FROM order_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val idCredit = runner.query(conn, codeSQL, new ScalarHandler<>());
            id = (String) idCredit;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static String getIdInCreditTable() {
        String id = null;
        try {
            val codeSQL = "SELECT id FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
            val runner = new QueryRunner();
            val conn = DriverManager
                    .getConnection(getDataBaseConn().url, getDataBaseConn().user, getDataBaseConn().password);
            val idCredit = runner.query(conn, codeSQL, new ScalarHandler<>());
            id = (String) idCredit;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
