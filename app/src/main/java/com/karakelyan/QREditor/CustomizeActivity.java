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
    //massive of the layers
    public  Drawable[] layers;
    //square for placing a bitmap of the QR code
    ImageView imageView;
    //button for choosing first color of the gradient
    Button btn;
    //button for choosing second color of the gradient
    Button btn1;
    //button which will apply chosen colors in gradient
    Button btn2;
    //button for taking picture from gallery
    Button photobtn;
    //button for choosing color of the QR code
    Button frontcl;
    //button for choosing colore of the silent area
    Button backcl;
    //button for saving picture
    Button btnSave;
    //button for sending picture
    Button btnShare;
    //Layer which has bottom position
    public Drawable firstLayer;
    //Layer which has top position and presents a QR code
    public Bitmap qrLayer;
    //variable for ARGB color value which is present color of QR code
    public int FRONT = 0x00FFFFFF;
    //variable for ARGB color value which is present color of QR code silent area
    public int BACK = 0xFFFFFFFF;
    //variable for ARGB color value which is present first color of gradient
    public int START=0xFF000000;
    //variable for ARGB color value which is present second color of gradient
    public int END=0xFF000000;
    //variable which is content text to coding into QR code
    public String s;
    //variable which is contains an id of correction level
    public int lvl;
    //variable which is contains an id for request permission to write memory
    private static final int REQUEST_PERMISSION_WRITE = 11;
    //variable which is contains an id for request permission to read memory
    private static final int REQUEST_PERMISSION_READ=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
        imageView=findViewById(R.id.imgv);
        //on the start of activity set a gradient background with black color
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
        //open dialog for choosing first color for gradient
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
        //open a dialog for choosing second color for gradient
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
        //--------------------------------------------------------------------------------------------------
        //this is defenition for button which will call methods for creating gradient according with chosen colors and unite layers as one object
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

        //open a dialog for choosing color of QR code
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
        //open a dialog for choosing color of silent area
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

    //this method according with chosen color for background of layer which is contain QR code,
    // recreate QR code bitmap with new parameters and unite new created layer with another
    private void changeFrontColor(int selectedColor) {
        FRONT = selectedColor;
        qrLayer=new QRcreate().qrBitmap(FRONT, BACK, lvl, s);
        createImage();
    }

    //this method according with chosen color for background of layer which is contain QR code,
    // recreate QR code bitmap with new parameters and unite new created layer with another
    private void changeBackgroundColor(int selectedColor) {
        BACK = selectedColor;
        qrLayer=new QRcreate().qrBitmap(FRONT, BACK, lvl, s);
        createImage();
    }

    //this method checking if app has permission to read files from memory of the device,
    //if yes it will call method [photoPick()] for choosing picture from gallery.
    //if not it will call method for asking an user to grant this permission
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

    //this method checking if app has permission to save files in memory of the device,
    //if yes it will call method [photoSave()] for saving picture.
    //if not it will call method for asking an user to grant this permission
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

    //this method checking if app has permission to save files in memory of the device,
    //if yes it will call method [photoShare()] for sharing picture.
    //if not it will call method for asking an user to grant this permission
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

    //this method is calling Intent object to choose photo from gallery
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
            Toast.makeText(this, "Memory access required to choose photo from gallery.",
                    Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION_READ);
                }

         else {
            Toast.makeText(this,"Permission is not available.", Toast.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ);
        }
    }

    //this method define algorithm after choosing a photo by user.
    //converting bitmap to drawable object as layer and calling method [createImage()] to unite layers as one object
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

    //this method changing color of the first gradient button and text on it according with chosen color
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
    //this method changing color of the second gradient button and text on it according with chosen color
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
    //cheking results for request permissions
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
