package org.iii.chihlee;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityMap extends AppCompatActivity
{
   
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        findViewById(R.id.imageViewMap).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Dialog dialog = new Dialog(ActivityMap.this, R.style.MyDialog);//指定自定義樣式
                dialog.setContentView(R.layout.dialog_map_guide);//指定自定義layout
                dialog.show();
            }
        });
    }
}
