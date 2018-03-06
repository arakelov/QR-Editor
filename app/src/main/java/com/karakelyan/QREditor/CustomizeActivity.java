package com.karakelyan.QREditor;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.graphics.Bitmap.createScaledBitmap;


public class CustomizeActivity extends AppCompatActivity {
    public  Drawable[] layers;
    ImageView imageView;
    Button btn;
    Button btn1;
    Button btn2;
    Button photobtn;
    Button frontcl;
    Button backcl;
    Button btnSave;
    Button btnShare;
    public Drawable firstLayer;
    public Bitmap qrLayer;
    public int FRONT = 0x00FFFFFF;
    public int BACK = 0xFFFFFFFF;
    public int START=0xFF000000;
    public int END=0xFF000000;
    public String s;
    public int lvl;
    private static final int REQUEST_PERMISSION_WRITE = 11;
    private static final int REQUEST_PERMISSION_READ=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        imageView=findViewById(R.id.imgv);
        setGrad();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
             s = extras.getString("data");
            lvl = extras.getInt("lvl");
            qrLayer=new QRcreate().qrBitmap(FRONT, BACK, lvl, s);
            createImage();
        }
        btn=findViewById(R.id.startcl);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Context context = CustomizeActivity.this;

                ColorPickerDialogBuilder

                        .with(context)
                        .setTitle("Pick color")
                        .setColorEditTextColor(0xff000000)
                        .initialColor(0xffffffff)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(24)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                            }
                        })

                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                gradInitSt(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .build()
                        .show();

            }

        });
        //--------------------------------------------------------------------------------------------------
        btn1=findViewById(R.id.endcl);

        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Context context = CustomizeActivity.this;

                ColorPickerDialogBuilder

                        .with(context)
                        .setTitle("Pick color")
                        .setColorEditTextColor(0xff000000)
                        .initialColor(0xffffffff)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(24)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                //toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })

                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                gradInitEn(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .build()
                        .show();

            }
        });
        //-----------------------------------------------------------------------------------------------
        btn2=findViewById(R.id.refr);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            setGrad();
                        FRONT = 0x00FFFFFF;
                        qrLayer=new QRcreate().qrBitmap(FRONT, BACK, lvl, s);
                            createImage();
                    }
                });

        //--------------------------------------------------------------------------------------------------



        //-------------------------------------------------------------------------------------------------
        frontcl=findViewById(R.id.frontcl);

        frontcl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Context context = CustomizeActivity.this;

                ColorPickerDialogBuilder

                        .with(context)
                        .setTitle("Pick color")
                        .setColorEditTextColor(0xff000000)
                        .initialColor(0xffffffff)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(24)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                            }
                        })

                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                changeFrontColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .build()
                        .show();

            }
        });
        //-------------------------------------------------------------------------------------------------
        backcl=findViewById(R.id.backcl);

        backcl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final Context context = CustomizeActivity.this;

                ColorPickerDialogBuilder

                        .with(context)
                        .setTitle("Pick color")
                        .setColorEditTextColor(0xff000000)
                        .initialColor(0xffffffff)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(24)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                            }
                        })

                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                changeBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .showColorEdit(true)
                        .build()
                        .show();

            }
        });
        //-----------------------------------------------------------------------------------------------------------
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoSaveInit();
            }
        });
        //-----------------------------------------------------------------------------------------------------------
        btnShare=findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoShareInit();
            }
        });
        //-----------------------------------------------------------------------------------------------------------
        photobtn=findViewById(R.id.setphoto);
        photobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPickInit();
            }

        });
    }

    private void changeFrontColor(int selectedColor) {
        FRONT = selectedColor;
        qrLayer=new QRcreate().qrBitmap(FRONT, BACK, lvl, s);
        createImage();

    }
    private void changeBackgroundColor(int selectedColor) {
        BACK = selectedColor;
        qrLayer=new QRcreate().qrBitmap(FRONT, BACK, lvl, s);
        createImage();

    }
    public void photoPickInit()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            photoPick();
        }else{
            if (!isExternalStorageReadable())
            {Toast.makeText(this, "External storage is unavailable.", Toast.LENGTH_LONG).show();}
            else
            requestForReadPermission();
        }
    }

    public void photoSaveInit()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            photoSave();
        }else{
            if (!isExternalStorageWriteable())
            {Toast.makeText(this, "External storage is unavailable.", Toast.LENGTH_LONG).show();}
            else
            requestForWritePermission();
        }
    }

    public void photoShareInit(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            photoShare();
        }else{
            if (!isExternalStorageWriteable())
            {Toast.makeText(this, "External storage is unavailable.", Toast.LENGTH_LONG).show();}
            else
            requestForWritePermission();
        }
    }

    public void photoPick()
    {
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        photoIntent.setType("image/*");
        startActivityForResult(photoIntent, REQUEST_PERMISSION_READ);
    }

    private void requestForWritePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            Toast.makeText(this, "Memory access is required to save picture.",
                    Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
        } else {
            Toast.makeText(this,
                    "Permission is not available. Requesting memory permission.",
                    Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
        }
    }

    private void requestForReadPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Toast.makeText(this, "Memory access required to choose photo from gallery.",
                    Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_READ);
                }

         else {
            Toast.makeText(this,
                    "Permission is not available.",
                    Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=null;
        switch (requestCode)
        {
            case REQUEST_PERMISSION_READ:
                if(resultCode==RESULT_OK)
                {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    assert bitmap != null;
                    firstLayer=new BitmapDrawable(getResources(), createScaledBitmap (bitmap, QRcreate.WIDTH, QRcreate.HEIGHT, false));
                    createImage();
                }
                break;
        }
    }

    //this method unites 2 layers in one
    public void createImage()
    {
        Drawable d = new BitmapDrawable(getResources(), qrLayer);
        Drawable dd=firstLayer;
        layers= new Drawable[2];
        layers[0]=dd;
        layers[1]=d;
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        int i=QRcreate.count;
        layerDrawable.setLayerInset(0, i, i, i, i);
        imageView.setImageDrawable(layerDrawable);
    }


    private void gradInitSt(int clst){
        START=clst;
        int R=(clst>>16)&0xFF;
        int G=(clst>>8)&0xFF;
        int B=(clst)&0xFF;
        int A=255;
        btn.setBackgroundColor(START);
        clst=(A & 0xff)<< 24 | (B & 0xff) << 16 | (R & 0xff) << 8 | (G & 0xff);
        btn.setTextColor(clst);
    }

    private void gradInitEn(int clen){
        END=clen;
        int R=(clen>>16)&0xFF;
        int G=(clen>>8)&0xFF;
        int B=(clen)&0xFF;
        int A=255;
        btn1.setBackgroundColor(END);
        clen=(A & 0xff)<< 24 | (B & 0xff) << 16 | (R & 0xff) << 8 | (G & 0xff);
        btn1.setTextColor(clen);
    }

    //this method creates a gradient on the background layer using 2 colors which are contains in "START" and "END" parameters
    public void setGrad(){
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] { START, END });
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setSize(100, 100);
        firstLayer=drawable;
    }

    //this method is for saving ImageView content as a PNG file and sending it through the content provider to another applications
    public void photoShare(){
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = imageView.getDrawingCache();
        File file =saveBitmap(bitmap1);
        imageView.setDrawingCacheEnabled(false);
        Uri link= FileProvider.getUriForFile(this,"com.karakelyan.fileprovider", file);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/png");
        shareIntent.putExtra(Intent.EXTRA_STREAM, link);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch, myQR!");
        startActivity(Intent.createChooser(shareIntent, "Send email"));
    }

    //this method gets bitmap from ImageView and calling next method for saving bitmap as a picture file using getted bitmap as parametr
    public void photoSave(){
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = imageView.getDrawingCache();
        saveBitmap(bitmap1);
        imageView.setDrawingCacheEnabled(false);
    }

    //method for saving bitmap as a picture in PNG format and returns it as file
    private File saveBitmap(Bitmap bitmap) {

        File folder=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/myQR");
        if (!folder.exists())
        {folder.mkdir();}
        SimpleDateFormat sdf=new SimpleDateFormat("yyyymmsshhmmss");
        String date= sdf.format(new Date());
        String name= "Img"+date+".png";

        FileOutputStream fOut;
        File file = new File(folder, name);
        try {
            fOut=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            Toast.makeText(this, "File is saved", Toast.LENGTH_SHORT).show();
            refreshGal(folder);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //sending a signal to refresh gallery state
    public void refreshGal(File file)
    {
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }
    // check, if external storage is available for write
    public boolean isExternalStorageWriteable(){
        String state = Environment.getExternalStorageState();
        return  Environment.MEDIA_MOUNTED.equals(state);
    }
    // check, if external storage is available for read
    public boolean isExternalStorageReadable(){
        String state = Environment.getExternalStorageState();
        return  (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case REQUEST_PERMISSION_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    photoSave();
                } else {
                    Toast.makeText(this, "Need to grant permission", Toast.LENGTH_LONG).show();
                }
            }
                break;
            case REQUEST_PERMISSION_READ:{
                if (grantResults.length==1 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    photoPick();
                } else {
                    Toast.makeText(this, "Need to grant permission", Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }

}
