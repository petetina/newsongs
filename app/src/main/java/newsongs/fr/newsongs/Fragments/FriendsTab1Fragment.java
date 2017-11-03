package newsongs.fr.newsongs.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

public class FriendsTab1Fragment extends Fragment {
    private ListView lv;
    private SearchView searchView;
    private int idutilisateur;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getContext(),"onCreateView1",Toast.LENGTH_SHORT).show();
        View view = inflater.inflate(R.layout.tabfriends1, container, false);

        SharedPreferences settings = getContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur

        if(idutilisateur != -2) {
            hook(view);
        }

        return view;
    }


    private void hook(View view){
        lv = view.findViewById(R.id.lvSearchFriends);
        lv.setAdapter(new CustomAdapter(getActivity(), new ArrayList<Utilisateur>()));

        searchView = view.findViewById(R.id.searchViewFriends);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getDatas();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    private void getDatas(){
        if(idutilisateur != -2 && !searchView.getQuery().toString().isEmpty()){
            UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
            Call<List<Utilisateur>> call = service.findAllByPseudo(idutilisateur,searchView.getQuery().toString());
            call.enqueue(new Callback<List<Utilisateur>>() {
                @Override
                public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                    if(response.body().isEmpty()){
                        lv.setAdapter(new CustomAdapter(getActivity(),new ArrayList<Utilisateur>()));
                        Toast.makeText(getContext(),"Aucun résultat !",Toast.LENGTH_LONG).show();
                    }
                    else
                        lv.setAdapter(new CustomAdapter(getActivity(),response.body()));

                }

                @Override
                public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                    Toast.makeText(getContext(),"Erreur de recherche !",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}