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
import newsongs.fr.newsongs.Adapters.InvitationsAdapter;
import newsongs.fr.newsongs.Interfaces.UpdateableFragmentInterface;
import newsongs.fr.newsongs.Models.Utilisateur;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by antoine on 02/11/17.
 */

public class FriendsTab2Fragment extends Fragment implements UpdateableFragmentInterface {
    private ListView lv;
    private TextView tvNoInvitations;

    private int idutilisateur;

    private LayoutInflater inflater;
    private ViewGroup container;
    private Bundle savedInstanceState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfriends2, container, false);
        SharedPreferences settings = getContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur

        if(idutilisateur != -2) {
            hook(view);
            getDatas();
        }

        this.inflater = inflater;
        this.container = container;
        this.savedInstanceState = savedInstanceState;

        return view;
    }

    private void hook(View view){
        lv = view.findViewById(R.id.lvInvitations);
        lv.setVisibility(View.VISIBLE);
        tvNoInvitations = view.findViewById(R.id.tvNoInvitations);
        tvNoInvitations.setVisibility(View.GONE);
    }

    private void getDatas(){
        FriendClient service = ServiceGenerator.createService(FriendClient.class);
        Call<List<Utilisateur>> call = service.getInvitations(idutilisateur);
        call.enqueue(new Callback<List<Utilisateur>>() {
            @Override
            public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                if(response.isSuccessful())
                {
                    if(response.body().isEmpty())
                    {
                        lv.setVisibility(View.GONE);
                        tvNoInvitations.setVisibility(View.VISIBLE);
                    }else
                        lv.setAdapter(new InvitationsAdapter(getActivity(),response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                Toast.makeText(getContext(),"Erreur lors de la récupération des invitations",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void refresh() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}
