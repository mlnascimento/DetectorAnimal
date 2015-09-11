package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marcelo on 10/09/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "bd_animais";
    private static final int VERSAO = 1;


    public DatabaseHelper(Context context){
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabela de Usuários
        db.execSQL("create table users (_id integer primary key autoincrement, "
                    + "nome text not null, login text not null, senha text not null)");

        // Tabela de Animais
        db.execSQL("create table animals (_id integer primary key autoincrement, "
                    + "nome text not null, genero text not null, especie text not null, "
                    + "nome_popular text not null, habitat text not null");

        //Cadastro de Usuário Inicial
        db.execSQL("insert into users (nome, login, senha) values ('Administrador', 'admin', '123456')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Users{
        public static final String TABELA = "users";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String LOGIN = "login";
        public static final String SENHA = "senha";

        public static final String[] COLUNAS = new String[]{
                _ID, NOME, LOGIN, SENHA
        };
    }

    public static class Animals{
        public static final String TABELA = "animals";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String GENERO = "genero";
        public static final String ESPECIE = "especie";
        public static final String NOME_POPULAR = "nome_popular";
        public static final String HABITAT = "habitat";

        public static final String[] COLUNAS = new String[]{
                _ID, NOME, GENERO, ESPECIE, NOME_POPULAR, HABITAT
        };
    }
}
