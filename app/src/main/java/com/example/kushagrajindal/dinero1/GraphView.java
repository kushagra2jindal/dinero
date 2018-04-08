package com.example.kushagrajindal.dinero1;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class GraphView extends AppCompatActivity {
    private GestureDetectorCompat gestureDetectorCompat;
    Button b;
    DatabaseHelper myDb;
    float gro=0,bills=0,entertainment=0,food=0,fuel=0,health=0,emi=0,invertment=0,other=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);
        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener1());
        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getAllData();
        while (res.moveToNext()){

            String category=res.getString(1);
            String amount=res.getString(2);
           // showMessage("dsff",category+" "+amount);

            if(category.equalsIgnoreCase("Groceries")){
                gro+=Double.parseDouble(amount);
               // showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("Bills")){
                bills+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("Entertainment")){
                entertainment+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("Food")){
                food+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("Fuel")){
                fuel+=Double.parseDouble(amount);
                //showMessage("fds",Double.toString(fuel));
            }
            else if(category.equalsIgnoreCase("Health")){
                health+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("EMI")){
                emi+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("Investment")){
                invertment+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }
            else if(category.equalsIgnoreCase("Others")){
                other+=Double.parseDouble(amount);
                //showMessage("fds",Float.toString(gro));
            }

        }
        StringBuffer buffer=new StringBuffer();
        buffer.append("gloceries:-"+Float.toString(gro)+" ");
        buffer.append("Bills:-"+Float.toString(bills)+" ");
        buffer.append("entertainment:-"+Float.toString(entertainment)+" ");
        buffer.append("food and drink:-"+Float.toString(food)+" ");
        buffer.append("fuel:-"+Float.toString(fuel)+" ");
        buffer.append("health:-"+Float.toString(health)+" ");
        buffer.append("EMI:-"+Float.toString(emi)+" ");
        buffer.append("Investment:-"+Float.toString(invertment)+" ");
        buffer.append("Others:-"+Float.toString(other)+" ");
        showMessage("abc",new String(buffer));


        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Gl");
        labels.add("B");
        labels.add("Ent");
        labels.add("Fd");
        labels.add("Fl");
        labels.add("Hth");
        labels.add("EMI");
        labels.add("Inv");
        labels.add("O");

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(gro, 0));
        entries.add(new BarEntry(bills, 1));
        entries.add(new BarEntry(entertainment, 2));
        entries.add(new BarEntry(food, 3));
        entries.add(new BarEntry(fuel, 4));
        entries.add(new BarEntry(health, 5));
        entries.add(new BarEntry(emi, 6));
        entries.add(new BarEntry(invertment, 7));
        entries.add(new BarEntry(other, 8));


        BarDataSet bardataset = new BarDataSet(entries, "");
        BarData data = new BarData(labels, bardataset);
        barChart.setData(data);
        barChart.setDescription("Set Bar Chart Description");
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateX(5000);


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

    class MyGestureListener1 extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() < event1.getX()) {
                Intent intent = new Intent(GraphView.this, AddCash.class);
                startActivity(intent);
            }

            return true;
        }
    }

}
