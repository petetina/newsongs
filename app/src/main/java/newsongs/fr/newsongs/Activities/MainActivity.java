package newsongs.fr.newsongs.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import newsongs.fr.newsongs.API.PlaylistClient;
import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.Fragments.MyPlayerFragment;
import newsongs.fr.newsongs.Interfaces.PlayerInterface;
import newsongs.fr.newsongs.Interfaces.UpdateableFragmentInterface;
import newsongs.fr.newsongs.Models.Playlist;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements PlayerInterface {
    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Tools.isConnected(getApplicationContext()))
        {
            getMenuInflater().inflate(R.menu.activity_main, menu);
            this.menu = menu;
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_mes_amis:
                Intent intent = new Intent(this,FriendsActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Tools.isConnected(this)) {

            //On teste si l'utilisateur de l'application s'est déjà authentifié
            SharedPreferences settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
            int idutilisateur = settings.getInt("idutilisateur", -2); //2

            //Si l'idutilisateur est -2, alors c'est la première utilisation de l'appli

            if (idutilisateur == -2) {
                Intent intent = new Intent(getApplicationContext(), FirstUseActivity.class);
                startActivity(intent);
                finish();
            } else {
                //On affiche activity_main seulement si on a des playlists à afficher
                PlaylistClient service = ServiceGenerator.createService(PlaylistClient.class);
                Call<List<Playlist>> call = service.getPlaylists(idutilisateur);

                call.enqueue(new Callback<List<Playlist>>() {
                    @Override
                    public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                        if(!response.body().isEmpty())
                            setContentView(R.layout.activity_main);
                        else
                            setContentView(R.layout.no_playlists);
                    }

                    @Override
                    public void onFailure(Call<List<Playlist>> call, Throwable t) {

                    }
                });


            }
        }else
            setContentView(R.layout.no_connection);
    }

    @Override
    public void init(String url, String titre){
        ((MyPlayerFragment)(getFragmentManager().findFragmentById(R.id.fragmentplayer))).init(url, titre);
    }

}
