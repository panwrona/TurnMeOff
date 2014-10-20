//package oldserv;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Scanner;
//
//
//public class EchoClient {
//
//	public static void main(String[] args) {
//        try {
//            //
//            // Create a connection to the server socket on the server application
//            //
//            InetAddress host = InetAddress.getLocalHost();
//            System.out.println(host.getHostAddress());
//            Socket socket = new Socket(host.getHostName(), 7777);
// 
//            //
//            // Send a message to the client application
//            //
//            Scanner scan = new Scanner(System.in);
//            String message = scan.next();
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject("Hello There...");
// 
//            //
//            // Read and display the response message sent by server application
//            //
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            String message2 = (String) ois.readObject();
//            System.out.println("Message: " + message2);
// 
//            ois.close();
//            oos.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
