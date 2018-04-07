package com.example.aman.pccontrol.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aman.pccontrol.MainActivity;
import com.example.aman.pccontrol.R;
import com.example.aman.pccontrol.SendMessageToServer;
import com.example.aman.pccontrol.Server.Server;

import java.net.Socket;

/**
 * Created by aman on 3/4/18.
 */

public class ConnectFragment extends Fragment {
    private Button connectButton, disconnectButton;
    private EditText ipAddressEditText, portNumberEditText;
    private SharedPreferences sharedPreferences;
    
    public ConnectFragment(){
    
        }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.fragment_connect, container, false);
            ipAddressEditText = (EditText) rootView.findViewById(R.id.ipAddress);
            portNumberEditText = (EditText) rootView.findViewById(R.id.portNumber);
            connectButton = (Button) rootView.findViewById(R.id.connectButton);
            disconnectButton = (Button)  rootView.findViewById(R.id.closeConnectButton);
            sharedPreferences = getActivity().getSharedPreferences("lastConnectionDetails", Context.MODE_PRIVATE);
            String lastConnectionDetails[] = getLastConnectionDetails();
            ipAddressEditText.setText(lastConnectionDetails[0]);
            portNumberEditText.setText(lastConnectionDetails[1]);
            if (MainActivity.clientSocket != null) {
                connectButton.setText("connected");
                disconnectButton.setEnabled(true);
                connectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
            }
            else {
                connectButton.setText("Connect");
                connectButton.setEnabled(true);

            }
            connectButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    makeConnection();
                }
            });
            disconnectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disconnect();
                }
            });
            return rootView;

        }

    private void disconnect() {
        MainActivity.sendMessageToServer("DISCONNECT");
        connectButton.setEnabled(true);
        connectButton.setText("Connect");
    }

    public void makeConnection() {
        String ipAddress = ipAddressEditText.getText().toString();
        String port = portNumberEditText.getText().toString();
            setLastConnectionDetails(new String[] {ipAddress, port});
            connectButton.setText("Connecting...");
            connectButton.setEnabled(false);
            Log.d("Connect fragment", "makeConnection: Making connection ");
            new MakeConnection(ipAddress, port, getActivity()) {
                @Override
                public void receiveData(Object result) {
                    Log.d("Receiving data", "receiveData: Has data been received ? ");
                    MainActivity.clientSocket = (Socket) result;
                    Log.d("Async", "receiveData: Socket");
                    if (MainActivity.clientSocket == null) {
                        Toast.makeText(getActivity(), "Server is not listening", Toast.LENGTH_SHORT).show();
                        connectButton.setText("connect");
                        connectButton.setEnabled(true);
                    } else {
                        connectButton.setText("connected");
                        new Thread(new Runnable() {
                            public void run() {
                                new Server(getActivity()).startServer(Integer.parseInt(port));
                            }
                        }).start();
                    }
                }
            }.execute();

    }

    private String[] getLastConnectionDetails() {
        String arr[] = new String[2];
        arr[0] = sharedPreferences.getString("lastConnectedIP", "");
        arr[1] = sharedPreferences.getString("lastConnectedPort", "3000");
        return arr;
    }
    private void setLastConnectionDetails(String arr[]) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastConnectedIP", arr[0]);
        editor.putString("lastConnectedPort", arr[1]);
        editor.apply();
    }
    public void onStart() {

        super.onStart();
    }


}
