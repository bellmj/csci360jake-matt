package System.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Stage loginStage = new Stage();
        loginStage.setTitle("Authentication");
        loginStage.setScene(new Scene(root, 340, 340));
        loginStage.setResizable(false);
        loginStage.showAndWait();

        root = FXMLLoader.load(getClass().getResource("OpenScreen.fxml"));
        primaryStage.setTitle("360Voting Startup");
        primaryStage.setScene(new Scene(root, 400, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
