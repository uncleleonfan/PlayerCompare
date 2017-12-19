package com.leon.playercompare;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceHolder;

import java.io.IOException;

import io.vov.vitamio.MediaPlayer;

public class VitamioMediaPlayerProxy implements IMediaPlayerProxy, IMediaPlayer {

    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private boolean enableHWDecoder = false;

    public VitamioMediaPlayerProxy(Context context) {
        this(context, false);
    }

    public VitamioMediaPlayerProxy(Context context, boolean enableHWDecoder) {
        mContext = context;
        this.enableHWDecoder = enableHWDecoder;
    }

    @Override
    public IMediaPlayer newInstance() {
        mMediaPlayer = new MediaPlayer(mContext, enableHWDecoder);
        return this;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void start() throws IllegalStateException {
        mMediaPlayer.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        mMediaPlayer.stop();
    }

    @Override
    public void pause() throws IllegalStateException {
        mMediaPlayer.pause();
    }

    @Override
    public void release() {
        mMediaPlayer.release();
    }

    @Override
    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        mMediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void setLogEnabled(Boolean enable) {
    }

    @Override
    public void setOnPreparedListener(final OnPreparedListener listener) {
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                listener.onPrepared(VitamioMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnInfoListener(final OnInfoListener listener) {
        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return listener.onInfo(VitamioMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnCompletionListener(final OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                listener.onCompletion(VitamioMediaPlayerProxy.this);
            }
        });

    }

    @Override
    public void setOnErrorListener(final OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return listener.onError(VitamioMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnVideoSizeChangeListener(final OnVideoSizeChangeListener listener) {
        mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                listener.onVideoSizeChange(VitamioMediaPlayerProxy.this, width, height);
            }
        });
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mMediaPlayer.setDataSource(context, uri);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mMediaPlayer.setDataSource(path);
    }

    @Override
    public void setScreenOnWhilePlaying(Boolean enable) {
        mMediaPlayer.setScreenOnWhilePlaying(enable);
    }

    @Override
    public void enableMediaCodec() {
        //implement by MediaPlayer Construct
    }
}
