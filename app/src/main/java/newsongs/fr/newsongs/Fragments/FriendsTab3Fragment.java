package newsongs.fr.newsongs.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import newsongs.fr.newsongs.API.FriendClient;
import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Adapters.CustomAdapter;
import newsongs.fr.newsongs.Models.Utilisateur;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by antoine on 02/11/17.
 */

public class FriendsTab3Fragment extends Fragment {
    private ListView lv;
    private TextView tvNoFriends;

    private int idutilisateur;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getContext(),"onCreateView3",Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.tabfriends3, container, false);
        SharedPreferences settings = getContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur

        if(idutilisateur != -2) {
            hook(view);
            getDatas();
        }

        return view;
    }

    private void hook(View view){
        lv = view.findViewById(R.id.lvFriends);
        tvNoFriends = view.findViewById(R.id.tvNoFriendsTab2);
    }

    private void getDatas(){
        FriendClient service = ServiceGenerator.createService(FriendClient.class);
        Call<List<Utilisateur>> user = service.getFriendsById(idutilisateur);

        user.enqueue(new Callback<List<Utilisateur>>() {

            @Override
            public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                if(response.code() == 200){
                    List<Utilisateur> list = response.body();
                    if(!response.body().isEmpty()){
                        lv.setVisibility(View.VISIBLE);
                        tvNoFriends.setVisibility(View.GONE);
                        lv.setAdapter(new CustomAdapter(getActivity(), list));
                    }
                }else if(response.code()==404){
                    lv.setVisibility(View.GONE);
                    tvNoFriends.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Erreur lors de la récupération des amis !", Toast.LENGTH_LONG).show();
            }
        });
    }
}