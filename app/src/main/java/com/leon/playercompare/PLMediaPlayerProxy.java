package com.leon.playercompare;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceHolder;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

public class PLMediaPlayerProxy implements IMediaPlayerProxy, IMediaPlayer {

    private PLMediaPlayer mMediaPlayer;
    private Context mContext;
    private AVOptions mAvOptions;

    public PLMediaPlayerProxy(Context context) {
        this(context, null);
    }

    public PLMediaPlayerProxy(Context context, AVOptions avOptions) {
        mContext = context;
        mAvOptions = avOptions;
    }


    @Override
    public IMediaPlayer newInstance() {
        mMediaPlayer = new PLMediaPlayer(mContext, mAvOptions);
        return this;
    }

    @Override
    public void enableMediaCodec() {
        //implement by Avoptions
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
        mMediaPlayer.setDebugLoggingEnabled(enable);
    }


    @Override
    public void setOnPreparedListener(final OnPreparedListener listener) {
        mMediaPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(PLMediaPlayer plMediaPlayer, int i) {
                listener.onPrepared(PLMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnInfoListener(final OnInfoListener listener) {
        mMediaPlayer.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                return listener.onInfo(PLMediaPlayerProxy.this);
            }
        });

    }

    @Override
    public void setOnCompletionListener(final OnCompletionListener listener) {
        mMediaPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(PLMediaPlayer plMediaPlayer) {
                listener.onCompletion(PLMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnErrorListener(final OnErrorListener listener) {
        mMediaPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                return listener.onError(PLMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnVideoSizeChangeListener(final OnVideoSizeChangeListener listener) {
        mMediaPlayer.setOnVideoSizeChangedListener(new PLMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int w, int h) {
                listener.onVideoSizeChange(PLMediaPlayerProxy.this, w, h);
            }
        });
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mMediaPlayer.setDataSource(path);
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mMediaPlayer.setDataSource(uri.getPath());
    }

    @Override
    public void setScreenOnWhilePlaying(Boolean enable) {
        mMediaPlayer.setScreenOnWhilePlaying(enable);
    }

}
