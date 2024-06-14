package ex1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertSQL {

    public void insertPokemon (String nome, String tipo, int nivel) {
        pokemonDAO db = new pokemonDAO();
        Connection conn = db.getConnection();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO pokemon (nome, tipo, nivel) VALUES (?, ?, ?)";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            stmt.setInt(3, nivel);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            db.disconnect();
        }
    }
}