package com.leon.playercompare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class VitamioPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("VitamioPlayer");

        VideoView mVideoView = findViewById(R.id.video_view);
        mVideoView.setMediaPlayerProxy(new VitamioMediaPlayerProxy(this, false));
        String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        mVideoView.setVideoPath(path);
    }

}
