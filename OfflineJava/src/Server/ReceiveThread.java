package Server;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Ashikul haque on 12/19/2015.
 */
public class ReceiveThread implements Runnable {
    Player player;
    ServerController controller;
    Thread t;


    public ReceiveThread(Player player, ServerController controller) {
        this.player = player;
        this.controller = controller;
        t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            DataInputStream din=new DataInputStream(player.getSocket().getInputStream());
            while (true){
                String str=din.readUTF();
                System.out.println("Server Reci:"+str);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.addInfo(str,player);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
