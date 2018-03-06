package com.karakelyan.QREditor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VizitkaActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE_READ_CONTACTS=696;
    private static final int REQUEST_CODE_READ_CONTACTS=969;

    EditText edName;
    EditText edComp;
    EditText edTit;
    EditText edTel;
    EditText edUrl;
    EditText edMail;
    EditText edAdr;
    EditText edCit;
    EditText edPos;
    EditText edSt;
    EditText edNote;
    Button  addCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizitka);
        init();

        addCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermission())
                {
                    pickContact();
                }
                else
                {
                    requestPermissionWithRationale();
                }
            }
        });
        
    }
    public void init(){
        edName = findViewById(R.id.name);
        edComp = findViewById(R.id.org);
        edTit = findViewById(R.id.tit);
        edTel = findViewById(R.id.tel);
        edUrl = findViewById(R.id.site);
        edMail =findViewById(R.id.mail);
        edAdr =findViewById(R.id.adr);
        edCit =findViewById(R.id.city);
        edPos =findViewById(R.id.post);
        edSt =findViewById(R.id.stat);
        edNote =findViewById(R.id.note);
        addCont=findViewById(R.id.addcontact);
    }



    public void pickContact(){
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, REQUEST_CODE_READ_CONTACTS);
    }


    private boolean hasPermission()
    {
        int res;
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS};
        for (String perms : permissions)
        {
            res=checkCallingOrSelfPermission(perms);
            if(!(res==PackageManager.PERMISSION_GRANTED))
            {
                return false;
            }
        }
        return true;
    }

    private void requestPerms()
    {
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(permissions, PERMISSION_CODE_READ_CONTACTS);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]grantResults )
    {
        boolean allowed=true;
        switch (requestCode){
            case PERMISSION_CODE_READ_CONTACTS:
                for (int res: grantResults){
                    allowed=allowed&&(res == PackageManager.PERMISSION_GRANTED);
                }
                break;
                default:
                allowed = false;
                break;
        }
        if(allowed){
            Toast.makeText(this,"Permission granted", Toast.LENGTH_SHORT).show();
            pickContact();
        }
        else {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
                    Toast.makeText(this, "Read contacts permission denied.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "You need grant permissions to read contacts. You could do it in the settings of this application.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {
            final String message = "For this action need permission to your contacts";
            Snackbar.make(VizitkaActivity.this.findViewById(R.id.activity_view), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_CODE_READ_CONTACTS:
                    Cursor c=null;
                        Uri contactData = data.getData();
                        assert contactData != null;
                        c = getContentResolver().query(contactData, null, null, null, null);
                        try {
                            assert c != null;
                            if(c.moveToFirst()){
                            String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                            String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                        null, null);
                                assert phones != null;
                                phones.moveToFirst();
                                String number = phones.getString(phones.getColumnIndex("data1"));
                                edTel.setText(number);
                                phones.close();
                            }
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            edName.setText(name);
                            c.close();

                        }}
                        catch (Exception e)
                        {e.printStackTrace();}
                        break;
                    }}}


    public void onClickQR(View view)
    {
        String s;
        s="BEGIN:VCARD" +
                "\r\nVERSION:3.0"+
                "\r\nN:"+edName.getText().toString()+
                "\r\nORG:"+edComp.getText().toString()+
                "\r\nTITLE:"+edTit.getText().toString()+
                "\r\nTEL;WORK:"+edTel.getText().toString()+
                "\r\nURL:"+edUrl.getText().toString()+
                "\r\nEMAIL;WORK:"+ edMail.getText().toString()+
                "\r\nADR;WORK:;;"+edAdr.getText().toString()+";"+edCit.getText().toString()+";;"+edPos.getText().toString()+";"+edSt.getText().toString()+
                "\r\nNOTE:"+edNote.getText().toString()+
                "\r\nEND:VCARD";

        if (s.length() > 108) {
            Intent intent = new Intent(this, SuccessActivity.class);
            intent.putExtra("data", s);
            startActivity(intent);
        } else {
            Toast.makeText(this, "There is nothing to code.", Toast.LENGTH_SHORT).show();
        }
    }
}
