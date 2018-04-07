package com.example.aman.pccontrol.Server;

import android.app.Activity;
import android.widget.Toast;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by aman on 3/4/18.
 */

public class Server {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static Activity activity;

    public Server(Activity activity) {
        this.activity = activity;
    }

    public void startServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch(Exception e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "Unable to start server", Toast.LENGTH_LONG).show();
                }
            });
            e.printStackTrace();
            return;
        }
        try {
            clientSocket = serverSocket.accept();
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            objectOutputStream = new ObjectOutputStream(outputStream);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void sendMessageToServer(long message) {
        if (clientSocket != null) {
            try {
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
                socketException();
            }
        }
    }

    private static void socketException() {
        Toast.makeText(activity, "Connection Closed", Toast.LENGTH_LONG).show();
        if (clientSocket != null) {
            try {
                clientSocket.close();
                objectOutputStream.close();
                clientSocket = null;
            } catch(Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
