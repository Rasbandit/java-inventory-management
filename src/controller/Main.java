package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// The Javadoc folder is located at the root of the zip file

/** The Main class that creates the program. */
public class Main extends Application {

    @Override
    /** The method that starts the JavaFX application. */
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        String css = this.getClass().getResource("/view/index.css").toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }

    /** Launching the min Application. */
    public static void main(String[] args) {
        launch(args);
    }
}
