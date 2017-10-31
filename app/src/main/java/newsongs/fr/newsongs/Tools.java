package newsongs.fr.newsongs;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public abstract class Tools {
    public static final String PREFS_NAME = "PREFS";

    public static boolean isConnected(Context ctx){
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
