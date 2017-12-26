package com.leon.playercompare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPlayerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("IjkPlayer");

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        VideoView videoView = findViewById(R.id.video_view);
        videoView.setMediaPlayerProxy(new IjkMediaPlayerProxy());
        String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        videoView.setVideoPath(path);
    }


    @Override
    protected void onStop() {
        super.onStop();
        IjkMediaPlayer.native_profileEnd();
    }

}
