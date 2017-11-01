package newsongs.fr.newsongs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import newsongs.fr.newsongs.Models.Musique;
import newsongs.fr.newsongs.R;

/**
 * Created by daimajia on 14-5-29.
 */
public class TransformerAdapter extends BaseAdapter{
    private Context mContext;
    private List<Musique> playlists;

    public TransformerAdapter(Context context, List<Musique> list) {
        mContext = context;
        this.playlists = list;
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Musique getItem(int position) {
        return playlists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return playlists.get(position).getIdmusique();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t = (TextView)LayoutInflater.from(mContext).inflate(R.layout.item_slider,null);
        t.setText(getItem(position).getTitre());
        return t;
    }
}
