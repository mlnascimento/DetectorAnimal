package com.wordpress.marcellonascimento.detectoranimal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import dao.AnimalDAO;
import model.Animal;
import util.Mensagem;


public class ScannerActivity extends AppCompatActivity {

    public String caminhoImg;
    private AnimalDAO animalDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scanner);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        startActivityForResult(intent, 0);

    }


    protected void onDestroy(){
        animalDAO.fechar();
        setTitle(getString(R.string.title_activity_scanner));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult (int resquestCode, int resultCode, Intent intent){
        super.onActivityResult(resquestCode, resultCode, intent);

        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Bitmap img = (Bitmap) bundle.get("data");

                ImageView iv = (ImageView) findViewById(R.id.imgFoto);

                iv.setImageBitmap(img);
                String nomeFoto = "Foto_" + System.currentTimeMillis() + ".jpg";

                File caminhoFoto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), nomeFoto);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoFoto));

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    public void buscarAnimal(View view) {

        /**
         * Como não foi implementado o faceDetector, fazer uma busca aleatoria
         */

//        final Random numRandom = new Random();
//        final int idRandom = numRandom.nextInt(25);

      //  Intent intent = new Intent(this, CadAnimalActivity.class);
      //  intent.putExtra("RANDOM_ID", idRandom);
     //   startActivity(intent);
        animalDAO = new AnimalDAO(this);

        final Random numRandom = new Random();
        final int idRandom = numRandom.nextInt(20);
        setTitle(getString(R.string.vizualizarAnimal));
        Animal model = animalDAO.buscarAnimalPorId(idRandom);

        if (model == null){
            ///Mensagem.Msg(this, getString(R.string.msg_animalNaoexiste));
            EditText error = (EditText) findViewById(R.id.errorBuscaAnimal);
            error.setError("Animal não localizado");
            setTitle(getString(R.string.title_activity_scanner));
        } else {
              Intent intent = new Intent(this, CadAnimalActivity.class);
              intent.putExtra("RANDOM_ID", idRandom);
              startActivity(intent);
        }

    }
}
