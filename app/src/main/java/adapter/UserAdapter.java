package adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wordpress.marcellonascimento.detectoranimal.R;

import java.util.List;

import model.User;

/**
 * Created by marcelo on 11/09/15.
 */
public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<User> lista;

    public UserAdapter (Context ctx, List<User> users){
        this.context = ctx;
        this.lista   = users;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        User user = lista.get(position);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.users, null);
        }
        TextView txtNome = (TextView) view.findViewById(R.id.usuario_lista_nome);
        txtNome.setText(user.getNome());

        return view;
    }
}
