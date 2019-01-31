package tp2.mp.dj.tp2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Classe permettant l'affichage d'un ImageView dans un GridView
 */
public class CarteAdapter extends BaseAdapter {

    private List<ImageView> images;

    public CarteAdapter(Context c, List<ImageView> liste) {
        images = liste;
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return images.get(position);
    }
}
