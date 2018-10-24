package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by user on 11/28/2015.
 */
public class ClientController {

    ClientMain main;
    @FXML
    TextArea textArea;
    @FXML
    TextField textField;
    @FXML
    Button button;
    @FXML
    public void buttonclick(ActionEvent ob2)
    {
        Socket socket=null;
        try {
            socket=new Socket("localhost",10321);
            DataOutputStream outputStream=new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(textField.getText());
            outputStream.flush();
            main.showWaitingPage(socket,textField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void addMsg(String msg){
        textArea.setText(msg);
    }

    public void setMain(ClientMain main) {
        this.main = main;
    }
}
