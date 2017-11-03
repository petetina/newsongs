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

import newsongs.fr.newsongs.API.FriendClient;
import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationsAdapter extends BaseAdapter{

    private List<Utilisateur> result;
    private Context context;

    private static LayoutInflater inflater=null;
    public InvitationsAdapter(Activity mainActivity, List<Utilisateur> prgmFriendsList) {

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
        ImageButton btnAccept,btnDeny;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list_invitations, null);
        holder.tv=rowView.findViewById(R.id.textView1);
        holder.btnAccept=rowView.findViewById(R.id.btnAccept);
        holder.btnDeny = rowView.findViewById(R.id.btnDeny);
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

        SharedPreferences settings = context.getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        final int idutilisateur = settings.getInt("idutilisateur", -2);

        holder.btnAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int friendToAdd = (int)getItemId(position);

                Log.e("acceptFriend",idutilisateur + ","+friendToAdd);
                FriendClient service = ServiceGenerator.createService(FriendClient.class);

                Call<Reponse> call = service.acceptInvitation(idutilisateur, friendToAdd);

                call.enqueue(new Callback<Reponse>() {

                    @Override
                    public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                        if(response.code() == 200)
                        {
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            result.remove(position);
                            notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(context,"L'utilisateur n'a pas pu être ajouté ! "+response.code(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Reponse> call, Throwable t) {
                        Toast.makeText(context, "L'utilisateur n'a pas pu être ajouté"+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        holder.btnDeny.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FriendClient service = ServiceGenerator.createService(FriendClient.class);
                int friendToDeny = (int)getItemId(position);
                Call<Reponse> call = service.deleteFriend(idutilisateur,friendToDeny);
                call.enqueue(new Callback<Reponse>() {
                    @Override
                    public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                        if(response.isSuccessful()){
                            result.remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Reponse> call, Throwable t) {
                        Toast.makeText(context,"Erreur lors du rejet de la demande !", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return rowView;
    }

}