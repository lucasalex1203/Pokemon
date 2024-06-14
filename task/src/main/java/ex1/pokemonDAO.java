package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class pokemonDAO {

    private Connection conn;


    private static final String URL = "jdbc:postgresql://localhost:5432/pokemon";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public PreparedStatement executeNotReturn(String sql) throws SQLException {
        if (conn == null) {
            getConnection();
        }
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        stmt.close();
        return stmt;
    }

        public ResultSet executeWithReturn(String comando) {

        ResultSet resultSet = null;

        try {
            PreparedStatement stm = this.conn.prepareStatement(comando);


            resultSet = stm.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;

    }
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (conn == null || conn.isClosed()) {
            getConnection();
        }
        return conn.prepareStatement(sql);
    }

    public static void main(String[] args) {
        pokemonDAO poketest=new pokemonDAO();
        poketest.getConnection();
        System.out.println("Êxito");


        
    }
}
