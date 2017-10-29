package newsongs.fr.newsongs.Fragments;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import newsongs.fr.newsongs.R;

/**
 * Created by antoine on 14/10/17.
 */

public class MyPlayerFragment extends Fragment {
    private Button btnApres,btnPause,btnPrecedent;
    public Button btnPlay;
    private TextView tx1,tx2,tx3;

    private double startTime = 0;
    private double currentTime = 0;
    private double finalTime = 0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    private SeekBar seekbar;
    private static int oneTimeOnly = 0;
    private MediaPlayer mediaPlayer;
    private Handler myHandler = new Handler();

    public MyPlayerFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player,container,false);

        hook(view);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fragment player","play");
                playMusic();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer !=null){

                    mediaPlayer.pause();
                    currentTime = seekbar.getProgress();
                    btnPause.setEnabled(false);
                    btnPlay.setEnabled(true);
                }
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                currentTime = seekBar.getProgress();
                mediaPlayer.seekTo((int)currentTime);
            }
        });

        return view;
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    private void hook(View view){

        btnPlay = (Button)view.findViewById(R.id.btnPlay);
        btnPause = (Button) view.findViewById(R.id.btnPause);
        tx1 = (TextView)view.findViewById(R.id.textView2);
        tx2 = (TextView)view.findViewById(R.id.textView3);
        tx3 = (TextView)view.findViewById(R.id.textView4);
        seekbar = (SeekBar)view.findViewById(R.id.seekBar);
        seekbar.setClickable(false);

        btnPause.setEnabled(false);
    }

    public void playMusic(){
        Log.e("playfragment", "playMusic");
        if(mediaPlayer != null){

            mediaPlayer.start();
            myHandler.postDelayed(UpdateSongTime,100);
            btnPause.setEnabled(true);
            btnPlay.setEnabled(false);
        }
        else
            Toast.makeText(getActivity(),"Veuillez sélectionner une musique d'abord !",Toast.LENGTH_LONG).show();
    }

    public void init(String url){
        Log.e("playfragment", "init");
        if(mediaPlayer == null) {
            Log.e("init", "mediaPlayer null");
            mediaPlayer = new MediaPlayer();
        }
        else{
            Log.e("init","mediaPlayer isplaying");
            mediaPlayer.stop();
            currentTime =0;
            startTime=0;
            btnPlay.setEnabled(true);
            btnPause.setEnabled(false);
            mediaPlayer = new MediaPlayer();
        }

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getActivity(), "Problème de chargement de la musique !", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getActivity(), "Problème de chargement de la musique !", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Problème de chargement de la musique !", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getActivity(), "Problème de chargement de la musique !", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Problème de chargement de la musique !", Toast.LENGTH_LONG).show();
        }

        mediaPlayer.seekTo((int)currentTime);

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        tx2.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        tx1.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        seekbar.setProgress((int)startTime);
    }
}
