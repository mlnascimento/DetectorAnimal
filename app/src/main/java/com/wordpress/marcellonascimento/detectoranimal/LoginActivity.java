package com.wordpress.marcellonascimento.detectoranimal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import dao.UserDAO;
import util.Mensagem;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsuario, edtSenha;
    private UserDAO helper;
    private CheckBox ckbConectado;

    private static final String MCONECTADO = "mconectado";
    private static final String PREFERENCE_NAME = "LoginActivityPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuario   = (EditText) findViewById(R.id.login_edtUsuario);
        edtSenha     = (EditText) findViewById(R.id.login_edtSenha);
        ckbConectado = (CheckBox) findViewById(R.id.login_ckbConectdo);


        helper = new UserDAO(this);

        //recupera a informação gravada
        SharedPreferences preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        boolean conectado             = preferences.getBoolean(MCONECTADO, false);

        if (conectado){
            ChamarMainActivity();
        }

    }

    public void logar(View view){
        String user = edtUsuario.getText().toString();
        String senha = edtSenha.getText().toString();

        boolean validar = true;

        // Verifica se foi informado um usuario
        if(user == null || user.equals("")){

            validar = false;
            edtUsuario.setError(getString(R.string.login_valUser));

        }

        // verifica se foi informado a senha
        if(senha == null || senha.equals("")){

            validar = false;
            edtSenha.setError(getString(R.string.login_valSenha));

        }

        // usuario e senha informados
        if (validar) {
            if (helper.logar(user,senha)) {

                // verifica se o checkbox manter conectado está marcado
                if (ckbConectado.isChecked()){
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean(MCONECTADO, true);
                    editor.commit();
                }
                ChamarMainActivity();

            } else {
                Mensagem.Msg(this, getString(R.string.login_msgincorreto));
            }

        }

    }

    private void ChamarMainActivity(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
