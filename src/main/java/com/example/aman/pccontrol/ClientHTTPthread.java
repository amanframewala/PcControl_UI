package com.example.aman.pccontrol;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by aman on 28/3/18.
 */


public class ClientHTTPthread extends Thread {
    static final int HttpPORT = 5000;
    Socket socket;
    String ip_address,port_number;
    String listen_msg;
    String send_msg = "Hello";
    Boolean change = false;
    int command = 1234;

    public void setChange(Boolean change) {
        this.change = change;
    }

    public String getSend_msg() {
        return send_msg;
    }

    public void setSend_msg(String send_msg) {
        this.send_msg = send_msg;

    }

    public String getListen_msg() {
        return listen_msg;
    }


    public void setter(String ip_address,String port_number) {

        this.ip_address = ip_address;
        this.port_number = port_number;
    }

    public String getIp_address() {
        return ip_address;
    }

    public String getPort_number() {
        return port_number;
    }

    @Override
    public void run() {

        try {
            socket = new Socket(ip_address, Integer.parseInt(port_number));
            if (socket != null) {
                Log.d("Connection", "run: Success");
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(send_msg);
                out.flush();
                out.close();
                }
                /*while (true) {
                    BufferedReader inp = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    this.listen_msg = inp.readLine();

                }
*/
            } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

