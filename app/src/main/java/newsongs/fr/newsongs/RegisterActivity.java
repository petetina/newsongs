package newsongs.fr.newsongs;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private Button btnInscription;
    private TextView txtPseudo;
    private TextView txtEmail;
    private TextView txtPrenom;
    private TextView txtNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hook();

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel à notre API pour créer l'utilisateur
                UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
                Utilisateur utilisateur = new Utilisateur();

                Call<Reponse> call = service.createUser();
                call.enqueue(new Callback<Reponse>() {
                    @Override
                    public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                        if(response.code() == 400)
                            Log.e("dfgbfdng","Bad request");
                        else
                            Log.e("onResponse","code = " + call.request().url().toString());
                    }

                    @Override
                    public void onFailure(Call<Reponse> call, Throwable t) {
                        Log.e("fail","OnFailure");
                    }
                });


            }
        });
    }

    private void hook(){
        btnInscription  =(Button)findViewById(R.id.btnInscription2);
        txtPseudo = (TextView)findViewById(R.id.txtPseudo);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtPrenom = (TextView)findViewById(R.id.txtPrenom);
        txtNom = (TextView)findViewById(R.id.txtNom);
    }
}
