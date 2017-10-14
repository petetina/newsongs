package newsongs.fr.newsongs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import newsongs.fr.newsongs.Libraries.MyPlayer;
import newsongs.fr.newsongs.Libraries.MySlider;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //On teste si l'utilisateur de l'application s'est déjà authentifié
        SharedPreferences settings = getApplicationContext().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        int idutilisateur = settings.getInt("idutilisateur",-2); //2

        //Si l'idutilisateur est -2, alors c'est la premièr utilisation de l'appli
        if(idutilisateur == -2){
            Intent intent = new Intent(getApplicationContext(),FirstUseActivity.class);
            startActivity(intent);
        }else{
            setContentView(R.layout.activity_main);

        }
*/

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialisation du player
        MyPlayer myPlayer = new MyPlayer(this);
        //Initialisation du slider
        MySlider mySlider = new MySlider(this);


    }
}
