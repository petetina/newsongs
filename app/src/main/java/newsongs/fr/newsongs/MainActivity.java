package newsongs.fr.newsongs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import newsongs.fr.newsongs.Fragments.MyPlayerFragment;

public class MainActivity extends BaseActivity implements PlayerInterface {
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Tools.isConnected(this)) {

            //On teste si l'utilisateur de l'application s'est déjà authentifié
            SharedPreferences settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
            int idutilisateur = settings.getInt("idutilisateur", -2); //2

            //Si l'idutilisateur est -2, alors c'est la premièr utilisation de l'appli

            if (idutilisateur == -2) {
                Intent intent = new Intent(getApplicationContext(), FirstUseActivity.class);
                startActivity(intent);
            } else {

                setContentView(R.layout.activity_main);


                Toast.makeText(getApplicationContext(), idutilisateur + "", Toast.LENGTH_LONG).show();


            }
        }else
            setContentView(R.layout.no_connection);
    }

    public void init(String url, String titre){
        ((MyPlayerFragment)(getFragmentManager().findFragmentById(R.id.fragmentplayer))).init(url, titre);
    }
}
