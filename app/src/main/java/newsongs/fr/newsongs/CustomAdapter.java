package newsongs.fr.newsongs;
        import android.app.Activity;
        import android.content.Context;
        import android.content.SharedPreferences;
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

        import newsongs.fr.newsongs.API.ServiceGenerator;
        import newsongs.fr.newsongs.API.UtilisateurClient;
        import newsongs.fr.newsongs.Models.Reponse;
        import newsongs.fr.newsongs.Models.Utilisateur;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

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

        //les actions faisable pour chaque amis

        //voir en detail amis
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + result.get(position).getPseudo(), Toast.LENGTH_LONG).show();
            }
        });

        //supprimer amis
        holder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked on button" + getItem(position).getIdutilisateur(), Toast.LENGTH_LONG).show();

                SharedPreferences settings = context.getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                int idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur
                int friendToDelete = getItem(position).getIdutilisateur();

                UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);

                Call<Reponse> call = service.deleteFriend(idutilisateur, friendToDelete);

                call.enqueue(new Callback<Reponse>() {

                    @Override
                    public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                        if(response.code() == 200)
                            Toast.makeText(context, "utilisateur supprimé " , Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Reponse> call, Throwable t) {
                        Toast.makeText(context, "pas ok", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        return rowView;
    }

}