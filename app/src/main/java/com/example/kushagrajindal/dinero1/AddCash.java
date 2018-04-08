package com.example.kushagrajindal.dinero1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;



public class AddCash extends AppCompatActivity {
    DatabaseHelper myDb;
    RadioButton rb;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash);
        myDb=new DatabaseHelper(this);

        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener2());

        final EditText e1=(EditText)findViewById(R.id.editText2);
        final EditText e2=(EditText)findViewById(R.id.editText3);
        final RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
        Button add=(Button)findViewById(R.id.button18);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount=e1.getText().toString();
                float amt=Float.parseFloat(amount);
                float gst=(5*amt)/100;
                String desc=e2.getText().toString();

                int selectedId = rg.getCheckedRadioButtonId();
                rb=(RadioButton)findViewById(selectedId);
                String category=rb.getText().toString();

                boolean isInserted = myDb.insertData(category,amt,"debit",desc,gst);
                if(isInserted == true)
                    Toast.makeText(AddCash.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddCash.this,"Data not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener2 extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() > event1.getX()) {
                Intent intent = new Intent(AddCash.this, GraphView.class);
                startActivity(intent);
            }

            else if (event2.getX() < event1.getX()) {
                Intent intent = new Intent(AddCash.this, ViewDetails.class);
                startActivity(intent);
            }

            return true;
        }
    }


}
