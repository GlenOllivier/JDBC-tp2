package tpjava.dao;

import tpjava.dao.dal.PersistenceManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) {
        try {
            Connection connection = PersistenceManager.getConnection();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM t_contact_con")) {

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("con_email"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Attention :" + e.getMessage());
        }

        try {
            PersistenceManager.closeConnection();
        } catch (SQLException e) {
            System.out.println("Attention :" + e.getMessage());
        }
    }
}