import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
 
public class MainBoard extends JFrame {
    private JTextArea textField; 
    private TCPServer tcpServer;
 
    
    private void appendInformation(){
    	textField.append("> Hello!\n"+
    "> Welcome to the Turn Me Off server application!\n"+
    "> Use your Turn Me Off android widget to turn off / hibernate / restart your computer!\n"+
    "> Enjoy!\n");
    }
    
    
    
    
    public MainBoard() {
 
        super("TurnMeOff Server Application");
 
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
 
        
 
        //here we will have the text messages screen
        textField = new JTextArea();
        textField.setColumns(50);
        textField.setRows(10);
        textField.setBackground(Color.black);
        textField.setForeground(Color.green);
        textField.setFont(new Font("Verdana,", Font.BOLD, 12));
        appendInformation();              
        textField.setEditable(false);
        
        tcpServer = new TCPServer(new MessageCallback() {
                   
        	
        	        
					@Override
					public void messageReceivedCallback(String message) {
						// TODO Auto-generated method stub
						textField.append("\n "+message);
						
					}

					@Override
					public void messageSentCallback(String message) {
						// TODO Auto-generated method stub
						textField.append("\n "+message);
						
					}
                });
                tcpServer.start();
 
        JScrollPane jScroll = new JScrollPane(textField);
        jScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       
        panel.add(textField); 
        panel.add(jScroll);
        getContentPane().add(panel);
      
 
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
 
        setSize(300, 170);
        setResizable(false);
        setVisible(true);
    }
}