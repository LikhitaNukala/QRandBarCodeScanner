package com.example.android.myscannerapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    static final String ACTION_SCAN="com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void barcodeScan(View view){
        try{
            Intent intent=new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE","PRODUCT_MODE");
            startActivityForResult(intent,0);
        }catch(ActivityNotFoundException e){
            showDialog(MainActivity.this,"No scanner found","Download a scanner code activity?","Yes","No").show();
        }
    }



    public void QRScan(View view){
        Log.d("qr scan","this func is invoked");
        try{
            Intent intent=new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE","QR_CODE_MODE");
            startActivityForResult(intent,0);
        }catch (ActivityNotFoundException e){
            showDialog(MainActivity.this,"Scanner not found","Download a scanner activity","Yes","No").show();



        }
    }
    private AlertDialog showDialog(final Activity act, CharSequence title, CharSequence msg, CharSequence yes, CharSequence no) {
        AlertDialog.Builder downloadDialog=new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(msg);
        downloadDialog.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                try{
                    act.startActivity(intent);

                }catch (ActivityNotFoundException e){

                }
            }
        });
        downloadDialog.setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return downloadDialog.show();

    }
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if (requestCode==0){
            if(resultCode==RESULT_OK){
                String text=intent.getStringExtra("SCAN_RESULT");
                String format=intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast=Toast.makeText(this,"text: "+text+"Format :"+format,Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
