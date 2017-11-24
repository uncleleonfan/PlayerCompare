package com.leon.playercompare;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceHolder;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkMediaPlayerProxy implements IMediaPlayerProxy, IMediaPlayer {

    private IjkMediaPlayer mIjkMediaPlayer;

    @Override
    public IMediaPlayer newInstance() {
        mIjkMediaPlayer = new IjkMediaPlayer();
        return this;
    }

    @Override
    public void prepareAsync() throws IllegalStateException {
        mIjkMediaPlayer.prepareAsync();
    }

    @Override
    public void start() throws IllegalStateException {
        mIjkMediaPlayer.start();
    }

    @Override
    public void stop() throws IllegalStateException {
        mIjkMediaPlayer.stop();
    }

    @Override
    public void pause() throws IllegalStateException {
        mIjkMediaPlayer.pause();
    }

    @Override
    public void release() {
        mIjkMediaPlayer.release();
    }

    @Override
    public void reset() {
        mIjkMediaPlayer.reset();
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        mIjkMediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void setLogEnabled(Boolean enable) {
        mIjkMediaPlayer.setLogEnabled(enable);
    }

    @Override
    public void enableMediaCodec() {
        mIjkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
    }

    @Override
    public void setOnPreparedListener(final OnPreparedListener listener) {
        mIjkMediaPlayer.setOnPreparedListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer) {
                listener.onPrepared(IjkMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnInfoListener(final OnInfoListener listener) {
        mIjkMediaPlayer.setOnInfoListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {
                return listener.onInfo(IjkMediaPlayerProxy.this);
            }
        });
    }

    @Override
    public void setOnCompletionListener(final OnCompletionListener listener) {
        mIjkMediaPlayer.setOnCompletionListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer) {
                listener.onCompletion(IjkMediaPlayerProxy.this);
            }
        });

    }

    @Override
    public void setOnErrorListener(final OnErrorListener listener) {
        mIjkMediaPlayer.setOnErrorListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {
                return listener.onError(IjkMediaPlayerProxy.this);
            }
        });

    }

    @Override
    public void setOnVideoSizeChangeListener(final OnVideoSizeChangeListener listener) {
        mIjkMediaPlayer.setOnVideoSizeChangedListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
                listener.onVideoSizeChange(IjkMediaPlayerProxy.this, iMediaPlayer.getVideoWidth(), iMediaPlayer.getVideoHeight());
            }
        });
    }

    @Override
    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mIjkMediaPlayer.setDataSource(context, uri);
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        mIjkMediaPlayer.setDataSource(path);
    }

    @Override
    public void setScreenOnWhilePlaying(Boolean enable) {
        mIjkMediaPlayer.setScreenOnWhilePlaying(enable);
    }
}
