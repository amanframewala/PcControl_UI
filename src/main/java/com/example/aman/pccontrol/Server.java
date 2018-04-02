package com.example.aman.pccontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class Server extends AppCompatActivity {

    TextView infoIp ;
    TextView infoMsg, report ;

    String msg = "" ;
    ServerSocket serverSocket ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        //infoIp = (TextView) findViewById(R.id.infoip) ;
        //infoMsg = (TextView) findViewById(R.id.msg) ;
        infoIp.setText(getIP() + HttpThread.HttpPORT + "\n") ;

        HttpThread httpThread = new HttpThread() ;
        httpThread.start() ;
    }

    private String getIP() {
        String ip = "" ;

        try {
            Enumeration<NetworkInterface> enumNetInt = NetworkInterface.getNetworkInterfaces() ;

            while(enumNetInt.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetInt.nextElement() ;
                Enumeration<InetAddress> enumInetAddr = networkInterface.getInetAddresses() ;

                while(enumInetAddr.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddr.nextElement() ;

                    if(inetAddress.isSiteLocalAddress()) {
                        ip += "Site Local Address: " + inetAddress.getHostAddress() + "\n" ;
                    }
                }
            }
        } catch (SocketException se) {
            se.printStackTrace() ;
            ip += "Socket Exception " + se.toString() + "\n" ;
        }

        return ip ;
    }
    private class HttpThread extends Thread {
        static final int HttpPORT = 5000 ;

        @Override
        public void run() {
            Socket socket = null ;

            try {
                serverSocket = new ServerSocket(HttpPORT) ;
                int cnt = 0 ;

                while(true) {
                    cnt++ ;
                    socket = serverSocket.accept() ;

                    if(socket != null) {
                        Log.d("Report:", "run: Success");
                        try {
                            PrintWriter out = new PrintWriter(socket.getOutputStream());
                            out.println("Hi");
                            out.flush();
                        } catch (IOException e) {
                            Log.d("Transfer", "run: " + e);

                        }
                        BufferedReader inp = new BufferedReader(new InputStreamReader(socket.getInputStream())) ;
                        String string = inp.readLine() ;
                        Log.d("Message Received", "run: "+ string);
                    }


                    ResponseThread responseThread = new ResponseThread(socket, "Hello", cnt) ;
                    responseThread.start() ;
                }
            } catch (IOException ie) {
                ie.printStackTrace() ;
            }
        }
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
}
