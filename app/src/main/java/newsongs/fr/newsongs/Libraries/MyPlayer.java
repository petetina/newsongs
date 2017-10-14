package newsongs.fr.newsongs.Libraries;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
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

public class MyPlayer {
    private Activity ctx;
    private Button btnApres,btnPause,btnPlay,btnPrecedent;
    private TextView tx1,tx2,tx3;

    private double startTime = 0;
    private double currentTime = 0;
    private double finalTime = 0;
    private int forwardTime = 5000;
    private int backwardTime = 5000;

    private SeekBar seekbar;
    public static int oneTimeOnly = 0;
    private MediaPlayer mediaPlayer;
    private Handler myHandler = new Handler();

    public MyPlayer(Activity ctxf){
        this.ctx = ctxf;

        hook();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Playing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource("https://e-cdns-preview-5.dzcdn.net/stream/51afcde9f56a132096c0496cc95eb24b-4.mp3");
                } catch (IllegalArgumentException e) {
                    Toast.makeText(ctx, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(ctx, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(ctx, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IllegalStateException e) {
                    Toast.makeText(ctx, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(ctx, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }

                mediaPlayer.seekTo((int)currentTime);
                mediaPlayer.start();

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
                myHandler.postDelayed(UpdateSongTime,100);
                btnPause.setEnabled(true);
                btnPlay.setEnabled(false);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Pausing sound",Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                currentTime = seekbar.getProgress();
                btnPause.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });

        /*btnApres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(ctx,"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ctx,"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
/*
        btnPrecedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(ctx,"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ctx,"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
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
    private void hook(){

        btnPlay = (Button)ctx.findViewById(R.id.btnPlay);
        btnPause = (Button) ctx.findViewById(R.id.btnPause);
        tx1 = (TextView)ctx.findViewById(R.id.textView2);
        tx2 = (TextView)ctx.findViewById(R.id.textView3);
        tx3 = (TextView)ctx.findViewById(R.id.textView4);
        seekbar = (SeekBar)ctx.findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        btnPause.setEnabled(false);
    }
}
