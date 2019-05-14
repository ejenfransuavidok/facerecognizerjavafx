package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Scenes.FIRST_SCENE.getTemplate()));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        Controller.scenesRepository.put(Scenes.FIRST_SCENE.getId(), scene);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle(Scenes.FIRST_SCENE.getTitle());
        // set the proper behavior on closing the application
        primaryStage.setOnCloseRequest((we -> ((Controller)loader.getController()).setClosed()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // load the native OpenCV library
        nu.pattern.OpenCV.loadLocally();

        try {
            Controller.faceRecognition.loadModel();
            launch(args);
        } catch (Exception e) {
            System.err.println("Ошибка во время старта приложения!");
        }

    }
}
