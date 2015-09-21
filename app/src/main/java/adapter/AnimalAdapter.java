package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.marcellonascimento.detectoranimal.R;

import java.util.List;

import model.Animal;

/**
 * Created by marcelo on 16/09/15.
 */
public class AnimalAdapter  extends BaseAdapter{
    private Context context;
    private List<Animal> lista;

    public AnimalAdapter (Context ctx, List<Animal> animals){
        this.context = ctx;
        this.lista   = animals;
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
        Animal animal = lista.get(position);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.animals, null);
        }
        TextView txtNome = (TextView) view.findViewById(R.id.animal_lista_nome);
        txtNome.setText(animal.getNome());

        ImageView imgFoto = (ImageView) view.findViewById(R.id.animal_imgFoto);

        String caminho = animal.getFoto();

        Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        ImageView iv = (ImageView) view.findViewById(R.id.animal_imgFoto);

        iv.setImageBitmap(bitmap);

        return view;
    }

}
