package com.leon.playercompare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.pili.pldroid.player.AVOptions;

public class PLDroidPlayerActivity extends AppCompatActivity{

    private static final String TAG = "PLDroidPlayerActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pldroid);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("PLDroidPlayer");

        VideoView mVideoView = findViewById(R.id.video_view);
        AVOptions avOptions = new AVOptions();
        avOptions.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_HW_DECODE);
        mVideoView.setMediaPlayerProxy(new PLMediaPlayerProxy(this, null));
//        String path = "http://hc.yinyuetai.com/uploads/videos/common/2B40015FD4683805AAD2D7D35A80F606.mp4?sc=364e86c8a7f42de3&br=783&rd=Android";
        String path = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";
        mVideoView.setVideoPath(path);
    }

}
