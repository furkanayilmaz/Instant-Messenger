package com.message.messenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ServerSocket serverSocket;
    private Socket socketConnection;
    //Constructor
    public Server(){
        super("Instant Messenger");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(e.getActionCommand());
                userText.setText(""); // Clear Text Field After Message Send
            }
        });
        add(userText, BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        chatWindow.setEditable(false);
        setSize(300,150);
        setVisible(true);
    }

    //Setup & Run Server
    public void startRunning(){
        try{
            serverSocket = new ServerSocket(6789,100); // Note: Port Can Be Any Number (4 Digit) & BackLog Can Be Any Number --> IMPORTANT: Making Backlog Number Big Cause Some Trouble (Big Connections), (High Number Connections)
            while(true){
                 try{
                    //Connect and Chat
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                 }catch(EOFException eofException){
                    showMessage("\n Server Ended the Connection!");
                 }finally{
                     closeConnection();
                 }
            }
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //Wait For Connection & Display Connection Information
    private void waitForConnection() throws IOException{
        showMessage("Waiting For Connection...\n"); //Waiting Connection
        socketConnection = serverSocket.accept();
        showMessage("Connected To "+socketConnection.getInetAddress().getHostName());
    }

    //Get Stream To Send & Receive Data
    private void setupStreams() throws IOException{
        outputStream = new ObjectOutputStream(socketConnection.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socketConnection.getInputStream());
        showMessage("\n Streams Are Setup! \n");
    }

    //During The Chat
    private void whileChatting() throws IOException{
        String message = " You Are Connected ";
        sendMessage(message);
        typeMessage(true);
        do{
            try{
                message = (String) inputStream.readObject();
                showMessage("\n"+ message);
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n Cannot Recognize Message Sent!");
            }
        }while(!message.equals("CLIENT - END"));
    }

    //Close Streams & Sockets
    private void closeConnection(){
        showMessage("\n Closing Connections... \n");
        typeMessage(false);
        try{
            outputStream.close();
            inputStream.close();
            socketConnection.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //Send Message To Client
    private void sendMessage(String message){
        try{
            outputStream.writeObject("SERVER - "+ message);
            outputStream.flush();
            showMessage("\n Server - "+message);
        }catch (IOException ioException){
            chatWindow.append("\n Cannot Send Message! Try Again Later!");
        }
    }

    //Update ChatWindow
    private void showMessage(final String text){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatWindow.append(text);
            }
        });
    }

    //Allow Users To Type Message Into Their ChatBox
    private void typeMessage(final boolean tof){ // NOTE: tof--> True Or False
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                userText.setEditable(tof);
            }
        });
    }

}
