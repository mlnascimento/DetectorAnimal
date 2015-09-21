package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Animal;

/**
 * Created by marcelo on 10/09/15.
 */
public class AnimalDAO {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public AnimalDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getDatabase(){
        if (database == null){
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    private Animal criarAnimal(Cursor cursor){
        Animal model = new Animal(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Animals._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Animals.NOME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Animals.GENERO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Animals.ESPECIE)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Animals.NOME_POPULAR)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Animals.HABITAT)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Animals.FOTO))
                );
        return model;
    }

    public List<Animal> listarAnimals(){
        //String order = "nome DESC";
        Cursor cursor = getDatabase().query(DatabaseHelper.Animals.TABELA,
                DatabaseHelper.Animals.COLUNAS, null, null, null, null, "nome DESC");

        List<Animal> animals = new ArrayList<Animal>();
        while (cursor.moveToNext()){
            Animal model = criarAnimal(cursor);
            animals.add(model);
        }
        cursor.close();
        return animals;
    }

    public long salvarAnimal (Animal animal){
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Animals.NOME, animal.getNome());
        valores.put(DatabaseHelper.Animals.GENERO, animal.getGenero());
        valores.put(DatabaseHelper.Animals.ESPECIE, animal.getEspecie());
        valores.put(DatabaseHelper.Animals.NOME_POPULAR, animal.getNome_popular());
        valores.put(DatabaseHelper.Animals.HABITAT, animal.getHabitat());
        valores.put(DatabaseHelper.Animals.FOTO, animal.getFoto());

        if (animal.get_id() != null){
            return getDatabase().update(DatabaseHelper.Animals.TABELA, valores,
                    "_id = ?", new String [] {animal.get_id().toString()});
        }

        return getDatabase().insert(DatabaseHelper.Animals.TABELA, null, valores);
    }

    public boolean removerAnimal(int id){
        return getDatabase().delete(DatabaseHelper.Animals.TABELA,
                "_id = ?", new String[] {Integer.toString(id)}) > 0;
    }

    public Animal buscarAnimalPorId(int id){
        Cursor cursor = getDatabase().query(DatabaseHelper.Animals.TABELA,
                DatabaseHelper.Animals.COLUNAS,"_id = ?" , new String[]{Integer.toString(id)}, null, null,null);

        if (cursor.moveToNext()){
            Animal model = criarAnimal(cursor);
            cursor.close();
            return model;
        }

        return  null;
    }

    public void fechar(){
        databaseHelper.close();
        database = null;
    }
}
