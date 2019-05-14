package sample;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ControllerThird extends Controller {

    @FXML
    private GridPane gridPane;

    public ControllerThird() throws IOException {

    }

    @FXML
    private void prev() {
        makeFadeOut(Scenes.SECOND_SCENE);
    }

    private void makeFadeOut(Scenes scene) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> loadNextScene(scene));
        fadeTransition.play();
    }

    private void loadNextScene(Scenes scene) {
        try {
            Parent secondView = FXMLLoader.load(getClass().getResource(scene.getTemplate()));
            Scene newScene = Controller.scenesRepository.get(scene.getId());
            if (newScene == null) {
                newScene = new Scene(secondView);
                Controller.scenesRepository.put(scene.getId(), newScene);
            }
            Stage curStage = (Stage) gridPane.getScene().getWindow();
            curStage.setTitle(scene.getTitle());
            curStage.setScene(newScene);
            curStage.setOnCloseRequest((we -> setClosed()));
        } catch (IOException e) {
            log.debug("Непредвиденная ошибка во время переключения сцен");
        }
    }

    @Override
    void setClosed() {

    }

}
