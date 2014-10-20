package io.panwrona.turnmeoff;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Mariusz on 09.10.14.
 */
public class TCPClient {

    private static final String TAG = "TCPClient";
    private final Handler mHandler;


    private String ipNumber;
    BufferedReader in;
    PrintWriter out;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    private String serverMessage,command;

    public TCPClient(Handler mHandler, String command, String ipNumber, OnMessageReceived listener) {
        mMessageListener = listener;
        this.ipNumber = ipNumber;
        this.command = command;
        this.mHandler = mHandler;
    }

    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
            mHandler.obtainMessage(MainActivity.SENDING);
            Log.d(TAG, "Sent Message: " + message);

        }
    }


    public void stopClient(){
        Log.d(TAG, "Client stopped!");
        mRun = false;

    }

    public void run() {

        mRun = true;


        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(ipNumber);

            Log.d(TAG, "C: Connecting...");

            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, 4444);


            try {

                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.d("TCP Client", "C: Sent.");

                Log.d("TCP Client", "C: Done.");

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.sendMessage(command);
                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    serverMessage = in.readLine();
                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);

                    }
                    serverMessage = null;

                }

                Log.d("TCP", "S: Received Message: '" + serverMessage + "'");

            } catch (Exception e) {

                Log.d("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                out.flush();
                out.close();
                in.close();
                socket.close();
                Log.d("TCP", "Socket Closed");
            }

        } catch (Exception e) {

            Log.d("TCP", "C: Error", e);

        }

    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}

