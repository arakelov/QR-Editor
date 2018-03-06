package com.karakelyan.QREditor;


import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class SuccessActivity extends AppCompatActivity {



    ImageView imageView;
    String[] ecc ={"Level L(7%)","Level M(15%)","Level Q(25%)","Level H(30%)"};
    String s_item;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        imageView= findViewById(R.id.imgv);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String s =extras.getString("data");
            Bitmap bitmap=new QRcreate().qrBitmap(FRONT, BACK, LEVEL, s);
            imageView.setImageBitmap(bitmap);
        }
        Spinner spinner=findViewById(R.id.ecc);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ecc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setPrompt("Select error correction level");

        spinner.setAdapter(adapter);

        OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s_item=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
//-----------------------------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------------------------

    }
    public void onClickChangeQR(View v) {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String s =extras.getString("data");
            if(s_item=="Level L(7%)")
            {LEVEL=7;}
            else if (s_item=="Level M(15%)")
            {LEVEL=15;}
            else if(s_item=="Level Q(25%)")
            {LEVEL=25;}
            else if(s_item=="Level H(30%)")
            {LEVEL=30;}
            Bitmap bitmap=new QRcreate().qrBitmap(FRONT, BACK, LEVEL, s);
            imageView.setImageBitmap(bitmap);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------
    public void onClickQR(View v) {
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            String s = extras.getString("data");
            Intent intent = new Intent(this, CustomizeActivity.class);
            intent.putExtra("data", s);
            intent.putExtra("lvl", LEVEL);
            startActivity(intent);
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------
    public int FRONT = 0xFF000000;
    public int BACK = 0xFFFFFFFF;
    public int LEVEL = 15;

}

