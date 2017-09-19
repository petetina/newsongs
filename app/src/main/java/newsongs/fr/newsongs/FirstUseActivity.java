package newsongs.fr.newsongs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;

public class FirstUseActivity extends BaseActivity {
    private Button btnInvite;
    private Button btnConnecte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnConnecte = (Button)findViewById(R.id.btnConnecte);
        btnInvite = (Button)findViewById(R.id.btnInvite);

        //Définitions des listeners
        btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //On met à jour les préférences partagées
                SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
                editor = settings.edit(); //2

                editor.putInt("idutilisateur",-1); //3
                editor.commit(); //4

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

        btnConnecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launches the authentication process
                connectToDeezer();
            }
        });

        // restore any saved session
        SessionStore sessionStore = new SessionStore();
        if (sessionStore.restore(mDeezerConnect, getApplicationContext())) {
            Toast.makeText(getApplicationContext(),"Connecté",Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(),"Pas connecté",Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "Connecté !", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Pas connecté !", Toast.LENGTH_LONG).show();
/*
            // Launch the Home activity
            Intent intent = new Intent(FirstUseActivity.this, MainActivity.class);
            startActivity(intent);*/
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
