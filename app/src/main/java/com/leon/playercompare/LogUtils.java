package com.leon.playercompare;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.os.Process;
import android.util.Log;

import java.io.RandomAccessFile;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogUtils {

    private static final String TAG = "LogUtils";

    private static LogUtils instance;

    private long startPrepare;

    private long endPrepare;

    private float minCpu = 100;
    private float maxCpu = 0;
    private float avgCpu = 0;

    private float minMem = 100;
    private float maxMem = 0;
    private float avgMem = 0;

    private int count = 0;

    private ScheduledThreadPoolExecutor scheduler;
    private ActivityManager activityManager;

    private long lastCpuTime;
    private long lastAppCpuTime;

    private RandomAccessFile procStatFile;
    private RandomAccessFile appStatFile;

    private Handler mHandler;

    private OnUpdateLogListener mUpdateLogListener;

    private LogUtils(){
        mHandler = new Handler();
    }

    public static LogUtils getInstance() {
        if (instance == null) {
            synchronized (LogUtils.class) {
                if (instance == null) {
                    instance = new LogUtils();
                }
            }
        }
        return instance;
    }


    public void onStartPrepare() {
        startPrepare = System.currentTimeMillis();
        String msg = "onStartPrepare: " + startPrepare;
        log(msg);
    }



    public void onEndPrepare() {
        endPrepare = System.currentTimeMillis();
        String endPrepareMsg = "onEndPrepare: " + endPrepare;
        log(endPrepareMsg);
        String firstOpenMsg = "首开时间: endPrepare-startPrepare = " + (endPrepare - startPrepare);
        log(firstOpenMsg);

    }

    private class SampleTask implements Runnable {

        @Override
        public void run() {
            float cpu = sampleCPU();
            float mem = sampleMemory();

            if (cpu < minCpu && cpu != 0) {
                minCpu = cpu;
            }
            if (cpu > maxCpu) {
                maxCpu = cpu;
            }

            if (mem < minMem && mem != 0) {
                minMem = mem;
            }

            if (mem > maxMem) {
                maxMem = mem;
            }

            count ++;
            avgCpu = (avgCpu * (count -1) + cpu) / count;
            avgMem = (avgMem * (count - 1) + mem) / count;

            String sampleMsg = "CPU: " + cpu + "%" + " Memory: " + mem + "MB";
            log(sampleMsg);

        }
    }

    // freq为采样周期
    public void init(Context context) {
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        scheduler = new ScheduledThreadPoolExecutor(1);
    }

    public void start() {
        scheduler.scheduleWithFixedDelay(new SampleTask(), 0L, 1000L, TimeUnit.MILLISECONDS);
    }

    public void startForDuration(long minutes) {
        start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }, minutes * 60 * 1000);
    }

    public void stop() {
        scheduler.shutdown();
        String stopMsg = "stop: minCpu: " + minCpu + " maxCpu: " + maxCpu + " avgCpu:" + avgCpu
                + " minMem: " + minMem + " maxMem: " + maxMem + " avgMem: " + avgMem;
        log(stopMsg);
    }

    private float sampleCPU() {
        long cpuTime;
        long appTime;
        float sampleValue = 0;
        try {
            if (procStatFile == null || appStatFile == null) {
                procStatFile = new RandomAccessFile("/proc/stat", "r");
                appStatFile = new RandomAccessFile("/proc/" + Process.myPid() + "/stat", "r");
            } else {
                procStatFile.seek(0L);
                appStatFile.seek(0L);
            }
            String procStatString = procStatFile.readLine();
            String appStatString = appStatFile.readLine();
            String procStats[] = procStatString.split(" ");
            String appStats[] = appStatString.split(" ");
            cpuTime = Long.parseLong(procStats[2]) + Long.parseLong(procStats[3])
                    + Long.parseLong(procStats[4]) + Long.parseLong(procStats[5])
                    + Long.parseLong(procStats[6]) + Long.parseLong(procStats[7])
                    + Long.parseLong(procStats[8]);

            appTime = Long.parseLong(appStats[13])
                    + Long.parseLong(appStats[14])
                    + Long.parseLong(appStats[15])
                    + Long.parseLong(appStats[16]);
            if (lastCpuTime == 0 && lastAppCpuTime == 0) {
                lastCpuTime = cpuTime;
                lastAppCpuTime = appTime;
                return sampleValue;
            }
            sampleValue = ((appTime - lastAppCpuTime) * 1.0f/ (cpuTime - lastCpuTime) * 100);
            lastCpuTime = cpuTime;
            lastAppCpuTime = appTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sampleValue;
    }

    private float sampleMemory() {
        float mem = 0;
        try {
            final Debug.MemoryInfo[] memInfo = activityManager.getProcessMemoryInfo(new int[]{Process.myPid()});
            return memInfo[0].getTotalPss() * 1.0f/ 1024;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mem;
    }

    public interface OnUpdateLogListener {

        void onUpdate(long timestamp, String msg);
    }


    public void setOnUpdateLogListener(OnUpdateLogListener updateLogListener) {
        mUpdateLogListener = updateLogListener;
    }

    private void log(String msg) {
        Log.i(TAG, msg);
        if (mUpdateLogListener != null) {
            mUpdateLogListener.onUpdate(System.currentTimeMillis(), msg);
        }
    }
}
