package com.example.aman.pccontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ToPCWithNav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText ipAddress, portNumber, mMessage;
    Button connect, message, mMan, mDog;
    final String[] ip_address = new String[1];
    final String[] port_num = new String[1];
    Socket clientSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_pcwith_nav);
        ipAddress = (EditText) findViewById(R.id.ipEt);
        portNumber = (EditText) findViewById(R.id.portEt);
        connect = (Button) findViewById(R.id.connectBt);
        message = (Button) findViewById(R.id.doneButton);
        final ClientHTTPthread httpThread = new ClientHTTPthread();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ToPCWithNav.this,TestActivity.class));
            }
        });
        /*connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ip_address[0] = ipAddress.getText().toString();
                port_num[0] = portNumber.getText().toString();
                httpThread.setter(ip_address[0],port_num[0]);
                httpThread.setSend_msg(mMessage.getText().toString());
                httpThread.start() ;

                connect.setEnabled(false);
                httpThread.setChange(true);
                if(httpThread.isInterrupted()) {
                    httpThread.notify();
                }
                if(httpThread.getListen_msg() != null){

                    connect.setText(httpThread.getListen_msg());
                }

                connect.setText("hey");
                Intent tezt = new Intent(ToPCWithNav.this,TestActivity.class);
                tezt.putExtra("IP",ip_address[0]);
                tezt.putExtra("port",port_num[0]);
                startActivity(tezt);

            }
        });*/

     /*   message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tezt = new Intent(ToPCWithNav.this,TestActivity.class);
                tezt.putExtra("IP",ip_address[0]);
                tezt.putExtra("port",port_num[0]);
                startActivity(tezt);
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private class ResponseThread extends Thread {
        Socket socket ;
        String msg ;
        int cnt ;

        ResponseThread(Socket socket, String msg, int cnt) {
            this.socket = socket ;
            this.msg = msg ;
            this.cnt = cnt ;
        }

        @Override
        public void run() {
            BufferedReader is ;
            PrintWriter os ;
            String request ;

            try {
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                request = is.readLine();
                System.out.println(request + " " + cnt) ;

                os = new PrintWriter(socket.getOutputStream(), true);

                String response = "<html><head></head>" + "<body>" + "<h1>" + msg + "</h1>" + "</body></html>";

                os.print("HTTP/1.0 200" + "\r\n");
                os.print("Content type: text/html" + "\r\n");
                os.print("Content length: " + response.length() + "\r\n");
                os.print("\r\n");
                os.print(response + "\r\n");
                os.flush();

                socket.close();
            } catch (IOException ie) {
                ie.printStackTrace() ;
            }

            return ;
        }
    }
    public void showToast(String msg){
        Toast toast = Toast.makeText(this,msg,Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_file_transfer) {
            // Handle the camera action
        } else if (id == R.id.nav_keyboard) {

        } else if (id == R.id.nav_mouse) {

        } else if (id == R.id.nav_presenter) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
