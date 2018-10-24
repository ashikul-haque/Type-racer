package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;;
import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.acl.Group;

/**
 * Created by user on 11/28/2015.
 */
public class ClientMain extends Application {
    Stage stage;
    String name;
    String [] part;
    float distancePerLetter;
    int c=0;
    TextArea ta;
    TextField in;
    Label info;
    Label current;
    ImageView frndView;
    public static void main(String[] args) {
        Application.launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage=primaryStage;
        showFirstPage();
    }



    public void showFirstPage() throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("client.fxml"));
        Parent root=loader.load();
        ClientController clientController=loader.getController();
        clientController.setMain(this);
        Scene scene=new Scene(root,600,450);
        stage.setScene(scene);
        stage.show();
    }

    public void showWaitingPage(Socket socket,String n) throws IOException {
        name=n;
        new MessageReceiver(socket,this);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("waiting.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root,600,450);
        stage.setScene(scene);
        stage.show();
    }
    public void secondPage(Socket socket) throws IOException {

        DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
        javafx.scene.Group root=new javafx.scene.Group();
        Label l=new Label("Sentence you have to type");
        l.setLayoutX(230.0);
        l.setLayoutY(14.0);
        Label l1=new Label("Your Input");
        l1.setLayoutX(272.0);
        l1.setLayoutY(175.0);
        ta=new TextArea();
        ta.setEditable(false);
        ta.setLayoutX(21.0);
        ta.setLayoutY(43.0);
        ta.setPrefHeight(33.0);
        ta.setPrefWidth(558.0);
        in=new TextField();;
        in.setLayoutX(21.0);
        in.setLayoutY(200);
        in.setPrefHeight(20.0);
        in.setPrefWidth(558.0);
        current=new Label();
        current.setLayoutX(43.0);
        current.setLayoutY(119.0);
        current.setFont(new Font(18.0));
        ImageView view=new ImageView(new Image(getClass().getResource("a.png").toString()));
        view.setLayoutX(43.0);
        view.setLayoutY(279.0);
        frndView=new ImageView(new Image(getClass().getResource("b.png").toString()));
        frndView.setLayoutX(43.0);
        frndView.setLayoutY(350.0);
        info=new Label(" ");
        info.setLayoutX(43.0);
        info.setLayoutY(420.0);
        info.setTextFill(Color.BLUE);
        info.setFont(new Font(18));
        Label p1=new Label("You :");
        p1.setLayoutX(10.0);
        p1.setLayoutY(282.0);
        Label p2=new Label("Friend :");
        p2.setLayoutX(10.0);
        p2.setLayoutY(355.0);
        root.getChildren().addAll(l, l1, ta, in, current, view, frndView,info,p1,p2);
        Scene scene=new Scene(root,600,500);
        stage.setTitle("Player :"+name);

        in.textProperty().addListener(((observable, oldValue, value) -> {
            String s[]=value.split(" ");
            String newValue=s[s.length-1];
            if (value.equals(ta.getText())){
                current.setText("Nice You Did the job !!!");
                int dis=(int)((distancePerLetter)*value.length());
                view.setLayoutX(43  + dis);

                in.setEditable(false);
                try {
                    dout.writeUTF(String.valueOf(in.getText().length()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (newValue.equals(part[c])&&value.endsWith(" ")) {
                c = c + 1;
                int dis=(int)((distancePerLetter)*value.length());
                view.setLayoutX(43+dis);
                System.out.println("->"+value.length()+" "+in.getText().length()+" "+dis);
                current.setText(part[c]);
                try {
                    dout.writeUTF(String.valueOf(in.getText().length()));
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            else if (part[c].startsWith(newValue)) {
                current.setTextFill(Color.GREEN);
            } else {
                current.setTextFill(Color.RED);
            }

        }));








        stage.setScene(scene);
        stage.show();

    }

    public void addMSG(String st){
        if (st.startsWith("MSG")){
            String str=st.split("#")[1];
            distancePerLetter=400.0f/str.length();


            part=str.split(" ");
            System.out.println(str);
            ta.setText(str);

            current.setText(part[c]);
        }
        else if (st.startsWith("frdCom")){
            String str=st.split(" ")[2];
            int com=Integer.parseInt(str);
            int dis=(int)((distancePerLetter)*com);
            frndView.setLayoutX(43+dis);
            System.out.println(dis);
            System.out.println("frdCpom");

        }
        else if(st.startsWith("yourPos")){
            String str=st.split(" ")[1];
            info.setText(info.getText()+" You Pos:"+str);


        }
        else if (st.startsWith("frdPos")){
            String n=st.split(" ")[1];
            String p=st.split(" ")[2];
            info.setText(info.getText()+" "+n+":"+p);

        }

    }
}
