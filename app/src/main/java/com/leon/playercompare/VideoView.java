package com.leon.playercompare;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


public class VideoView extends SurfaceView implements IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnInfoListener,
        IMediaPlayer.OnVideoSizeChangeListener{

    private static final String TAG = "VideoView";

    private SurfaceHolder mSurfaceHolder;

    private String mVideoPath = null;

    private int mVideoWidth;
    private int mVideoHeight;

    public void setMediaPlayerProxy(IMediaPlayerProxy mediaPlayerProxy) {
        mMediaPlayerProxy = mediaPlayerProxy;
    }

    private IMediaPlayerProxy mMediaPlayerProxy;

    private IMediaPlayer mMediaPlayer;

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


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        if (mVideoWidth > 0 && mVideoHeight > 0) {

            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize;
                height = heightSpecSize;

                // for compatibility, we adjust size based on aspect ratio
                if ( mVideoWidth * height  < width * mVideoHeight ) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight;
                } else if ( mVideoWidth * height  > width * mVideoHeight ) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth;
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize;
                height = width * mVideoHeight / mVideoWidth;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize;
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize;
                width = height * mVideoWidth / mVideoHeight;
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize;
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = mVideoWidth;
                height = mVideoHeight;
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize;
                    width = height * mVideoWidth / mVideoHeight;
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize;
                    height = width * mVideoHeight / mVideoWidth;
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }
        setMeasuredDimension(width, height);
    }


    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {

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
        mVideoPath = path;
    }

    private void openVideo() {
        if (mVideoPath == null) {
            return;
        }
        release();
        mMediaPlayer = mMediaPlayerProxy.newInstance();
        mMediaPlayer.setScreenOnWhilePlaying(true);
        mMediaPlayer.setDisplay(mSurfaceHolder);
        mMediaPlayer.setLogEnabled(BuildConfig.DEBUG);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnVideoSizeChangeListener(this);
        mMediaPlayer.enableMediaCodec();

        try {
            mMediaPlayer.setDataSource(mVideoPath);

            LogUtils.getInstance().init(getContext());
            LogUtils.getInstance().onStartPrepare();
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        LogUtils.getInstance().onEndPrepare();
        iMediaPlayer.start();
//        LogUtils.getInstance().start();
        LogUtils.getInstance().startForDuration(5);
    }



    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        LogUtils.getInstance().stop();
    }


    private void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer) {
        return false;
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer) {
        return false;
    }

    @Override
    public void onVideoSizeChange(IMediaPlayer iMediaPlayer, int width, int height) {
        if (width != 0 && height != 0) {
            mVideoWidth = width;
            mVideoHeight = height;
            getHolder().setFixedSize(width, height);
            requestLayout();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }
}
