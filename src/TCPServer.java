import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
 
/**
 * The class extends the Thread class so we can receive and send messages at the same time
 */
public class TCPServer extends Thread {
 
    public static final int SERVERPORT = 4444;
    private boolean running = false;
    private PrintWriter mOut;
    private BufferedReader in;
    private MessageCallback messageListener;
    private Command shutdownCommand,restartCommand,hibernateCommand;
    private Switcher switcher;
    private Computer computer;
 
    public static void main(String[] args) {
    	
        //opens the window where the messages will be received and sent
        final MainBoard frame = new MainBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImages(getIcons());
        frame.setExtendedState(JFrame.ICONIFIED);
        frame.addWindowStateListener(new WindowStateListener(){
        	public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == JFrame.ICONIFIED) {
                    try {
                    	
                    	BufferedImage trayIconImage = ImageIO.read(getClass().getResource("./16.png"));
                    	int trayIconWidth = new TrayIcon(trayIconImage).getSize().width;
                    	final TrayIcon trayIcon = new TrayIcon(trayIconImage.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH));
                    	
                        trayIcon.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                frame.setVisible(true);
                                frame.setState(JFrame.NORMAL);
                                SystemTray.getSystemTray().remove(trayIcon);
                            }
                        });
                        SystemTray.getSystemTray().add(trayIcon);
                        trayIcon.displayMessage("Info!", "Application has been minimized. To close it, click on the icon from system tray and then click on the exit button!", TrayIcon.MessageType.INFO);
                        frame.setVisible(false);
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            }
        });
        frame.pack();
        frame.setVisible(true);
 
    }
    
    private static List<Image> getIcons(){
    	List<Image> list = new ArrayList<Image>();
    	URL iconURL1 = TCPServer.class.getResource("./64.png");
    	URL iconURL2 = TCPServer.class.getResource("./32.png");
    	URL iconURL3 = TCPServer.class.getResource("./16.png");
    	list.add(new ImageIcon(iconURL1).getImage());
    	list.add(new ImageIcon(iconURL2).getImage());
    	list.add(new ImageIcon(iconURL3).getImage());
    	return list;
    }
 
    /**
     * Constructor of the class
     * @param messageListener listens for the messages
     */
    public TCPServer(MessageCallback messageListener) {
        this.messageListener = messageListener;
        computer = new Computer();
        switcher = new Switcher();
        hibernateCommand = new HibernateCommand(computer);
        shutdownCommand = new ShutdownCommand(computer);
        restartCommand = new RestartCommand(computer); 
        
    }
 
    /**
     * Method to send the messages from server to client
     * @param message the message sent by the server
     */
    public void sendMessage(String message){
        if (mOut != null && !mOut.checkError()) {
            mOut.println(message);
            mOut.flush();
            System.out.println("Sent message: " + message);
            messageListener.messageSentCallback("> "+message);
        }
    }
    
    
 
    @Override
    public void run() {
        super.run();
 
        running = true;
 
        try {
            messageListener.messageReceivedCallback("> Waiting for connection...");

 
            
            ServerSocket serverSocket = new ServerSocket(SERVERPORT);
            
            serverSocket.setReuseAddress(true);
 
            
            Socket client = serverSocket.accept();
            messageListener.messageReceivedCallback("> Connected! Receiving command...");
 
            try {
 
                
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
 
              
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                
                
                
                
                while (running) {
                    String message = in.readLine();
                    
 
                    if (message != null && messageListener != null) {
                    	System.out.println(message);
                        
                    	if(message.equals(Computer.SHUTDOWN)){
                    		messageListener.messageReceivedCallback("> Message received: shutdown! Turning computer off...");
                    		switcher.setCommand(shutdownCommand);
                    		//switcher.switchCommand();
                    		this.sendMessage("shutdown");
                    		running=false;                		
                    	}else if(message.equals(Computer.RESTART)){
                    		messageListener.messageReceivedCallback("> Message received: restart! Restarting computer...");
                    		switcher.setCommand(restartCommand);                    		
                    		//switcher.switchCommand();
                    		this.sendMessage("restart");
                    		running=false; 
                    	}else if(message.equals(Computer.HIBERNATE)){
                    		messageListener.messageReceivedCallback("> Message received: hibernate! Hibernating computer...");
                    		switcher.setCommand(hibernateCommand);
                    		//switcher.switchCommand();
                    		this.sendMessage("hibernate");
                    		running=false; 
                    	}else if(message.equals("test")){
                    		this.finalize();
                    		running = false;                 		
                    		
                    	}
                    	
                        
                    }
                }
                
 
            } catch (Exception e) {
                messageListener.messageReceivedCallback("> Error! Something went wrong...");
                e.printStackTrace();
            } catch (Throwable e) {
				// TODO Auto-generated catch block
            	messageListener.messageReceivedCallback("> Error! Something went wrong...");
				e.printStackTrace();
			} finally {
				mOut.flush();
				mOut.close();
				in.close();
                client.close();
                serverSocket.close();
                Thread.sleep(2000);
                System.out.println("S: Done.");
                this.run();
                
                
            }
            
 
        } catch (Exception e) {
        	messageListener.messageReceivedCallback("> Error! Something went wrong...");
            e.printStackTrace();
        }
 
    }
 
    
 
}