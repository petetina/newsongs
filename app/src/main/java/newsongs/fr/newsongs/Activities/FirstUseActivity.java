package newsongs.fr.newsongs.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.User;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.AsyncDeezerTask;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;

import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.API.UtilisateurClient;
import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstUseActivity extends BaseActivity {
    private Button btnInscription;
    private Button btnConnectWithDeezer;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstuse);

        btnInscription = (Button)findViewById(R.id.btnInscription);
        btnConnectWithDeezer = (Button)findViewById(R.id.btnConnectDezeer);
        btnConnect = (Button)findViewById(R.id.btnConnect);

        //Définitions des listeners
        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);

            }
        });

        btnConnectWithDeezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launches the authentication process
                connectToDeezer();
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(FirstUseActivity.this);
                dialog.setContentView(R.layout.connect_dialog);
                dialog.setTitle("Me connecter");

                // set the custom dialog components - text, image and button
                final EditText txtpseudo = dialog.findViewById(R.id.txtPseudo);
                final EditText txtmotdepasse = dialog.findViewById(R.id.txtMotDePasse);
                ImageButton btnClose = dialog.findViewById(R.id.btnCloseDialog);
                Button btnConnect = dialog.findViewById(R.id.btnConnect2);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnConnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!txtpseudo.getText().toString().isEmpty() && !txtmotdepasse.getText().toString().isEmpty()){
                            UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
                            Call<Integer> call = service.login(txtpseudo.getText().toString(), Tools.md5(txtmotdepasse.getText().toString()));
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if(response.code()==200)
                                    {
                                        //On sauvegarde l'id de l'utilisateur dans les préférences partagées
                                        //On met à jour les préférences partagées
                                        SharedPreferences settings;
                                        SharedPreferences.Editor editor;
                                        settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                                        editor = settings.edit(); //2

                                        editor.putInt("idutilisateur",response.body()); //3
                                        editor.commit(); //4

                                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(i);
                                    }else if(response.code() == 404){
                                        Toast.makeText(getApplicationContext(),"Pseudo ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Une erreur est survenue lors de la connexion !"+t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }else
                            Toast.makeText(getApplicationContext(), "Veuillez renseigner tous les champs !",Toast.LENGTH_LONG).show();
                    }
                });

                dialog.show();
            }
        });
    }


    /**
     * Asks the SDK to display a log in dialog for the user
     */
    private void connectToDeezer() {
        mDeezerConnect.authorize(this, PERMISSIONS, mDeezerDialogListener);
    }

    /**
     * A listener for the Deezer Login Dialog
     */
    private DialogListener mDeezerDialogListener = new DialogListener() {

        @Override
        public void onComplete(final Bundle values) {

            // On sauvegarde la session de l'utilisateur connecté
            SessionStore sessionStore = new SessionStore();
            sessionStore.save(mDeezerConnect, FirstUseActivity.this);

            if(mDeezerConnect.isSessionValid())
            {

                //On récupère l'id du user qui vient de se connecter
                // get the current user id
                DeezerRequest request = DeezerRequestFactory.requestCurrentUser();
                AsyncDeezerTask task = new AsyncDeezerTask(mDeezerConnect, new JsonRequestListener() {

                    @Override
                    public void onResult(final Object result, final Object requestId) {
                        if (result instanceof User) {
                            final User currentUser = (User)result;
                            Log.e("idutilisateurdeezer",currentUser.getId()+"");

                            //Une fois qu'on a récupéré l'idutilisateurdeezer de l'utilisateur courant,
                            //on va créer un utilisateur dans notre bdd (si l'utilisateur n'existe pas déjà dans la bdd)
                            UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
                            Call<Reponse> call;
                            String mail = currentUser.getEmail() == null ? "" : currentUser.getEmail();
                            call = service.createUser(mail,currentUser.getName(),"", currentUser.getId());
                            call.enqueue(new Callback<Reponse>() {
                                @Override
                                public void onResponse(Call<Reponse> call, Response<Reponse> response) {

                                    if(response.code() == 201 || response.code() == 200) {
                                        //On sauvegarde l'id de l'utilisateur dans les préférences partagées
                                        //On met à jour les préférences partagées
                                        SharedPreferences settings;
                                        SharedPreferences.Editor editor;
                                        settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                                        editor = settings.edit(); //2

                                        editor.putInt("idutilisateur", Integer.parseInt(response.body().getMessage())); //3
                                        Log.e("idutilisateur", response.body().getMessage());

                                        editor.putLong("idutilisateurdeezer", currentUser.getId()); //3
                                        editor.commit(); //4
                                        Log.e("idutilisateurdeezer", Long.toString(currentUser.getId()));

                                        Intent intent = new Intent(FirstUseActivity.this, MainActivity.class);
                                        intent.putExtra("firstuse",true);
                                        startActivity(intent);
                                        finish();
                                        if(response.code()==200)
                                            Toast.makeText(getApplicationContext(), "Enfin de retour !", Toast.LENGTH_LONG).show();


                                    }
                                }

                                @Override
                                public void onFailure(Call<Reponse> call, Throwable t) {
                                    t.printStackTrace();
                                    Toast.makeText(getApplicationContext(),t.getMessage()+"Erreur interne.",Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            handleError(new IllegalArgumentException());
                        }
                    }

                    @Override
                    public void onUnparsedResult(final String response, final Object requestId) {
                        handleError(new DeezerError("Unparsed reponse"));
                    }


                    @Override
                    public void onException(final Exception exception, final Object requestId) {
                        handleError(exception);
                    }
                });

                task.execute(request);

            }

        }

        @Override
        public void onException(final Exception exception) {
            Toast.makeText(FirstUseActivity.this, "exception : " + exception.getMessage(), Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCancel() {
            Toast.makeText(FirstUseActivity.this, "Cancelled ", Toast.LENGTH_LONG).show();
        }


    };
}
