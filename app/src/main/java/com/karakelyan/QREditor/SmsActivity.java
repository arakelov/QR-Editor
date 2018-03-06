package com.karakelyan.QREditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    }

    public void onClickQR(View view)
    {
        EditText edPhone = findViewById(R.id.tel);
        EditText edMess =findViewById(R.id.message);

        String s="smsto:"+edPhone.getText().toString()+":"+edMess.getText().toString();

        if (s.length() > 7) {
            Intent intent = new Intent(this, SuccessActivity.class);
            intent.putExtra("data", s);
            startActivity(intent);
        } else {
            Toast.makeText(this, "There is nothing to code.", Toast.LENGTH_SHORT).show();
        }
    }
}
