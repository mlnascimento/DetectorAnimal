package model;

/**
 * Created by marcelo on 10/09/15.
 */
public class Animal {
    private Integer _id;
    private String nome;
    private String genero;
    private String especie;
    private String nome_popular;
    private String habitat;

    public Animal(){}

    public Animal(Integer id, String nome, String genero, String especie, String nome_popular, String habitat){
        this._id = id;
        this.nome = nome;
        this.genero = genero;
        this.especie = especie;
        this.nome_popular = nome_popular;
        this.habitat = habitat;

    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNome_popular() {
        return nome_popular;
    }

    public void setNome_popular(String nome_popular) {
        this.nome_popular = nome_popular;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
}
