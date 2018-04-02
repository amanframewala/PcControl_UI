package com.example.aman.pccontrol;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AsyncTask<Void, Void, Void> {
    String dstAddress ;
    int dstPort ;
    String response = "" ;
    String otherText = "" ;
    String fileName = "" ;
    TextView textResponse, mTextView ;

    Client(String addr, int port, String fileName, TextView textResponse, TextView textView) {
        this.dstAddress = addr ;
        this.dstPort = port ;
        this.fileName = fileName ;
        this.textResponse = textResponse ;
        this.mTextView = textView ;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Socket socket = null ;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024) ;
        byte[] buffer = new byte[1024] ;

        int bytesRead ;

        Log.d("ClientApp", "Here 1") ;
        try {
            socket = new Socket(dstAddress, dstPort) ;
            if(socket != null) {
                otherText = "Successful Connection!" ;
            }

            int num = 3 ;
            Log.d("ClientApp", "Here 2") ;

            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true) ;
            outToServer.println(fileName) ;

            Log.d("ClientApp", "Here 3") ;
            InputStream inputStream = socket.getInputStream() ;
            Log.d("ClientApp", "Here 4") ;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)) ;
            String line ;

            while((line = bufferedReader.readLine()) != null) {
                Log.d("ClientApp", line) ;
                response += line ;
            }

            bufferedReader.close() ;
            outToServer.close() ;
            socket.close() ;
            /*
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead) ;
                response += byteArrayOutputStream.toString("UTF-8") ;
                Log.d("ClientApp", "Here " + num + " ") ;
                num++ ;
            }
            */
        } catch (UnknownHostException he) {
            he.printStackTrace() ;
            response = "Unknown Host: " + he.toString() ;
        } catch (IOException e) {
            e.printStackTrace() ;
            response = "IO Exception: " + e.toString() ;
        } catch (Exception e) {
            e.printStackTrace() ;
            response = "Exception: " + e.toString() ;
        }

        return null ;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        textResponse.setText(response) ;
        mTextView.setText(otherText) ;
        super.onPostExecute(aVoid) ;
    }
}
