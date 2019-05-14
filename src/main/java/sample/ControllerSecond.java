package sample;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;

@Slf4j
public class ControllerSecond extends Controller {

    private final Double ATTEMPTS = 100d;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label cameraLabel;

    @FXML
    private ImageView cameraView;

    @FXML
    private ImageView faceView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Button nextScene;

    public static AtomicInteger counter = new AtomicInteger(0);

    public static AtomicInteger trueCounter = new AtomicInteger(0);

    public ControllerSecond () throws Exception {
        this.capture = new VideoCapture();
    }

    /**
     * The action triggered by pushing the button on the GUI
     */
    @FXML
    protected void startCamera() {
        progressBar.setProgress(0);
        counter.set(0);
        trueCounter.set(0);
        nextScene.setDisable(true);
        if (!this.cameraActive) {
            counter.incrementAndGet();
            // start the video capture
            if (!this.capture.isOpened())
                this.capture.open(0);

            // is the video stream available?
            if (this.capture.isOpened()) {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run() {
                        counter.incrementAndGet();
                        progressBar.setProgress(counter.doubleValue() / ATTEMPTS );
                        // effectively grab and process a single frame
                        Pair<Mat, Mat> frames = grabFrame();
                        // convert and show the frame
                        Image imageToShow = Utils.mat2Image(frames.getKey());
                        updateImageView(cameraView, imageToShow);
                        if (frames.getValue() != null) {
                            imageToShow = Utils.mat2Image(frames.getValue());
                            updateImageView(faceView, imageToShow);
                            try {
                                Imgproc.cvtColor(frames.getValue(), frames.getValue(), CV_BGR2GRAY);
                                String whoIs = faceRecognition.whoIs(frames.getValue());
                                if (whoIs.contains(Controller.CLIENT)) {
                                    trueCounter.incrementAndGet();
                                }
                            } catch (IOException e) {
                                log.debug("ошибка во время распознавания");
                            }
                        }
                        if (counter.get() == ATTEMPTS) {
                            stopAcquisition();
                            nextScene.setDisable(false);
                        }
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
                this.cameraLabel.setText("Стоп");
            }
            else {
                // log the error
                System.err.println("Failed to open the camera connection...");
            }
        }
        else {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the button content
            this.cameraLabel.setText("Старт");
            // stop the timer
            this.stopAcquisition();
        }
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown()) {
            try {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened()) {
            // release the camera - не срабатывает на Linux Ubuntu 14.04
            // this.capture.release();
        }
    }

    @FXML
    private void prev() {
        makeFadeOut(Scenes.FIRST_SCENE);
    }

    @FXML
    private void next() {
        makeFadeOut(Scenes.THIRD_SCENE);
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
            if (newScene.lookup("#resultLabel") != null) {
                Label label = (Label) newScene.lookup("#resultLabel");
                label.setText(String.format("Это Вы с вероятностью %.2f", 100*trueCounter.doubleValue() / ATTEMPTS) + "%");
            }
        } catch (IOException e) {
            log.debug("Непредвиденная ошибка во время переключения сцен");
        }
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed() {
        this.stopAcquisition();
    }

}
