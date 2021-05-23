 b1 = findViewById(R.id.play_button);
        b2 = findViewById(R.id.pause_button);

        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    Uri uri = Uri.parse("https://ms18.sm3na.com/137/Sm3na_com_57667.mp3");
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.setDataSource(MainActivity.this, uri);
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                } catch(Exception e) {
                    System.out.println(e.toString());
                }
            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });

    }
    private void stopPlaying() {
        mp.release();
      //  mp = null;
    }