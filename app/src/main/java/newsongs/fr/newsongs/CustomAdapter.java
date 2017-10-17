package newsongs.fr.newsongs;
        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.List;

        import newsongs.fr.newsongs.Models.Utilisateur;

public class CustomAdapter extends BaseAdapter{

    List<Utilisateur> result;
    Context context;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Activity mainActivity, List<Utilisateur> prgmFriendsList) {

        // TODO Auto-generated constructor stub
        result=prgmFriendsList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Utilisateur getItem(int position) {
        // TODO Auto-generated method stub
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        int id;
        TextView tv;
        ImageButton img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageButton) rowView.findViewById(R.id.imageButton1);
        holder.tv.setText(result.get(position).getPseudo());
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result.get(position).getPseudo(), Toast.LENGTH_LONG).show();
            }
        });

        holder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked on button" + getItem(position).getPseudo(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

}