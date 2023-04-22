package org.example;

import java.sql.*;
import java.util.List;

public class MySqlConnection {
    private String username;
    private String password;
    private String host;
    private String port;
    private String db;
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public MySqlConnection() throws SQLException {
        username = "root";
        password ="";
        host = "localhost";
        port="3306";
        db = "thread_demo";
        openConnection();
    }
    public MySqlConnection(String username, String password, String host, String port, String db) throws SQLException {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.db = db;
        openConnection();

    }
    private Connection openConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        final String connectionString = "jdbc:mysql://"+host+":"+port+"/"+db+"?" +
                "user="+username+"&password="+password;
        this.connection = DriverManager.getConnection( connectionString);
        return connection;

    }
    public void saveBatch(List<Order> orderList){
        String sql = " INSERT INTO orders(product_name, qty) VALUES(? , ? ) ";
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            for(Order order:orderList){
                statement.setString(1, order.getName());
                statement.setInt(2, order.getQty());
                statement.executeUpdate();
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void save(Order order){
        String sql = " INSERT INTO orders(product_name, qty) VALUES(? , ? ) ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, order.getName());
            statement.setInt(2, order.getQty());
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
