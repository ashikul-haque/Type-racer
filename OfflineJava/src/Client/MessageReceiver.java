package Client;

import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by user on 11/28/2015.
 */
public class MessageReceiver implements Runnable {
    DataInputStream inputStream;
    ClientMain clientMain;
    Socket socket;

    public MessageReceiver(Socket socket,ClientMain clientMain) {
        this.socket=socket;
        try {
            inputStream=new DataInputStream(socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clientMain=clientMain;
        Thread t=new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true){
            try {
                String msg=inputStream.readUTF();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (msg.startsWith("MSG#")){
                            try {
                                clientMain.secondPage(socket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        clientMain.addMSG(msg);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
