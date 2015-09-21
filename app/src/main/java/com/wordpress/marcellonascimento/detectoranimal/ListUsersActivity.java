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

import adapter.UserAdapter;
import dao.UserDAO;
import model.User;
import util.Mensagem;

public class ListUsersActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener, DialogInterface.OnClickListener{

    private ListView lista;
    private List<User> userList;
    private UserAdapter userAdapter;
    private UserDAO userDAO;

    private int idposicao;

    private AlertDialog alertDialog, alertConfirmacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);

        alertDialog         = Mensagem.criarAlertDialog(this);
        alertConfirmacao    = Mensagem.criarDialogConfirmacao(this);


        userDAO        = new UserDAO(this);
        userList       = userDAO.listarUsers();
        userAdapter    = new UserAdapter(this,userList);

        // popular a listview

        lista = (ListView) findViewById(R.id.lvUsers);
        lista.setAdapter(userAdapter);

        lista.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_users, menu);
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

        if (id == R.id.action_cadastro_usuarios) {
            startActivity(new Intent(this, CadUsuarioActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        int id = userList.get(idposicao).get_id();

        switch (which){
            case 0:
                Intent intent = new Intent(this, CadUsuarioActivity.class);
                intent.putExtra("USER_ID", id);
                startActivity(intent);
                break;
            case 1:
                alertConfirmacao.show();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                userList.remove(idposicao);
                userDAO.removerUser(id);
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
