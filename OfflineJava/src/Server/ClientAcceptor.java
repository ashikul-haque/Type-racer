package Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user on 11/28/2015.
 */
public class ClientAcceptor implements Runnable
{
    ServerController controller;
    Thread t;
    private boolean isRunning;

    public ClientAcceptor(ServerController controller) {
        this.controller = controller;
        controller.setClientAcceptor(this);
        try {
            serverSocket=new ServerSocket(10321);
            System.out.println(InetAddress.getLocalHost().toString());
            System.out.println(serverSocket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning=true;
        t=new Thread(this);
        t.start();
    }
    ServerSocket serverSocket;
    @Override
    public void run() {
        while (isRunning)
        {
            try {
                Socket socket=serverSocket.accept();
                DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());
                String name=dataInputStream.readUTF();
                Player p=new Player(socket,name);
                controller.addPlayer(p);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void stop(){
        isRunning=false;
    }

}
