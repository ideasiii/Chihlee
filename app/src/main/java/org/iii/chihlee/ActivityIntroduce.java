package org.iii.chihlee;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityIntroduce extends AppCompatActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        View backgroundimage = findViewById(R.id.rlMain);
        Drawable background = backgroundimage.getBackground();
        background.setAlpha(80);
    }
}
