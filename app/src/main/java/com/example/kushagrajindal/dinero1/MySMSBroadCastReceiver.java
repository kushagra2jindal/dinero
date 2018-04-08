package com.example.kushagrajindal.dinero1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by kushagrajindal on 05/01/18.
 */

public class MySMSBroadCastReceiver extends BroadcastReceiver {
    DatabaseHelper myDb;
    public void onReceive(Context context, Intent intent)
    {
        myDb=new DatabaseHelper(context);
        float amount=0;
        String cnd="";
        //Toast.makeText(context, "fghfhgf", Toast.LENGTH_SHORT).show();
        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str ="";
        //private String file = "mydata";
        if (bundle != null)
        {

            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i=0; i<smsm.length; i++){
                smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);

                sms_str += "\r\nMessage: ";
                sms_str += smsm[i].getMessageBody().toString();
                sms_str+= "\r\n";

                String Sender = smsm[i].getOriginatingAddress();

                if(sms_str.contains("credited") || sms_str.contains("credit") || sms_str.contains("deposit") || sms_str.contains("deposited") || sms_str.contains("added") || sms_str.contains("cashback")){
                    // message related to a transaction
                    amount=Float.parseFloat(getAmount(sms_str,context));
                    cnd="credit";
                    boolean isInserted = myDb.insertData("other",amount,cnd,sms_str,(5*amount)/100);
                    if(isInserted == true)
                        Toast.makeText(context,"Data Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context,"Data not Inserted",Toast.LENGTH_LONG).show();

                    Toast.makeText(context, sms_str, Toast.LENGTH_SHORT).show();
                }
                else if(sms_str.contains("debited") || sms_str.contains("debit") || sms_str.contains("withdraw") || sms_str.contains("withdrawn")){
                    amount=Float.parseFloat(getAmount(sms_str,context));
                    cnd="debit";
                    boolean isInserted = myDb.insertData("other",amount,cnd,sms_str,(5*amount)/100);
                    if(isInserted == true)
                        Toast.makeText(context,"Data Inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context,"Data not Inserted",Toast.LENGTH_LONG).show();

                    Toast.makeText(context, sms_str, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getAmount(String s,Context context){
        Toast.makeText(context, "called", Toast.LENGTH_SHORT).show();
        String s1="";
        if(s.indexOf("INR")!=-1){
            int p=s.indexOf("INR");
            p=p+4;
            while(s.charAt(p)!=' '){
                s1+=s.charAt(p);
                p++;
                if(p==s.length()){
                    break;
                }
            }
        }

        else if(s.indexOf("Rs")!=-1 ) {
            int p = s.indexOf("Rs");
            p = p + 3;
            while (s.charAt(p) != ' ') {
                s1 = s1 + s.charAt(p);
                p++;
                if (p == s.length()) {
                    break;
                }
            }
        }
        return s1.trim();
    }

}
