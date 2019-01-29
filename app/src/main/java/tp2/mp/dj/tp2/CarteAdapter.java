package tp2.mp.dj.tp2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class CarteAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImageView> images;

    public CarteAdapter(Context c, List<ImageView> liste) {
        mContext = c;
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
        /*ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }*/

        //imageView.setImageResource(images.get(position));
        return images.get(position);
    }
}
