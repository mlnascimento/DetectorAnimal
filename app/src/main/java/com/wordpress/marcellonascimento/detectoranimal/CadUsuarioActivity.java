package com.wordpress.marcellonascimento.detectoranimal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import dao.UserDAO;
import model.User;
import util.Mensagem;

public class CadUsuarioActivity extends AppCompatActivity {

    private EditText edtNome, edtLogin, edtSenha;
    private UserDAO userDAO;
    private User user;
    private int iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        userDAO = new UserDAO(this);


        edtNome  = (EditText) findViewById(R.id.usuario_edtNome);
        edtLogin = (EditText) findViewById(R.id.usuario_edtLogin);
        edtSenha = (EditText) findViewById(R.id.usuario_edtSenha);

        // recupera os dados do usuario (edição)
        iduser = getIntent().getIntExtra("USER_ID", 0);

        if (iduser > 0){
            User model = userDAO.buscarUserPorId(iduser);

            edtNome.setText(model.getNome());
            edtLogin.setText(model.getLogin());
            edtSenha.setText(model.getSenha());
            setTitle(getString(R.string.AtualizarUsuario));
        }
    }

    @Override
    protected void onDestroy(){
        userDAO.fechar();
        super.onDestroy();
    }

    private void cadastrar(){
        boolean validar = true;

        String nome  = edtNome.getText().toString();
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        if (nome == null || nome.equals("")){
            validar = false;
            edtNome.setError(getString(R.string.campo_notEmpty));
        }

        if (login == null || login.equals("")){
            validar = false;
            edtLogin.setError(getString(R.string.campo_notEmpty));
        }

        if (senha == null || senha.equals("")){
            validar = false;
            edtSenha.setError(getString(R.string.campo_notEmpty));
        }

        if (validar){
            user = new User();
            user.setNome(nome);
            user.setLogin(login);
            user.setSenha(senha);

            //Atualização
            if(iduser > 0){
                user.set_id(iduser);
            }

            long result = userDAO.salvarUser(user);

            if(result != -1){
                if (iduser > 0){
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

        if(iduser > 0){
            menu.findItem(R.id.action_menu_excluir).setVisible(true);
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
        //if (id == R.id.action_settings) {
        //    return true;
       // }

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
