package newsongs.fr.newsongs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
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

            }
        });

        btnConnecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
