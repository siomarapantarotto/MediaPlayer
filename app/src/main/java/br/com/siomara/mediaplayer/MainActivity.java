package br.com.siomara.mediaplayer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // Hide action bar
        setContentView(R.layout.activity_main);

        /** Creates object media player */
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.idontwanttobe_gavindegraw);

        this.configVolume();
    }

    /** Plays media */
    public void play (View view) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    /** Pauses media */
    public void pause(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /** Stops media */
    public void stop(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.idontwanttobe_gavindegraw);
        }
    }

    /** Initial configuration for volume control */
    private void configVolume() {
        seekBarVolume = findViewById(R.id.seekBarVolume);

        // Retrieve volume information from user device/
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Get max and actual volume
        int maxVolume    = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Configure max volume to seekBarVolume
        seekBarVolume.setMax(maxVolume);

        // Configure actual volume to seekBarVolume
        seekBarVolume.setProgress(actualVolume);

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // listens to when user slides the seekBarVolume
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,
                        0); // Flags won't be implemented for this sample
                        //AudioManager.FLAG_SHOW_UI);
            }

            // Not used for this sample
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // not used for this sample
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    /** Pauses the media if user picks another application. */
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    /**
     * Saves user resources when app is destroyed.
     * If mediaPlayer is null: nothing to be done;
     * If not null and playing: things must be done to save user resources.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();  // Remove memory resources
            mediaPlayer = null;     // Clear memory allocated for media player
        }
    }
}
