package com.wordpress.marcellonascimento.detectoranimal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;


import dao.AnimalDAO;
import model.Animal;
import util.Mensagem;

public class CadAnimalActivity extends AppCompatActivity {

    private EditText edtNome, edtGenero, edtEspecie, edtNomepopular, edtHabitat, edtFoto;
    private ImageView imgFoto;
    private AnimalDAO animalDAO;
    private Animal animal;
    private int idanimal, idrandom;
    ImageView img;
    private Bitmap bitmap;
    public String caminhoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_animal);

        animalDAO = new AnimalDAO(this);


        edtNome              = (EditText) findViewById(R.id.animal_edtNome);
        edtGenero            = (EditText) findViewById(R.id.animal_edtGenero);
        edtEspecie           = (EditText) findViewById(R.id.animal_edtEspecie);
        edtNomepopular       = (EditText) findViewById(R.id.animal_edtNomepopular);
        edtHabitat           = (EditText) findViewById(R.id.animal_edtHabitat);
        imgFoto              = (ImageView) findViewById(R.id.animal_imgFoto);
        edtFoto              = (EditText) findViewById(R.id.animal_edtFoto);

        // recupera os dados do usuario (edição)
        idanimal = getIntent().getIntExtra("ANIMAL_ID", 0);


        // Recupera o numero randomico
        idrandom = getIntent().getIntExtra("RANDOM_ID", 0);

        if (idanimal > 0){
            Animal model = animalDAO.buscarAnimalPorId(idanimal);

            edtNome.setText(model.getNome());
            edtGenero.setText(model.getGenero());
            edtEspecie.setText(model.getEspecie());
            edtNomepopular.setText(model.getNome_popular());
            edtHabitat.setText(model.getHabitat());
            edtFoto.setText(model.getFoto());

            String caminho = model.getFoto();

            Bitmap bitmap = BitmapFactory.decodeFile(caminho);
            ImageView iv = (ImageView) findViewById(R.id.animal_imgFoto);

            iv.setImageBitmap(bitmap);

            setTitle(getString(R.string.AtualizarAnimal));
        } else {

            Animal model = animalDAO.buscarAnimalPorId(idrandom);

            if (model == null){
               // Mensagem.Msg(this, getString(R.string.msg_animalNaoexiste));
            } else {
                setTitle(getString(R.string.vizualizarAnimal));

                // desabilitar os botões
                Button btn = (Button) findViewById(R.id.btnBuscarFoto);
                btn.setEnabled(false);
                edtNome.setEnabled(false);
                edtGenero.setEnabled(false);
                edtEspecie.setEnabled(false);
                edtNomepopular.setEnabled(false);
                edtHabitat.setEnabled(false);
                edtFoto.setEnabled(false);


                edtNome.setText(model.getNome());
                edtGenero.setText(model.getGenero());
                edtEspecie.setText(model.getEspecie());
                edtNomepopular.setText(model.getNome_popular());
                edtHabitat.setText(model.getHabitat());
                edtFoto.setText(model.getFoto());

                String caminho = model.getFoto();

                Bitmap bitmap = BitmapFactory.decodeFile(caminho);
                ImageView iv = (ImageView) findViewById(R.id.animal_imgFoto);

                iv.setImageBitmap(bitmap);
            }

        }


    }

    protected void onDestroy(){
        animalDAO.fechar();
        super.onDestroy();
    }

    public void tirarFoto(View view){


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        startActivityForResult(intent, 0);
    }

    public void buscarFoto(View view){
        Intent data = new Intent(Intent.ACTION_GET_CONTENT);
        data.setType("image/");
        startActivityForResult(data, 101);
    }

    @Override
    protected void onActivityResult (int resquestCode, int resultCode, Intent intent){
        super.onActivityResult(resquestCode, resultCode, intent);

        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Bitmap img = (Bitmap) bundle.get("data");

                ImageView iv = (ImageView) findViewById(R.id.animal_imgFoto);

                iv.setImageBitmap(img);
                String nomeFoto = System.currentTimeMillis() + ".jpg";

                //File caminhoFoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto);
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));
              /*  FileOutputStream  fos = null;
                try {
                    fos = openFileOutput(nomeFoto, Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                caminhoImg = nomeFoto;
            }
        }

        if (resultCode == RESULT_OK){

            Uri imagemSelecionada =  intent.getData();


            if (resquestCode == 101){

                String[] colunas = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                cursor.moveToFirst();

                int indexColuna = cursor.getColumnIndex(colunas[0]);
                String pathImg = cursor.getString(indexColuna);
                caminhoImg = pathImg;
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
                ImageView iv = (ImageView) findViewById(R.id.animal_imgFoto);

                iv.setImageBitmap(bitmap);

            }
        }
    }


    private void cadastrar(){
        boolean validar = true;

        String nome         = edtNome.getText().toString();
        String genero       = edtGenero.getText().toString();
        String especie      = edtEspecie.getText().toString();
        String nomepopular  = edtNomepopular.getText().toString();
        String habitat      = edtHabitat.getText().toString();



        if (nome == null || nome.equals("")){
            validar = false;
            edtNome.setError(getString(R.string.campo_notEmpty));
        }

        if (genero == null || genero.equals("")){
            validar = false;
            edtGenero.setError(getString(R.string.campo_notEmpty));
        }

        if (especie == null || especie.equals("")){
            validar = false;
            edtEspecie.setError(getString(R.string.campo_notEmpty));
        }

        if (nomepopular == null || nomepopular.equals("")){
            validar = false;
            edtNomepopular.setError(getString(R.string.campo_notEmpty));
        }

        if (habitat == null || habitat.equals("")){
            validar = false;
            edtHabitat.setError(getString(R.string.campo_notEmpty));
        }


        if (validar){
            animal = new Animal();
            animal.setNome(nome);
            animal.setGenero(genero);
            animal.setEspecie(especie);
            animal.setNome_popular(nomepopular);
            animal.setHabitat(habitat);
            animal.setFoto(caminhoImg);

            //Atualização
            if(idanimal > 0){
                animal.set_id(idanimal);
            }

            long result = animalDAO.salvarAnimal(animal);

            if(result != -1){
                if (idanimal > 0){
                    Mensagem.Msg(this, getString(R.string.msg_atualizar));
                } else {
                    Mensagem.Msg(this, getString(R.string.msg_sucesso));
                }
                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Mensagem.Msg(this, getString(R.string.msg_error));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cadastros, menu);

        if(idanimal > 0){
           // menu.findItem(R.id.action_menu_excluir).setVisible(true);
            menu.findItem(R.id.action_menu_salvar).setVisible(true);
            menu.findItem(R.id.action_menu_sair).setVisible(true);
        }else {
            if (idrandom == 0 ) {
                menu.findItem(R.id.action_menu_salvar).setVisible(true);
            } else {
                menu.findItem(R.id.action_menu_salvar).setVisible(false);
                menu.findItem(R.id.action_menu_sair).setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_menu_salvar:
                this.cadastrar();
                break;
            case R.id.action_menu_sair:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
