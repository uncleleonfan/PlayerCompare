package com.leon.playercompare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PLDroidPlayerActivity extends AppCompatActivity{

    private static final String TAG = "PLDroidPlayerActivity";
    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pldroid);
        mVideoView = findViewById(R.id.video_view);
        mVideoView.setMediaPlayerProxy(new PLMediaPlayerProxy(this));
        String path = "http://hc.yinyuetai.com/uploads/videos/common/2B40015FD4683805AAD2D7D35A80F606.mp4?sc=364e86c8a7f42de3&br=783&rd=Android";
        mVideoView.setVideoPath(path);
    }

}
