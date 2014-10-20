//package oldserv;
//import java.io.BufferedReader;
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Date;
//
//import javax.bluetooth.*;
//
//import javax.bluetooth.LocalDevice;
//import javax.bluetooth.RemoteDevice;
//import javax.microedition.io.*;
//
//
//public class EchoServer {
//	private ServerSocket server;
//    private int port = 7777;
// 
//    public EchoServer() {
//        try {
//            server = new ServerSocket(port);
//            System.out.println(server.getInetAddress().getLocalHost().getHostAddress());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
// 
//    public static void main(String[] args) {
//        EchoServer example = new EchoServer();
//        example.handleConnection();
//    }
// 
//    public void handleConnection() {
//        System.out.println("Waiting for client message...");
// 
//        //
//        // The server do a loop here to accept all connection initiated by the
//        // client application.
//        //
//        while (true) {
//            try {
//                Socket socket = server.accept();
//                new ConnectionHandler(socket);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
// 
//class ConnectionHandler implements Runnable {
//    private Socket socket;
// 
//    public ConnectionHandler(Socket socket) {
//        this.socket = socket;
// 
//        Thread t = new Thread(this);
//        t.start();
//    }
// 
//    public void run() {
//        try
//        {
//            //
//            // Read a message sent by client application
//            //
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            String message = (String) ois.readObject();
//            System.out.println("Message Received: " + message);
//            
// 
//            //
//            // Send a response information to the client application
//            //
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject("Hi...");
//            
//            
//            oos.close();
//            ois.close();
//            socket.close();
// 
//            System.out.println("Waiting for client message...");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
