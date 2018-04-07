package com.example.aman.pccontrol;

import android.os.AsyncTask;

/**
 * Created by varun on 28/9/17.
 */

public class SendMessageToServer extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... params) {
        String message = params[0];
        String code = params[1];

        // message may be int, float, long or string
        int intMessage;
        float floatMessage;
        long longMessage;
        System.out.println(message + ", " + code);
        if (code.equals("STRING")) {
            try {
                com.example.aman.pccontrol.MainActivity.objectOutputStream.writeObject(message);
                com.example.aman.pccontrol.MainActivity.objectOutputStream.flush();
            } catch(Exception e) {
                e.printStackTrace();
                com.example.aman.pccontrol.MainActivity.socketException();
            }
        } else if (code.equals("INT")) {
            try {
                intMessage = Integer.parseInt(message);
                com.example.aman.pccontrol.MainActivity.objectOutputStream.writeObject(intMessage);
                com.example.aman.pccontrol.MainActivity.objectOutputStream.flush();
            } catch(Exception e) {
                e.printStackTrace();
                com.example.aman.pccontrol.MainActivity.socketException();
            }
        } else if (code.equals("FLOAT")) {
            try {
                floatMessage = Float.parseFloat(message);
                com.example.aman.pccontrol.MainActivity.objectOutputStream.writeObject(floatMessage);
                MainActivity.objectOutputStream.flush();
            } catch(Exception e) {
                e.printStackTrace();
                MainActivity.socketException();
            }
        } else if (code.equals("LONG")) {
            try {
                longMessage = Long.parseLong(message);
                MainActivity.objectOutputStream.writeObject(longMessage);
                MainActivity.objectOutputStream.flush();
            } catch(Exception e) {
                e.printStackTrace();
                MainActivity.socketException();
            }
        }
        return null;
    }
}
