package io.panwrona.turnmeoff;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Mariusz on 15.10.14.
 */
public class IpGetter {

    private static final String TAG = "IpGetter";


    private static Future<String> portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
       Log.d("IpGetter", "In portIsOpen");
        return es.submit(new Callable<String>() {
            Socket socket;
            PrintWriter mOut;
            @Override public String call() {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    mOut.println("test");
                    mOut.flush();
                    Log.d("portIsOpen", "ip: " + ip);
                    return ip;
                } catch (Exception ex) {
                    return null;

                }finally{
                    try {
                        mOut.close();
                        socket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public static String getIp(){
        final ExecutorService es = Executors.newFixedThreadPool(20);

        final int timeout = 300;
        final int port = 4444;
        final List<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i <= 255; i++) {
            String ip = "192.168.1."+i;
            futures.add(portIsOpen(es, ip, port, timeout));
        }
        es.shutdown();

        for (final Future<String> f : futures) {
            try {
                if (f.get() != null) {
                    Log.d("F get", f.get());
                    return f.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
