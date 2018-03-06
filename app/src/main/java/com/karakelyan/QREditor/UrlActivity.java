package com.karakelyan.QREditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UrlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
    }

    public void onClickQR(View view)
    {
        EditText edUrl= findViewById(R.id.url);

        String s=edUrl.getText().toString();

        if (s.length() != 0) {
            Intent intent = new Intent(this, SuccessActivity.class);
            intent.putExtra("data", s);
            startActivity(intent);
        } else {
            Toast.makeText(this, "There is nothing to code.", Toast.LENGTH_SHORT).show();
        }
    }
}
