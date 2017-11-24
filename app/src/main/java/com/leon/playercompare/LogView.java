package com.leon.playercompare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class LogView extends RelativeLayout {


    public LogView(@NonNull Context context) {
        this(context, null);
    }

    public LogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_log, this);


        final TextView textView = findViewById(R.id.tv);
        final ScrollView scrollView = findViewById(R.id.scroll_view);

        final StringBuilder stringBuilder = new StringBuilder();
        LogUtils.getInstance().setOnUpdateLogListener(new LogUtils.OnUpdateLogListener() {
            @Override
            public void onUpdate(final long timestamp, final String msg) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        String dateString = DateUtils.formatDateTime(getContext(), timestamp, DateUtils.FORMAT_SHOW_TIME);
                        String log = dateString + ": " + msg + "\n";
                        stringBuilder.append(log);
                        textView.setText(stringBuilder.toString());
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });

            }
        });
    }

}
