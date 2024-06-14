package ex1;

public class Pokemon {
    public Pokemon(String nomePokemon, String tipoPokemon, int nivelPokemon) {
        this.nomePokemon = nomePokemon;
        this.tipoPokemon = tipoPokemon;
        this.nivelPokemon = nivelPokemon;
    }

    private String nomePokemon;
    private String tipoPokemon;
    private int nivelPokemon;
    
    public String getNomePokemon() {
        return nomePokemon;
    }

    public void setNomePokemon(String nomePokemon) {
        this.nomePokemon = nomePokemon;
    }

    public String getTipoPokemon() {
        return tipoPokemon;
    }

    public void setTipoPokemon(String tipoPokemon) {
        this.tipoPokemon = tipoPokemon;
    }

    public int getNivelPokemon() {
        return nivelPokemon;
    }

    public void setNivelPokemon(int nivelPokemon) {
        this.nivelPokemon = nivelPokemon;
    }

}
