package com.example.tsf_app;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    //private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            //this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("failed to create client");
            e.printStackTrace();
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMsgToServer(String msgToServer)
    {
        try {
            bufferedWriter.write(msgToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("failed to send message..");
            closeAll(socket,bufferedReader,bufferedWriter);
        }
    }
    public void receiveMsgFromServer(VBox vBox)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                   // String msgFromServer = objectInputStream.readUTF();//alt of the bottom line
                        String msgFromServer = bufferedReader.readLine();
                        chat_Controller.addLabel(msgFromServer, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error \ncould not read message from the server!");
                        Client.this.closeAll(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

   /* public void receiveMsgFromServer(VBox vbox) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                String msgFromServer = objectInputStream.readUTF();
                chat_Controller.addLabel(msgFromServer, vbox);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle socket reset error
            if(e instanceof SocketException && e.getMessage().equals("Connection reset")) {
            // Reconnect to the server
                try {
                    socket = new Socket("localhost", 1234);
            // Call receiveMsgFromServer() recursively to start receiving messages again
                    receiveMsgFromServer(vbox);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }*/
    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
