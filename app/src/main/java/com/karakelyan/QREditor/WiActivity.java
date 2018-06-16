package com.karakelyan.QREditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class WiActivity extends AppCompatActivity {

    //massive of the string values which represents types of WiFi encryption
    String[] nettype ={"No encryption", "WEP", "WPA/WPA2"};
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi);
        //spinner bellow is for choosing type of encryption of the WiFi connection
        Spinner spinner = findViewById(R.id.nettype);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nettype);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setPrompt("Select network type");

        spinner.setAdapter(adapter);

        OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 type = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }
    public void onClickQR (View view){
        EditText edSsid= findViewById(R.id.sid);
        EditText edPass= findViewById(R.id.pass);
        String s;

        if(type=="No encryption")
        {
            s="WIFI:S:"+edSsid.getText().toString()+";P:"+edPass.getText().toString()+";;";
        }
        else
            {
                s="WIFI:S:"+edSsid.getText().toString()+
                ";T:"+type+
                ";P:"+edPass.getText().toString()+";;";
            }

        if (s.length() > 12) {
            Intent intent = new Intent(this, SuccessActivity.class);
            intent.putExtra("data", s);
            startActivity(intent);
        } else {
            Toast.makeText(this, "There is nothing to code.", Toast.LENGTH_SHORT).show();
        }
    }

}
