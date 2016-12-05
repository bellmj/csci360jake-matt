package system.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import system.db.FileBallotHandler;
import system.db.FileRegistrationHandler;
import system.election.ElectionHandler;
import system.election.voting.BallotHandler;
import system.registration.RegistrationHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginDialog loginDialog = new LoginDialog();

        loginDialog.showAndWait().ifPresent(result -> {
            if (result.getKey()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OpenScreen" +
                            ".fxml"));
                    Pane root = fxmlLoader.load();
                    OpenScreenController openScreenController = fxmlLoader
                            .getController();

                    openScreenController.setElectionHandler(new ElectionHandler());
                    openScreenController.setRegistrationHandler(new
                            RegistrationHandler(new FileRegistrationHandler()));
                    openScreenController.setBallotHandler(new BallotHandler
                            (new FileBallotHandler()));
                    openScreenController.setShouldShowAdminOptions(result.getValue());

                    primaryStage.setTitle("360Voting Startup");
                    primaryStage.setScene(new Scene(root, 400, 550));
                    primaryStage.setResizable(false);
                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
