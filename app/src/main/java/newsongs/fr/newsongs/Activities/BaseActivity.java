package newsongs.fr.newsongs.Activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;

import newsongs.fr.newsongs.R;

//Activite mere (toutes les activites extend celle ci)
public class BaseActivity extends AppCompatActivity {
    public static final String DEEZER_API_KEY = "252962";
    protected DeezerConnect mDeezerConnect = null;
    protected static final String[] PERMISSIONS = new String[]{
            Permissions.BASIC_ACCESS, Permissions.OFFLINE_ACCESS,
            Permissions.MANAGE_LIBRARY,
            Permissions.LISTENING_HISTORY
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        mDeezerConnect = new DeezerConnect(this, DEEZER_API_KEY);
    }

    /**
     * Handle errors by displaying a toast and logging.
     *
     * @param exception
     *            the exception that occured while contacting Deezer services.
     */
    protected void handleError(final Exception exception) {
        String message = exception.getMessage();
        if (TextUtils.isEmpty(message)) {
            message = exception.getClass().getName();
        }

        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        ((TextView) toast.getView().findViewById(android.R.id.message)).setTextColor(Color.RED);
        toast.show();

        Log.e("BaseActivity", "Exception occured " + exception.getClass().getName(), exception);
    }
}
