package com.example.android.mymiowk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        if(mMediaPlayer != null)
                            mMediaPlayer.pause();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        if(mMediaPlayer != null)
                            mMediaPlayer.pause();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, keep playing
                        if(mMediaPlayer != null)
                            mMediaPlayer.setVolume(0.2f,0.2f);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        if(mMediaPlayer != null) {
                            mMediaPlayer.setVolume(1f, 1f);
                            mMediaPlayer.start();
                        }

                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            stopPlaying();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
       final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("әpә","father",R.drawable.family_father,R.raw.family_father));
        words.add(new Word("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word("angsi","son",R.drawable.family_son,R.raw.family_son));
        words.add(new Word("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word("teṭe","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Word("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Word("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Word("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));


        WordAdapter adapter = new WordAdapter(this,words,R.color.category_family);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                stopPlaying();
                mMediaPlayer = MediaPlayer.create(FamilyActivity.this,words.get(position).getAudio());
                mMediaPlayer.start();
                int result =  mAudioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer.start();
                }
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
    }
    protected void stopPlaying() {
        // If media player is not null then try to stop it
        if (mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
