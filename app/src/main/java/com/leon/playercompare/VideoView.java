package com.leon.playercompare;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoView extends SurfaceView implements IjkMediaPlayer.OnPreparedListener,
        IjkMediaPlayer.OnErrorListener,
        IjkMediaPlayer.OnCompletionListener,
        IjkMediaPlayer.OnInfoListener{

    private static final String TAG = "VideoView";

    private SurfaceHolder mSurfaceHolder;

    private IjkMediaPlayer mIjkMediaPlayer;
    private Uri mVideoUri;


    public VideoView(Context context) {
        this(context, null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(mCallback);

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    private final SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
            openVideo();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mSurfaceHolder = null;
            release();
        }

    };

    public void setVideoPath(String path) {
        setVideoUri(Uri.parse(path));
    }


    public void setVideoUri(Uri videoUri) {
        mVideoUri = videoUri;

    }

    private void openVideo() {
        if (mVideoUri == null) {
            return;
        }
        release();
        mIjkMediaPlayer = new IjkMediaPlayer();
        mIjkMediaPlayer.setLogEnabled(BuildConfig.DEBUG);
        //设置硬编码 自动旋转
        mIjkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
        mIjkMediaPlayer.setOnPreparedListener(this);
        mIjkMediaPlayer.setOnInfoListener(this);
        mIjkMediaPlayer.setOnCompletionListener(this);
        mIjkMediaPlayer.setOnErrorListener(this);

        try {
            mIjkMediaPlayer.setDisplay(mSurfaceHolder);
            mIjkMediaPlayer.setDataSource(getContext(), mVideoUri);
            mIjkMediaPlayer.setScreenOnWhilePlaying(true);
            Log.d(TAG, "openVideo: start prepare");
            mIjkMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        Log.d(TAG, "onPrepared: ");
        iMediaPlayer.start();
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        Log.d(TAG, "onInfo: " + i);
        return false;
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Log.d(TAG, "onError: " + i);
        return false;
    }

    private void release() {
        if (mIjkMediaPlayer != null) {
            mIjkMediaPlayer.stop();
            mIjkMediaPlayer.release();
            mIjkMediaPlayer = null;
        }
    }

}
