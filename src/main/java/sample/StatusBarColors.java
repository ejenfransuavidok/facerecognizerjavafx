package sample;


import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public enum StatusBarColors {

    RED(1.0, 0, 0), GREEN(0, 1.0, 0);

    Background background;

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    private StatusBarColors (double r, double g, double b) {
        this.background = new Background(new BackgroundFill(new Color(r, g, b, 1.0), CornerRadii.EMPTY, Insets.EMPTY));
    }

}
