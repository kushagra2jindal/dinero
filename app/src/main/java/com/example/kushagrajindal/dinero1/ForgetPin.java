package com.example.kushagrajindal.dinero1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class ForgetPin extends AppCompatActivity {
    Context context;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pin);
        context =this;

        final EditText phno=(EditText)findViewById(R.id.editText);
        Button button =(Button)findViewById(R.id.button16);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }else{}

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //already has permission granted
                if(phno.getText().toString().length()!=10){
                    Toast.makeText(context, "Enter a valid number of 10 digits", Toast.LENGTH_SHORT).show();
                    phno.setText("");
                }
                else {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        String randomPin = "" + ((int) (Math.random() * 9000) + 1000);
                        //Toast.makeText(context, randomPin, Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(phno.getText().toString(), null, randomPin, null, null);
                        try {
                            FileOutputStream fileout = openFileOutput("password.txt", MODE_PRIVATE);
                            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                            outputWriter.write(randomPin);
                            outputWriter.close();
                            //Toast.makeText(context, "succesfully written", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "Invalid number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}
