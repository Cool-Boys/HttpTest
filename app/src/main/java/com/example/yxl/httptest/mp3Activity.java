package com.example.yxl.httptest;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class mp3Activity extends AppCompatActivity {
private String currentFilePath="";
    private  String currentTempFilePath="";
    private String strVideoURL="";

    private TextView mTextView01;
    private MediaPlayer mMediaPlayer01;
    private ImageButton mPlay, mReset, mPause, mStop;
    private boolean bIsReleased = false;
    private boolean bIsPaused = false;
    private static final String TAG = "Hippo_URL_MP3_Player";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
        strVideoURL="http://www.languangav.com/soft/media/vocal.mp3";
        mTextView01= (TextView) findViewById(R.id.myTextView1);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mPlay= (ImageButton) findViewById(R.id.play);
        mReset= (ImageButton) findViewById(R.id.reset);
        mPause= (ImageButton) findViewById(R.id.pause);
        mStop= (ImageButton) findViewById(R.id.stop);


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(strVideoURL);
            }
        });
        /*暂停*/
        mReset.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                if(bIsReleased == false)
                {
                    if (mMediaPlayer01 != null)
                    {
                        mMediaPlayer01.seekTo(0);
                        mTextView01.setText(R.string.str_play);
                    }
                }
            }
        });

    /* 重播*/
        mPause.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                if (mMediaPlayer01 != null)
                {
                    if(bIsReleased == false)
                    {
                        if(bIsPaused==false)
                        {
                            mMediaPlayer01.pause();
                            bIsPaused = true;
                            mTextView01.setText(R.string.str_pause);
                        }
                        else if(bIsPaused==true)
                        {
                            mMediaPlayer01.start();
                            bIsPaused = false;
                            mTextView01.setText(R.string.str_play);
                        }
                    }
                }
            }
        });

    /*停止 */
        mStop.setOnClickListener(new ImageButton.OnClickListener()
        {
            public void onClick(View view)
            {
                try
                {
                    if (mMediaPlayer01 != null)
                    {
                        if(bIsReleased==false)
                        {
                            mMediaPlayer01.seekTo(0);
                            mMediaPlayer01.pause();
                            //mMediaPlayer01.stop();
                            //mMediaPlayer01.release();
                            //bIsReleased = true;
                            mTextView01.setText(R.string.str_stop);
                        }
                    }
                }
                catch(Exception e)
                {
                    mTextView01.setText(e.toString());
                    Log.e(TAG, e.toString());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        try
        {
            delFile(currentTempFilePath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        super.onPause();
    }


    private void playVideo(final String strPath)
    {
        try
        {
            if (strPath.equals(currentFilePath)&& mMediaPlayer01 != null)
            {
                mMediaPlayer01.start();
                return;
            }

            currentFilePath = strPath;

            mMediaPlayer01 = new MediaPlayer();
            mMediaPlayer01.setAudioStreamType(2);

            mMediaPlayer01.setOnErrorListener(new MediaPlayer.OnErrorListener()
            {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra)
                {
                    //TODO Auto-generated method stub
                    Log.i(TAG, "Error on Listener, what: " + what + "extra: " + extra);
                    return false;
                }
            });

            mMediaPlayer01.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener()
            {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent)
                {
                    //TODO Auto-generated method stub
                    Log.i(TAG, "Update buffer: " + Integer.toString(percent)+ "%");
                }
            });

            mMediaPlayer01.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mp)
                {
                    //TODO Auto-generated method stub
                    //delFile(currentTempFilePath);
                    Log.i(TAG,"mMediaPlayer01 Listener Completed");
                }
            });

            mMediaPlayer01.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
            {
                @Override
                public void onPrepared(MediaPlayer mp)
                {
                    //TODO Auto-generated method stub
                    Log.i(TAG,"Prepared Listener");
                }
            });

            Runnable r = new Runnable()
            {
                public void run()
                {
                    try
                    {
                        setDataSource(strPath);
                        mMediaPlayer01.prepare();
                        Log.i(TAG, "Duration: " + mMediaPlayer01.getDuration());

                        mMediaPlayer01.start();
                        bIsReleased = false;
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            };
            new Thread(r).start();
        }
        catch(Exception e)
        {
            if (mMediaPlayer01 != null)
            {
                mMediaPlayer01.stop();
                mMediaPlayer01.release();
            }
            e.printStackTrace();
        }
    }

    private void setDataSource(String strPath) throws Exception
    {
    /* 耞肚琌URL */
        if (!URLUtil.isNetworkUrl(strPath))
        {
            mMediaPlayer01.setDataSource(strPath);
        }
        else
        {
            if(bIsReleased == false)
            {
                URL myURL = new URL(strPath);
                URLConnection conn = myURL.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                if (is == null)
                {
                    throw new RuntimeException("stream is null");
                }

                File myTempFile = File.createTempFile("yinyue", "."+getFileExtension(strPath));
                currentTempFilePath = myTempFile.getAbsolutePath();



                FileOutputStream fos = new FileOutputStream(myTempFile);
                byte buf[] = new byte[128];
                do
                {
                    int numread = is.read(buf);
                    if (numread <= 0)
                    {
                        break;
                    }
                    fos.write(buf, 0, numread);
                }while (true);

        /* fos纗Ч拨㊣MediaPlayer.setDataSource */
                mMediaPlayer01.setDataSource(currentTempFilePath);
                try
                {
                    is.close();
                }
                catch (Exception ex)
                {
                    Log.e(TAG, "error: " + ex.getMessage(), ex);
                }
            }
        }
    }

    private String getFileExtension(String strFileName)
    {
        File myFile = new File(strFileName);
        String strFileExtension=myFile.getName();
        strFileExtension=(strFileExtension.substring(strFileExtension.lastIndexOf(".")+1)).toLowerCase();
        if(strFileExtension=="")
        {
      /* 璝礚猭抖眔捌郎箇砞.dat */
            strFileExtension = "dat";
        }
        return strFileExtension;
    }

    /* 瞒秨祘Α惠㊣璹ㄧ计埃既贾郎 */
    private void delFile(String strFileName)
    {
        File myFile = new File(strFileName);
        if(myFile.exists())
        {
            myFile.delete();
        }
    }
}
