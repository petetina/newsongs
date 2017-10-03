package newsongs.fr.newsongs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends BaseActivity {
    private Button btnInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnInscription  =(Button)findViewById(R.id.btnInscription2);

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Appel à notre API pour créer l'utilisateur
                //Méthode create user
            }
        });
    }
}
