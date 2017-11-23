package com.leon.playercompare;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceHolder;

import java.io.IOException;

public interface IMediaPlayer {

    void prepareAsync() throws IllegalStateException;

    void start() throws IllegalStateException;

    void stop() throws IllegalStateException;

    void pause() throws IllegalStateException;

    void release();

    void reset();

    void setDisplay(SurfaceHolder surfaceHolder);

    void setLogEnabled(Boolean enable);

    void setOption();

    void setOnPreparedListener(OnPreparedListener listener);

    void setOnInfoListener(OnInfoListener listener);

    void setOnCompletionListener(OnCompletionListener listener);

    void setOnErrorListener(OnErrorListener listener);

    void setOnVideoSizeChangeListener(OnVideoSizeChangeListener listener);

    void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    void setDataSource(String path) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    void setScreenOnWhilePlaying(Boolean enable);


    interface OnInfoListener {
        boolean onInfo(IMediaPlayer iMediaPlayer);
    }

    interface OnErrorListener {
        boolean onError(IMediaPlayer iMediaPlayer);
    }

    interface OnPreparedListener {
        void onPrepared(IMediaPlayer iMediaPlayer);
    }

    interface OnCompletionListener {
        void onCompletion(IMediaPlayer iMediaPlayer);
    }

    interface OnVideoSizeChangeListener {
        void onVideoSizeChange(IMediaPlayer iMediaPlayer, int width, int height);
    }

}
