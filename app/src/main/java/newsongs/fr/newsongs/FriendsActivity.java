package newsongs.fr.newsongs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends BaseActivity {

    ListView lv;
    ArrayList prgmName;

    public static int [] prgmImages={R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp,R.drawable.ic_delete_forever_black_24dp};
    public static String [] prgmNameList={"Gerard","Antoine","Monique","Jean-Celestin","Marie-therese","Michel","Marcelle","Christian","Bertrand"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, prgmNameList, prgmImages));

        getFriendsByUser();
    }



    private void getFriendsByUser(){

        SharedPreferences settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        int idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur

        UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);

        Call<List<Utilisateur>> user = service.getFriendsById(idutilisateur);

        user.enqueue(new Callback<List<Utilisateur>>() {

            @Override
            public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                Toast.makeText(getApplicationContext(), response.body().get(0).getIdutilisateur() + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "pas ok", Toast.LENGTH_LONG).show();
            }
        });
    }
}
