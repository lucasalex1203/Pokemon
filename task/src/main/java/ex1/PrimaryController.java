package ex1;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    @FXML
    private TextField nomeField;

    @FXML
    private TableColumn<Pokemon, String> idNome;

    @FXML
    private TableColumn<Pokemon, Integer> idNivel;

    @FXML
    private TableColumn<Pokemon, String> idTipo;

    @FXML
    private TextField tipoField;

    @FXML
    private TextField nivelField;

    @FXML
    private TableView<Pokemon> pokemon;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idNome.setCellValueFactory(new PropertyValueFactory<>("nomePokemon"));
        idTipo.setCellValueFactory(new PropertyValueFactory<>("tipoPokemon"));
        idNivel.setCellValueFactory(new PropertyValueFactory<>("nivelPokemon"));

        idNome.setCellFactory(TextFieldTableCell.forTableColumn());
        idTipo.setCellFactory(TextFieldTableCell.forTableColumn());
        idNivel.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        idNome.setOnEditCommit(event -> {
            Pokemon pokemon = event.getRowValue();
            pokemon.setNomePokemon(event.getNewValue());
            atualizarPokemon(pokemon);
        });

        idTipo.setOnEditCommit(event -> {
            Pokemon pokemon = event.getRowValue();
            pokemon.setTipoPokemon(event.getNewValue());
            atualizarPokemon(pokemon);
        });

        idNivel.setOnEditCommit(event -> {
            Pokemon pokemon = event.getRowValue();
            pokemon.setNivelPokemon(event.getNewValue());
            atualizarPokemon(pokemon);
        });

        pokemon.setEditable(true);

        
        carregarDados();
    }

    private void carregarDados() {
        ObservableList<Pokemon> pokemons = buscarDados();
        pokemon.setItems(pokemons);
    }

    private void atualizarPokemon(Pokemon pokemon) {
        pokemonDAO db = new pokemonDAO();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE pokemon SET nome = ?, tipo = ?, nivel = ? WHERE nome = ?"; //teste
            stmt = db.prepareStatement(sql);
            stmt.setString(1, pokemon.getNomePokemon());
            stmt.setString(2, pokemon.getTipoPokemon());
            stmt.setInt(3, pokemon.getNivelPokemon());
            stmt.setString(4, pokemon.getNomePokemon()); 

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                db.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deletarPokemon(Pokemon pokemon) {
        pokemonDAO db = new pokemonDAO();
        PreparedStatement stmt = null;
    
        try {
            String sql = "DELETE FROM pokemon WHERE nome = ?";
            stmt = db.prepareStatement(sql);
            stmt.setString(1, pokemon.getNomePokemon());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                db.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

        private ObservableList<Pokemon> buscarDados() {
        ObservableList<Pokemon> pokemons = FXCollections.observableArrayList();
        pokemonDAO db = new pokemonDAO();
        PreparedStatement stmt = null;
        ResultSet rs = null;
    
        try {
            String sql = "SELECT nome, tipo, nivel FROM pokemon";
            stmt = db.prepareStatement(sql);
            rs = stmt.executeQuery();
    
            while (rs.next()) {
                String nomePokemon = rs.getString("nome");
                String tipoPokemon = rs.getString("tipo");
                int nivelPokemon = rs.getInt("nivel");
                Pokemon pokemon = new Pokemon(nomePokemon, tipoPokemon, nivelPokemon);
                pokemons.add(pokemon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.disconnect();
        }
    
        return pokemons;
    }

    @FXML
    void handlePokemon(ActionEvent event) {

        String nomePokemon = nomeField.getText();
        String tipoPokemon = tipoField.getText();
        int nivelPokemon = Integer.parseInt(nivelField.getText());
    
        InsertSQL value = new InsertSQL();
        value.insertPokemon(nomePokemon, tipoPokemon, nivelPokemon);
    
    }

    @FXML
    void excluir(ActionEvent event) {
        Pokemon pokemonSelecionado = pokemon.getSelectionModel().getSelectedItem();
    
        if(pokemonSelecionado != null){
            pokemon.getItems().remove(pokemonSelecionado);
            deletarPokemon(pokemonSelecionado);

        } else{
            System.out.println("Nenhum Pokemon selecionado para deletar.");
        }
    }

    @FXML
    void update(ActionEvent event) {
        carregarDados();
    }

}
