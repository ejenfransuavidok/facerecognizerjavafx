package sample;

import javafx.animation.FadeTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ControllerFirst extends Controller {

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView cameraView;

    @FXML
    private Label statusBar;

    @FXML
    private Button nextScene;

    @FXML
    private ImageView faceView;

    final FileChooser fileChooser;

    public ControllerFirst() throws IOException {
        fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    @FXML
    public void loadFile() {
        Stage curStage = (Stage) gridPane.getScene().getWindow();
        File file = fileChooser.showOpenDialog(curStage);
        if (file != null) {
            openFile(file);
        }
    }

    private void openFile(File file) {
        try {
            nextScene.setDisable(true);
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            cameraView.setImage(image);
            Mat frame = matify(bufferedImage);
            Mat face = detectAndDisplay(frame);
            Image imageToShow = Utils.mat2Image(frame);
            updateImageView(cameraView, imageToShow);
            if (face != null) {
                imageToShow = Utils.mat2Image(face);
                updateImageView(faceView, imageToShow);
                Controller.faceRecognition.registerNewMember(Controller.CLIENT, face);
                nextScene.setDisable(false);
                statusBar.setBackground(StatusBarColors.GREEN.getBackground());
                statusBar.setText("Загрузка скан-копии произошла успешно");
            } else {
                statusBar.setBackground(StatusBarColors.RED.getBackground());
                statusBar.setText("Не получилось распознать фото, пожалуйста загрузите более четкую копию");
            }
        } catch (IOException ex) {
            statusBar.setBackground(StatusBarColors.RED.getBackground());
            statusBar.setText(String.format("Во время загрузки скан-копии произошла непредвиденная ошибка: %s", ex.getMessage()));
            logger.warning(String.format("Во время загрузки скан-копии произошла непредвиденная ошибка: %s", ex.getMessage()));
        }
    }

    @FXML
    public void next() {
        makeFadeOut();
    }

    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(event -> loadNextScene());
        fadeTransition.play();
    }

    private void loadNextScene() {
        try {
            Parent secondView = FXMLLoader.load(getClass().getResource(Scenes.SECOND_SCENE.getTemplate()));
            Scene newScene = Controller.scenesRepository.get(1);
            if (newScene == null) {
                newScene = new Scene(secondView);
                Controller.scenesRepository.put(Scenes.SECOND_SCENE.getId(), newScene);
            }
            Stage curStage = (Stage) gridPane.getScene().getWindow();
            curStage.setScene(newScene);
            curStage.setTitle(Scenes.SECOND_SCENE.getTitle());
            curStage.setOnCloseRequest((we -> setClosed()));
        } catch (IOException e) {
            logger.warning("Непредвиденная ошибка во время переключения сцен");
        }
    }

    @Override
    protected void setClosed() {
    }

}
