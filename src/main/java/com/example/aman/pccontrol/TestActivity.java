package com.example.aman.pccontrol;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    TextView response, textView ;
    EditText editTextAddress, editTextPort, editFileName ;
    Button buttonConnect, buttonClear ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        editTextAddress = findViewById(R.id.textAddr) ;
        editTextPort = findViewById(R.id.textPort) ;
        buttonClear = findViewById(R.id.clear) ;
        buttonConnect = findViewById(R.id.connect) ;
        response = findViewById(R.id.response) ;
        textView = findViewById(R.id.textView) ;
        editFileName = findViewById(R.id.editFileName) ;

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client myClient = new Client(editTextAddress.getText().toString(),
                        Integer.parseInt(editTextPort.getText().toString()), editFileName.getText().toString(), response, textView) ;
                myClient.execute() ;
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }
}
