package com.leon.playercompare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

public class PLDroidPlayerActivity extends AppCompatActivity implements
        PLMediaPlayer.OnPreparedListener,PLMediaPlayer.OnInfoListener,
        PLMediaPlayer.OnErrorListener, PLMediaPlayer.OnCompletionListener{

    private static final String TAG = "PLDroidPlayerActivity";
    private PLVideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pldroid);

        mVideoView = findViewById(R.id.pl_video_view);
        mVideoView.setBufferingIndicator(new ProgressBar(this));
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnErrorListener(this);
        mVideoView.setOnCompletionListener(this);

        mVideoView.setScreenOnWhilePlaying(true);
        String path = "http://hc.yinyuetai.com/uploads/videos/common/2B40015FD4683805AAD2D7D35A80F606.mp4?sc=364e86c8a7f42de3&br=783&rd=Android";
        mVideoView.setVideoPath(path);
    }

    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer, int i) {
        Log.d(TAG, "onPrepared: ");
        mVideoView.start();
    }

    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
        return false;
    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }
}
