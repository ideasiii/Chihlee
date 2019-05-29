package org.iii.chihlee;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.iii.more.common.Logs;

public class ActivitySetting extends Activity
{
    private EditText editTextIP = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.buttonSave).setOnClickListener(onClick);
        editTextIP = (EditText)findViewById(R.id.editText_ip);
        editTextIP.setText(ConfigHandler.restoreIP(this));
    }
    
    private View.OnClickListener onClick = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int nId = v.getId();
            switch(nId)
            {
                case R.id.buttonSave:
                    ConfigHandler.saveIP(editTextIP.getText().toString(),ActivitySetting.this);
                    finish();
                    break;
            }
        }
    };
}
