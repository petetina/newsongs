package newsongs.fr.newsongs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Adapters.CustomAdapter;
import newsongs.fr.newsongs.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsActivity extends BaseActivity{
    private ListView lv;
    private TextView tvNoFriends;
    private SearchView searchView;
    private int idutilisateur;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_friends, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_retour:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        idutilisateur = settings.getInt("idutilisateur", -2); //2//Appel à notre API pour créer l'utilisateur

        if(idutilisateur != -2) {
            setContentView(R.layout.activity_friends);
            hook();

            UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
            Call<List<Utilisateur>> user = service.getFriendsById(idutilisateur);

            user.enqueue(new Callback<List<Utilisateur>>() {

                @Override
                public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                    Log.e("onResponse",response.code() + "");
                    if(response.code() == 200){
                        List<Utilisateur> list = response.body();
                        if(!response.body().isEmpty()){
                            lv.setVisibility(View.VISIBLE);
                            tvNoFriends.setVisibility(View.GONE);
                            lv.setAdapter(new CustomAdapter(FriendsActivity.this, list));
                        }
                    }else if(response.code()==404){
                        lv.setVisibility(View.GONE);
                        tvNoFriends.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "pas ok", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void hook(){
        lv = (ListView) findViewById(R.id.lvFriends);
        lv.setAdapter(new CustomAdapter(this, new ArrayList<Utilisateur>()));
        tvNoFriends = (TextView)findViewById(R.id.tvNoFriends);
        tvNoFriends.setVisibility(View.GONE);
        searchView = (SearchView)findViewById(R.id.searchViewFriends);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("onClick","cliqué");
                if(idutilisateur != -2 && !searchView.getQuery().toString().isEmpty()){
                    UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
                    Call<List<Utilisateur>> call = service.findAllByPseudo(searchView.getQuery().toString());
                    call.enqueue(new Callback<List<Utilisateur>>() {
                        @Override
                        public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                            Log.e("onResponseQuery",response.body().size()+"");
                            if(response.body().isEmpty()){
                                Toast.makeText(getApplicationContext(),"Aucun résultat !",Toast.LENGTH_LONG).show();
                                lv.setVisibility(View.GONE);
                                tvNoFriends.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                lv.setVisibility(View.VISIBLE);
                                lv.setAdapter(new CustomAdapter(FriendsActivity.this,response.body()));
                                tvNoFriends.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                            Log.e("onFailureQuery",t.getMessage());
                            Toast.makeText(getApplicationContext(),"Erreur de recherche !",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

}
