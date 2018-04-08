package com.example.kushagrajindal.dinero1;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;


public class ViewDetails extends AppCompatActivity {
    private GestureDetectorCompat gestureDetectorCompat;
    Button b;
    ScrollView scrollview;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);

        scrollview = new ScrollView(this);

        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(LinearLayout.VERTICAL);
        scrollview.addView(linearlayout);

        Cursor res = myDb.getAllData();

        if (res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }


        while (res.moveToNext()) {
            StringBuffer buffer = new StringBuffer();

            final String id=res.getString(0);
            final String category=res.getString(1);
            final String amount=res.getString(2);
            final String date=res.getString(5);
            final String desc=res.getString(4);

            buffer.append("Id :" + id + "\n");
            buffer.append("Category :" + category + "\n");
            buffer.append("Amount :" + amount + "\n");
            buffer.append("Date and Time :" + date + "\n");
            LinearLayout linear1 = new LinearLayout(this);
            linear1.setOrientation(LinearLayout.HORIZONTAL);
            linearlayout.addView(linear1);
            b = new Button(this);
            b.setText(buffer);
            b.setId(Integer.parseInt(res.getString(0)));
            b.setTextSize(20);
            b.setPadding(8, 3, 8, 3);
            b.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
            b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linear1.addView(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title="DESCRIPTION OF ID NO:-"+id;
                    showMessage(title,desc);
                }
            });
        }
        this.setContentView(scrollview);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener0());
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener0 extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() > event1.getX()) {
                Intent intent = new Intent(ViewDetails.this, AddCash.class);
                startActivity(intent);
            }

            return true;
        }
    }
}
