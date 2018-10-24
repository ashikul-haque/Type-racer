package Server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Random;

/**
 * Created by Ashikul haque on 12/19/2015.
 */
public class ServerController {
    public TableView tableView;
    ClientAcceptor clientAcceptor;
    public Button play;
    String msg;
    String []msgList={
        "As I Live All Must Die. Who's next on the chopping block? I'll leave them in pieces.",
            "I shall bring great suffering! This whole... living thing is... highly overrated. Misery loves company.",
            "My right arm is alot stronger than my left",
            "Go ahead be negative you'll be just my type",
            "I tried to silence my mother once... boy, do i regret that.",
            "More than just precious stones, i bring you an ancient power.",
            "Everyone's a hero... till you shoot off a leg or two. Don't die, yet! Heh, that was only a warning shot.",
            "My heart and sword always for Demacia. Fear is the first of many foes. Fear not, I'm coming.",
            "With utmost efficiency, I am the first of many. Submit to my designs. Join the glorious evolution.",
            "I don't need a buzzer to tell me when to start dominating. I'm here to dunk.",
            "Know that if the tables were turned, I would show you no mercy! I am evil! Stop laughing!",
            "It's only a short way? Is that a short joke?! Give up now! I can see the fear in your heart.",
            "Your entire life has been a mathematical error! Not even death can save you from me!",
            "Call me king, call me demon - water forgets the names of the drowned. It is my mouth into which all travels end.",


    };
    ObservableList<Player> players= FXCollections.observableArrayList();

    public void initialize(){
        TableColumn<Player, String> nameCol = new TableColumn<>("Player");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("sName"));
        TableColumn<Player, String> statusCol = new TableColumn<>("Status");
        statusCol.setMinWidth(100);
        statusCol.setCellValueFactory(new PropertyValueFactory<>("sStatus"));
        TableColumn<Player, String> completeCiol = new TableColumn<>("Complete %");
        completeCiol.setMinWidth(100);
        completeCiol.setCellValueFactory(new PropertyValueFactory<>("sComplete"));
        TableColumn<Player, String> posCol = new TableColumn<>("Position");
        posCol.setMinWidth(100);
        posCol.setCellValueFactory(new PropertyValueFactory<>("sPostion"));
        TableColumn<Player, String> wpmCol = new TableColumn<>("WPM");
        posCol.setMinWidth(100);
        wpmCol.setCellValueFactory(new PropertyValueFactory<>("sWPM"));
        Random random=new Random();
        int secliction=random.nextInt(msgList.length)-1;
        msg=msgList[secliction];
        tableView.getColumns().addAll(nameCol, statusCol, completeCiol, posCol,wpmCol);
        tableView.setItems(players);
    }

    public void addPlayer(Player player){
        players.add(player);
        player.setTotalLen(msg.length());
        new ReceiveThread(player,this);
        if(players.size()>=2){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendMSG();
        }
    }

    public void addInfo(String str,Player p){
        int com=Integer.parseInt(str);
        p.setComplete(com);
        for (Player pl:players){
            if (pl==p) continue;
            else pl.sendInfo("frdCom"+" "+p.getName()+" "+p.getComplete());
        }
        if(com==msg.length()){
            p.setPosition(ServerMain.NEXT_POSITION);
            ServerMain.NEXT_POSITION++;
            p.setStatus("Complete");
            for (Player pl:players){
                if (pl==p)  p.sendInfo("yourPos"+" "+p.getPosition()+"("+"WPM:"+p.getWPM()+")");
                else pl.sendInfo("frdPos"+" "+p.getName()+" "+p.getPosition()+"("+"WPM:"+p.getWPM()+")");
            }

        }



    }

    public void sendMSG(){
        clientAcceptor.stop();
        for(Player p:players){
            p.sendInfo("MSG"+"#"+msg);
            p.setStatus("Typing");
            p.setStartingTime(System.currentTimeMillis());
        }
    }


    public void startPlay(ActionEvent event) {
        sendMSG();
    }

    public void setClientAcceptor(ClientAcceptor clientAcceptor) {
        this.clientAcceptor = clientAcceptor;
    }
}
