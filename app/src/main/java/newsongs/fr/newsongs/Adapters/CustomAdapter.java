package newsongs.fr.newsongs.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends BaseAdapter{

    List<Utilisateur> result;
    Context context;

    private static LayoutInflater inflater=null;
    public CustomAdapter(Activity mainActivity, List<Utilisateur> prgmFriendsList) {

        result = prgmFriendsList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Utilisateur getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return result.get(position).getIdutilisateur();
    }

    public class Holder
    {
        TextView tv;
        ImageButton img;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=rowView.findViewById(R.id.textView1);
        holder.img=rowView.findViewById(R.id.imageButton1);
        holder.tv.setText(result.get(position).getPseudo());

        //les actions faisables pour chaque amis

        //voir en detail amis
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, "You Clicked " + result.get(position).getPseudo(), Toast.LENGTH_LONG).show();
            }
        });

        //Gestion du bouton
        //Si l'utilisateurCourant est ami avec l'utilisateur connecté
        //Alors on peut lui proposer de le supprimer de sa liste d'amis
        //Sinon on lui propose de l'ajouter à sa liste d'amis

        //Le champs estunami est un champs virtuel créé par la requête.
        if(getItem(position).estAmi()){
            holder.img.setImageResource(R.drawable.delete);
            //supprimer amis
            holder.img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences settings = context.getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                    int idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur
                    int friendToDelete = (int)getItemId(position);

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
                            Toast.makeText(context, "L'utilisateur n'a pas pu être supprimé", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
        }else
        {
            holder.img.setImageResource(R.drawable.plus);
            holder.img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences settings = context.getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                    int idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur
                    int friendToAdd = (int)getItemId(position);

                    Log.e("addFriend",idutilisateur + ","+friendToAdd);
                    UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);

                    Call<Reponse> call = service.addFriend(idutilisateur, friendToAdd);

                    call.enqueue(new Callback<Reponse>() {

                        @Override
                        public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                            if(response.code()==201 || response.code() == 200)
                            {
                                Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(context,"addFriend"+response.code() + ", ",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Reponse> call, Throwable t) {
                            Toast.makeText(context, "L'utilisateur n'a pas pu être ajouté", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
        return rowView;
    }

}