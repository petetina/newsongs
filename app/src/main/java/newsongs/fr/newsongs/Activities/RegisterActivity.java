package newsongs.fr.newsongs.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    private Button btnInscription;
    private TextView txtPseudo;
    private TextView txtEmail;
    private TextView txtMotDePasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        hook();

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInscription.setEnabled(false);
                //Appel à notre API pour créer l'utilisateur
                UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
                Utilisateur utilisateur = new Utilisateur();

                utilisateur.setPseudo(txtPseudo.getText().toString());
                utilisateur.setMail(txtEmail.getText().toString());
                utilisateur.setMotdepasse(Tools.md5(txtMotDePasse.getText().toString()));

                Call<Reponse> call = service.createUser(utilisateur.getMail(),utilisateur.getPseudo(),utilisateur.getMotdepasse());
                call.enqueue(new Callback<Reponse>() {
                    @Override
                    public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                        if(response.code() == 201){
                            Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                            //On sauvegarde l'id de l'utilisateur dans les préférences partagées
                            //On met à jour les préférences partagées
                            SharedPreferences settings;
                            SharedPreferences.Editor editor;
                            settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                            editor = settings.edit(); //2

                            editor.putInt("idutilisateur",Integer.parseInt(response.body().getMessage())); //3
                            editor.commit(); //4

                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            btnInscription.setEnabled(true);
                            Toast.makeText(getApplicationContext(), "L'inscription a échoué ! Essayez un nouveau pseudo", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<Reponse> call, Throwable t) {
                        Log.e("fail",t.getMessage());
                        btnInscription.setEnabled(true);
                    }
                });


            }
        });
    }

    private void hook(){
        btnInscription  =(Button)findViewById(R.id.btnInscription2);
        txtPseudo = (TextView)findViewById(R.id.txtPseudo);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        txtMotDePasse = (TextView)findViewById(R.id.txtMotDePasse);
    }
}
