package com.pp.cloud.pingback;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by hanzhang on 2018/5/1.
 */

public class PingbackThreadPool {

    private  Context mContext;

    private static PingbackThreadPool mPingbackThreadPool;

    public  File mPingbackFile;

    public  BufferedReader mBufferedReader;


    public  BufferedWriter mBufferedWriter;

    private static ExecutorService mRealThreadPools = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread("PingbackThread");
        }
    });

    public  void init(Context context) {
        mContext = context;
        checkFile();
    }

    private  void checkFile() {
        if(mPingbackFile == null) {
            File path = new File(mContext.getExternalFilesDir(null).getAbsolutePath());
            if(!path.exists()) {
                path.mkdirs();
            }
            mPingbackFile = new File(path+"/pingback.txt");
            if(!mPingbackFile.exists()) {
                try {
                    mPingbackFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(mBufferedWriter == null) {
            try {
                mBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mPingbackFile,true)));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(mBufferedReader == null) {
            try {
                mBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(mPingbackFile)));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PingbackThreadPool getInstance(Context context) {
        if(mPingbackThreadPool == null) {
            synchronized (PingbackThreadPool.class) {
                if(mPingbackThreadPool == null) {
                    mPingbackThreadPool = new PingbackThreadPool(context);
                }
            }
        }
        return mPingbackThreadPool;
    }

    private PingbackThreadPool(Context context) {
            init(context);
    }

    public  void send(final String content) {
        if(mBufferedWriter != null) {
            try {
                mBufferedWriter.append(content);
                mBufferedWriter.flush();
                mBufferedWriter.newLine();
                Log.e("sendPingback content",content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void postAll() throws IOException {
        final List<String> contents = new ArrayList();
        if(mBufferedReader != null) {
            String s = null;
            s = mBufferedReader.readLine();
            while (s != null) {
                s = mBufferedReader.readLine();
                contents.add(s);
            }
            Log.e("PostPingback",s);
        }
        sendPingback(new Runnable() {
            @Override
            public void run() {
                for(String s :contents) {



                }
            }
        });
    }
    private  void sendPingback(Runnable task) {
        mRealThreadPools.submit(task);
    }
}
