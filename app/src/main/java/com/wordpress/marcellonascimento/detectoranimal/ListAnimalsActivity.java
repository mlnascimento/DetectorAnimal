package com.wordpress.marcellonascimento.detectoranimal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

import adapter.AnimalAdapter;
import model.Animal;
import dao.AnimalDAO;
import util.Mensagem;


public class ListAnimalsActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    private ListView lista;
    private List<Animal> animalList;
    private AnimalAdapter animalAdapter;
    private AnimalDAO animalDAO;



    private int idposicao;

    private AlertDialog alertDialog, alertConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_animals);

        alertDialog         = Mensagem.criarAlertDialog(this);
        alertConfirmacao    = Mensagem.criarDialogConfirmacao(this);


        animalDAO        = new AnimalDAO(this);
        animalList       = animalDAO.listarAnimals();
        animalAdapter    = new AnimalAdapter(this,animalList);

        // popular a listview

        lista = (ListView) findViewById(R.id.lvAnimals);
        lista.setAdapter(animalAdapter);

        lista.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_animals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_cadastro_animals) {
            startActivity(new Intent(this, CadAnimalActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int id = animalList.get(idposicao).get_id();

        switch (which){
            case 0:
                /**
                 * Gerar um numero randomico em face da nao implementacao do facedetector
                 */
                final Random numRandom = new Random();
                final int idRandom = numRandom.nextInt(20);
                Intent intent = new Intent(this, CadAnimalActivity.class);
                intent.putExtra("RANDOM_ID", id);
                //intent.putExtra("ANIMAL_ID", id);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, CadAnimalActivity.class);
                intent.putExtra("ANIMAL_ID", id);
                startActivity(intent);
                break;
            case 2:
                alertConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                animalList.remove(idposicao);
                animalDAO.removerAnimal(id);
                lista.invalidateViews();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                alertConfirmacao.dismiss();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        idposicao = position;
        alertDialog.show();
    }

}
