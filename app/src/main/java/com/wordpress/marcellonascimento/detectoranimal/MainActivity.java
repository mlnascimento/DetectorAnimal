package com.wordpress.marcellonascimento.detectoranimal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.FaceDetector;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import util.Mensagem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_cadastro_usuarios) {
       //     startActivity(new Intent(this, CadUsuarioActivity.class));
       // }
        switch (item.getItemId()){
            case R.id.action_lista_usuarios:
                startActivity(new Intent(this, ListUsersActivity.class));
                break;
            case R.id.action_mostrar_todos:
                startActivity(new Intent(this, ListAnimalsActivity.class));
                break;
            case R.id.action_scanner:
                //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(new Intent(this, ListAnimalsActivity.class));
               // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               // startActivityForResult(intent, 0);
                startActivity(new Intent(this, ScannerActivity.class));
                break;
            case R.id.action_lista_animals:
                startActivity(new Intent(this, ListAnimalsActivity.class));
                break;
            case R.id.action_lista_sair:
                Mensagem.MsgConfirm(this, "Sair", "Deseja realmente sair?", R.mipmap.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                break;
            case R.id.action_lista_logout:
                SharedPreferences preferences = getSharedPreferences("LoginActivityPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
