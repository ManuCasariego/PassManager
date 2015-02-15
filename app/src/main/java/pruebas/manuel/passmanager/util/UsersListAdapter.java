package pruebas.manuel.passmanager.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import pruebas.manuel.passmanager.R;

public class UsersListAdapter extends ArrayAdapter<Usuario> {

    private Activity ctx;

    public UsersListAdapter(Activity context, List<Usuario> usuarios) {
        super(context, R.layout.list_item_personalizado, usuarios);
        this.ctx = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = ctx.getLayoutInflater().inflate(R.layout.list_item_personalizado, parent, false);
        }
        Usuario actual = this.getItem(position);
        inicializarComponentes(view, actual);
        return view;
    }

    private void inicializarComponentes(View view, Usuario actual) {
        //Title
        TextView textView = (TextView) view.findViewById(R.id.textViewServicio);
        String service = actual.getService();
        textView.setText(service);

        //categories
        textView = (TextView) view.findViewById(R.id.textViewNombreUsuario);
        textView.setText(actual.getUserName());


        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewCirculoLetra);
        ColorGenerator generator = ColorGenerator.MATERIAL;

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .toUpperCase()
                .endConfig()
                .buildRound(service.substring(0, 1), generator.getColor(service));
        imageView.setImageDrawable(drawable);


    }
}
