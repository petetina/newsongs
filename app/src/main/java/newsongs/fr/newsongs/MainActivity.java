package newsongs.fr.newsongs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //On teste si l'utilisateur de l'application s'est déjà authentifié
        SharedPreferences settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        int idutilisateur = settings.getInt("idutilisateur", -2); //2

        //Si l'idutilisateur est -2, alors c'est la premièr utilisation de l'appli

        if (idutilisateur == -2) {
            Intent intent = new Intent(getApplicationContext(), FirstUseActivity.class);
            startActivity(intent);
        } else{
            setContentView(R.layout.activity_main);
            Toast.makeText(getApplicationContext(),idutilisateur+"",Toast.LENGTH_LONG).show();
        }
    }
}
