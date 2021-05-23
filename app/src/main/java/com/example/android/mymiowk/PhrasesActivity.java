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

public class PhrasesActivity extends AppCompatActivity {
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
        final  ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("minto wuksus","Where are you going?",R.raw.phrase_where_are_you_going));
        words.add(new Word("tinnә oyaase'nә","What is your name?",R.raw.phrase_what_is_your_name));
        words.add(new Word("oyaaset...","My name is...",R.raw.phrase_my_name_is));
        words.add(new Word("michәksәs?","How are you feeling?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("kuchi achit","I’m feeling good.",R.raw.phrase_im_feeling_good));
        words.add(new Word("әәnәs'aa?","Are you coming?",R.raw.phrase_are_you_coming));
        words.add(new Word("hәә’ әәnәm","Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        words.add(new Word("әәnәm","I’m coming.",R.raw.phrase_im_coming));
        words.add(new Word("yoowutis","Let’s go.",R.raw.phrase_lets_go));
        words.add(new Word("әnni'nem","Come here.",R.raw.phrase_come_here));


        WordAdapter adapter = new WordAdapter(this,words,R.color.category_phrases);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                stopPlaying();
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,words.get(position).getAudio());
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
