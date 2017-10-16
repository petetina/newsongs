package newsongs.fr.newsongs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstUseActivity extends BaseActivity {
    private Button btnInscription;
    private Button btnConnectWithDeezer;
    private Button btnConnecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstuse);

        btnInscription = (Button)findViewById(R.id.btnInscription);
        btnConnectWithDeezer = (Button)findViewById(R.id.btnConnectDezeer);
        btnConnecte = (Button)findViewById(R.id.btnConnect);

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

        btnConnecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        // restore any saved session
        SessionStore sessionStore = new SessionStore();
        /*if (sessionStore.restore(mDeezerConnect, getApplicationContext())) {
            Toast.makeText(getApplicationContext(),"Connecté",Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(),"Pas connecté",Toast.LENGTH_LONG).show();*/
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

                            //Une fois qu'on a récupéré l'id de l'utilisateur courant,
                            //on va créer un utilisateur dans notre bdd (si l'utilisateur n'existe pas déjà dans la bdd)
                            UtilisateurClient service = ServiceGenerator.createService(UtilisateurClient.class);
                            Call<Reponse> call = service.createUser(currentUser.getEmail(),currentUser.getName(),"");
                            call.enqueue(new Callback<Reponse>() {
                                @Override
                                public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                                    if(response.code() == 201) {
                                        //On sauvegarde l'id de l'utilisateur dans les préférences partagées
                                        //On met à jour les préférences partagées
                                        SharedPreferences settings;
                                        SharedPreferences.Editor editor;
                                        settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                                        editor = settings.edit(); //2

                                        editor.putInt("idutilisateur", (int) (currentUser.getId())); //3
                                        editor.commit(); //4
                                        Log.e("idutilisateur", Long.toString(currentUser.getId()));

                                        Intent intent = new Intent(FirstUseActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }else
                                        Toast.makeText(getApplicationContext(),"Erreur lors de la création de votre compte !",Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<Reponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Erreur interne.",Toast.LENGTH_LONG).show();
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
