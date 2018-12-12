import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.sql.Time;

public class YourAreDeadScene extends StackPane{
    FadeTransition fade;

    YourAreDeadScene(){
        VBox hBox = new VBox(5);
        Label desc = new Label("Game Over");
        Button goHome = new Button("go home");

        FadeTransition blink = new FadeTransition(Duration.millis(3000), desc);
        blink.setCycleCount(Timeline.INDEFINITE);
        blink.setFromValue(1);
        blink.setToValue(0);
        blink.setAutoReverse(true);
        blink.play();

        this.setOpacity(1);
        fade = new FadeTransition(Duration.millis(5000), this);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setCycleCount(1);

        hBox.getChildren().addAll(desc, goHome);

        desc.getStyleClass().add("jen");
        hBox.getStyleClass().addAll("container");
        goHome.getStyleClass().addAll("button");


        this.getChildren().addAll(hBox);


    }
}
