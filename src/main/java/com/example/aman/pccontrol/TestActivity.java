package com.example.aman.pccontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    private Button mDone;
    private String IP,Port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        IP = getIntent().getStringExtra("IP");
        Port = getIntent().getStringExtra("port");
        mDone =(Button) findViewById(R.id.doneButton1);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientHTTPthread test = new ClientHTTPthread();
                test.setter(IP,Port);
                test.setSend_msg("LEFT_CLICK");
                test.start();
            }
        });

    }
    public void showToast(String msg){
        Toast toast = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
        toast.show();
    }
}
