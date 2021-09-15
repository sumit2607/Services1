package com.sumit.services1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

   private EditText mName;
private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intitview();
        IntentFilter intentFilter = new IntentFilter("com.sumit.services1");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void intitview() {
     mName = findViewById(R.id.name);
        Button mBtnSave = findViewById(R.id.button);
        mBtnSave.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FileOperationService.class);
            intent.putExtra("name" , mName.getText().toString());
            startService(intent);
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getStringExtra("data"), Toast.LENGTH_SHORT).show();

        }
    };
}