package com.example.kushagrajindal.dinero1;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    String s="";
    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "androidHive";
    private Cipher cipher;
    private TextView textView;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
           // Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }

        Button b1=(Button)findViewById(R.id.button10);
        Button b2=(Button)findViewById(R.id.button13);
        Button b3=(Button)findViewById(R.id.button14);
        Button b4=(Button)findViewById(R.id.button);
        Button b5=(Button)findViewById(R.id.button2);
        Button b6=(Button)findViewById(R.id.button3);
        Button b7=(Button)findViewById(R.id.button4);
        Button b8=(Button)findViewById(R.id.button5);
        Button b9=(Button)findViewById(R.id.button6);
        Button b0=(Button)findViewById(R.id.button8);
        Button bb=(Button)findViewById(R.id.button7);
        Button bo=(Button)findViewById(R.id.button9);

        Button forget=(Button)findViewById(R.id.button15);
        Button reset=(Button)findViewById(R.id.button11);

        final TextView t=(TextView)findViewById(R.id.textView);



        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"1";
                t.setText(s);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"2";
                t.setText(s);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"3";
                t.setText(s);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"4";
                t.setText(s);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"5";
                t.setText(s);
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"6";
                t.setText(s);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"7";
                t.setText(s);
            }
        });

        b8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"8";
                t.setText(s);
            }
        });

        b9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"9";
                t.setText(s);
            }
        });

        b0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s=s+"0";
                t.setText(s);
            }
        });

        bb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(s.length()!=0) {
                    s=s.substring(0,s.length()-1);
                    t.setText(s);
                }
                else{
                    t.setText("");
                }
            }
        });

        //final Intent i0=new Intent(this,MainOptions.class);
        bo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int READ_BLOCK_SIZE = 100;
                try {
                    FileInputStream fileIn=openFileInput("password.txt");
                    InputStreamReader InputRead= new InputStreamReader(fileIn);

                    char[] inputBuffer= new char[READ_BLOCK_SIZE];
                    String s="";
                    int charRead;

                    while ((charRead=InputRead.read(inputBuffer))>0) {
                        String readstring=String.copyValueOf(inputBuffer,0,charRead);
                        s +=readstring;
                    }
                    InputRead.close();
                        if(s.equals(t.getText())) {
                            //password correct give access
                            Intent i0=new Intent(MainActivity.this,AddCash.class);
                            startActivity(i0);
                            Toast.makeText(MainActivity.this, "password correct", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Password has not been set till now pls use forget pin", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

        });

        final Intent i=new Intent(this,ForgetPin.class);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);

            }
        });

        final Intent i1=new Intent(this,Reset.class);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i1);
            }
        });


        //fingure print authentication on main page

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        // Check whether the device has a Fingerprint sensor.
        if (!fingerprintManager.isHardwareDetected()) {
            //textView.setText("Your Device does not have a Fingerprint Sensor");
        } else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                //textView.setText("Fingerprint authentication permission not enabled");
            } else {
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    //textView.setText("Register at least one fingerprint in Settings");
                } else {
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                       // textView.setText("Lock screen security not enabled in Settings");
                    } else {
                        generateKey();


                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintHandler helper = new FingerprintHandler(this);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }


    }

   @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");

                // TextView tv = (TextView) findViewById(R.id.txtview);
                //tv.setText(message);
            }
        }
    };

    private  boolean checkAndRequestPermissions() {
        //Toast.makeText(this, "function called", Toast.LENGTH_SHORT).show();
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            //Toast.makeText(this, "returned false", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Toast.makeText(this, "return true", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
