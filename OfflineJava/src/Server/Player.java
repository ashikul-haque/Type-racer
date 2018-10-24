package Server;

import javafx.beans.property.SimpleStringProperty;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Ashikul haque on 12/19/2015.
 */
public class Player {
    Socket socket;
    String name;
    String status;
    int complete;
    int position;
    SimpleStringProperty sName;
    SimpleStringProperty sStatus;
    SimpleStringProperty sComplete;
    SimpleStringProperty sPostion;
    SimpleStringProperty sWPM;
    float WPM;
    int totalLen;
    long startingTime;
    int wordCount;

    public Player(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        sName=new SimpleStringProperty(name);
        status="waiting";
        complete=0;
        position=0;
        WPM=0;
        wordCount=0;
        sStatus=new SimpleStringProperty(status);
        sComplete=new SimpleStringProperty(String.valueOf(complete));
        sPostion=new SimpleStringProperty("");
        sWPM=new SimpleStringProperty("");
    }

    public void setTotalLen(int totalLen) {
        this.totalLen = totalLen;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;

    }

    public int getComplete() {
        return complete;

    }

    public int getPosition() {
        return position;
    }

    public String getsName() {
        return sName.get();
    }

    public SimpleStringProperty sNameProperty() {
        return sName;
    }

    public String getsStatus() {
        return sStatus.get();
    }

    public SimpleStringProperty sStatusProperty() {
        return sStatus;
    }

    public String getsComplete() {
        return sComplete.get();
    }

    public SimpleStringProperty sCompleteProperty() {
        return sComplete;
    }

    public String getsPostion() {
        return sPostion.get();
    }

    public SimpleStringProperty sPostionProperty() {
        return sPostion;
    }

    public void setStatus(String status) {
        this.status = status;
        sStatus.set(status);
    }

    public void setComplete(int complete) {
        this.complete = complete;
        wordCount++;
        WPM=(wordCount*60000.0f)/(System.currentTimeMillis()-startingTime);
        sWPM.set(String.valueOf(WPM));
        float per=(float)complete/(float)totalLen;
        sComplete.set(String.valueOf(per*100)+"%");
    }

    public float getWPM() {
        return WPM;
    }

    public void setPosition(int position) {
        this.position = position;

        sPostion.set(String.valueOf(position));
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public String getsWPM() {
        return sWPM.get();
    }

    public SimpleStringProperty sWPMProperty() {
        return sWPM;
    }

    public void sendInfo(String str) {
        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(str);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
