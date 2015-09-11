package model;

/**
 * Created by marcelo on 10/09/15.
 */
public class User {
    private Integer _id;
    private String nome;
    private String login;
    private String senha;


    public User(){};

    public User(Integer id, String nome, String login, String senha){
        this._id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}