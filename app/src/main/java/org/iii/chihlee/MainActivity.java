package org.iii.chihlee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private final int MSG_RUN_MAIN = 0;
    static final HashMap<Integer, Class<?>> mapActivity = new HashMap<Integer, Class<?>>()
    {{
        put(R.id.imageView_introduce, ActivityIntroduce.class);
        put(R.id.imageView_map, ActivityMap.class);
        put(R.id.imageView_chat, ActivityChat.class);
    }};
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
        handler.sendEmptyMessageDelayed(MSG_RUN_MAIN, 3000);
    }
    
    @Override
    protected void onPause()
    {
        handler.removeMessages(MSG_RUN_MAIN);
        super.onPause();
    }
    
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_RUN_MAIN:
                    handler.removeMessages(MSG_RUN_MAIN);
                    setContentView(R.layout.activity_main);
                    initMain();
                    break;
            }
        }
    };
    
    private void initMain()
    {
        for (int ImageViewId : mapActivity.keySet())
            findViewById(ImageViewId).setOnClickListener(viewOnClick);
    }
    
    private View.OnClickListener viewOnClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = null;
            intent = new Intent(MainActivity.this, mapActivity.get(v.getId()));
            startActivity(intent);
        }
    };
    
    
}
