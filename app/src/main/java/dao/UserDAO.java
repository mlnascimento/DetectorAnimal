package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * Created by marcelo on 10/09/15.
 */
public class UserDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public UserDAO(Context  context){
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    private User criarUsuario(Cursor cursor){
        User model = new User(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Users._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Users.NOME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Users.LOGIN)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Users.SENHA))
        );
        return model;
    }

    public List<User> listarUsers(){
        Cursor cursor = getDatabase().query(DatabaseHelper.Users.TABELA,
                DatabaseHelper.Users.COLUNAS, null, null, null, null, "nome ASC");

        List<User> users = new ArrayList<User>();
        while (cursor.moveToNext()){
            User model = criarUsuario(cursor);
            users.add(model);
        }
        cursor.close();
        return users;
    }

    public long salvarUser (User user){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Users.NOME, user.getNome());
        valores.put(DatabaseHelper.Users.LOGIN, user.getLogin());
        valores.put(DatabaseHelper.Users.SENHA, user.getSenha());

        if (user.get_id() != null){
            return getDatabase().update(DatabaseHelper.Users.TABELA, valores,
                    "_id = ?", new String [] {user.get_id().toString()});
        }

        return getDatabase().insert(DatabaseHelper.Users.TABELA, null, valores);
    }

    public boolean removerUser(int id){
        return getDatabase().delete(DatabaseHelper.Users.TABELA,
                "_id = ?", new String[] {Integer.toString(id)}) > 0;
    }

    public User buscarUserPorId(int id){
        Cursor cursor = getDatabase().query(DatabaseHelper.Users.TABELA,
                DatabaseHelper.Users.COLUNAS,"_id = ?" , new String[]{Integer.toString(id)}, null, null, null);

        if (cursor.moveToNext()){
            User model = criarUsuario(cursor);
            cursor.close();
            return model;
        }

        return  null;
    }

    public boolean logar(String usuario, String senha){
        Cursor cursor = getDatabase().query(DatabaseHelper.Users.TABELA,
                null, "LOGIN = ? AND SENHA = ?", new String[]{usuario, senha}, null, null, null);

                if (cursor.moveToFirst()){
                    return true;
                }
        return false;
    }

    public void fechar(){
        databaseHelper.close();
        database = null;
    }
}
