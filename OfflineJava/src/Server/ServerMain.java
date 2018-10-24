package Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by user on 11/28/2015.
 */
public class ServerMain extends Application {

    public static int NEXT_POSITION=1;
    public static void main(String[] args) {
        Application.launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("Server.fxml"));
        Parent root=loader.load();
        ServerController controller=loader.getController();
        new ClientAcceptor(controller);
        Scene scene=new Scene(root,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
