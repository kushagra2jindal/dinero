package com.example.kushagrajindal.dinero1;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Reset extends AppCompatActivity {
    String s="";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        context=this;

        Button r=(Button)findViewById(R.id.button17);
        final EditText old=(EditText)findViewById(R.id.editText4);
        final EditText pin=(EditText)findViewById(R.id.editText5);
        final EditText confirm=(EditText)findViewById(R.id.editText6);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int READ_BLOCK_SIZE = 100;   //Read old password
                try {
                    FileInputStream fileIn=openFileInput("password.txt");
                    InputStreamReader InputRead= new InputStreamReader(fileIn);

                    char[] inputBuffer= new char[READ_BLOCK_SIZE];
                    int charRead;

                    while ((charRead=InputRead.read(inputBuffer))>0) {
                        String readstring=String.copyValueOf(inputBuffer,0,charRead);
                        s +=readstring;
                    }
                    InputRead.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(pin.getText().toString().equals(confirm.getText().toString())){
                    if(old.getText().toString().equals(s)){
                        try {
                            FileOutputStream fileout=openFileOutput("password.txt", MODE_PRIVATE);
                            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
                            String s1=pin.getText().toString();
                            outputWriter.write(s1);
                            outputWriter.close();
                            Toast.makeText(context, "succesfully made the changes", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(context, "pin not verified from old pin TRY FORGET PIN", Toast.LENGTH_SHORT).show();
                        old.setText("");
                    }
                }
                else{
                    Toast.makeText(context, "pin and confirm pin are not equal", Toast.LENGTH_SHORT).show();
                    pin.setText("");
                    confirm.setText("");
                }
            }
        });
    }

}
