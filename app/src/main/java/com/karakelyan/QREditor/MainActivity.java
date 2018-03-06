package com.karakelyan.QREditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void onClick (View view)

    {
        RadioGroup radioGroup=findViewById(R.id.radios);
        int btn = radioGroup.getCheckedRadioButtonId();

        switch (btn)
        {

            case R.id.wi:
                {
                    Intent intent= new Intent(this, WiActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.viz:
                {
                    Intent intent = new Intent(this, VizitkaActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.tele:
            {
                Intent intent = new Intent(this, TelefonActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.sms:
            {
                Intent intent = new Intent(this, SmsActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.url:
            {
                Intent intent = new Intent(this, UrlActivity.class);
                startActivity(intent);
            }
            break;

            case R.id.txt:
            {
                Intent intent = new Intent(this, TextActivity.class);
                startActivity(intent);
            }
            break;

           /* case R.id.cv:
            {
                Intent intent = new Intent(this, CeventActivity.class);
                startActivity(intent);
            }
            break;*/
        }
    }
}
