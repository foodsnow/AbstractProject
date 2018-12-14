import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MenuPane extends BorderPane {
    MediaPlayer media = new MediaPlayer(new Media(getClass().getResource("music/Undertale - 001 - Once Upon A Time.mp3").toString()));
    Label error = new Label("ERROR! at jaz nahy");
    Button button = new Button("Start");
    TextField nickname = new TextField();
    FadeTransition menuFade;
    FadeTransition menuFadeOut;

    MenuPane(){
        Label title = new Label("Final Fantasy XS(europeika)");
        title.setFont(new Font(54));
        title.getStyleClass().add("jen");

        nickname.setText("azichLOH");
        Label label = new Label("Nickname", nickname);
        label.setContentDisplay(ContentDisplay.RIGHT);
        label.getStyleClass().add("text");
        nickname.getStyleClass().add("nickname");

        button.setAlignment(Pos.CENTER);
        button.getStyleClass().add("button");

        Image image = new Image("images/hero2.gif");     //!!!!!!
        ImageView gif = new ImageView();
        gif.setImage(image);

        Label hpValue = new Label("HP: 100");
        hpValue.setFont(new Font(35));
        Label manaValue = new Label("Mana: 100");
        manaValue.setFont(new Font(35));
        Label attackValue = new Label("Damage: 5");
        attackValue.setFont(new Font(35));
        Label magicValue = new Label("MagicDamage: 12");
        magicValue.setFont(new Font(35));
        hpValue.getStyleClass().add("text");
        manaValue.getStyleClass().add("text");
        attackValue.getStyleClass().add("text");
        magicValue.getStyleClass().add("text");

        VBox info = new VBox(5);
        info.getChildren().addAll(hpValue,manaValue,attackValue,magicValue);

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(gif,info);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(title,label,hBox,button);
        vBox.setAlignment(Pos.CENTER);

        menuFade = new FadeTransition(Duration.millis(4000), this);
        menuFade.setFromValue(0);
        menuFade.setToValue(1);
        menuFade.setCycleCount(1);

        menuFadeOut = new FadeTransition(Duration.millis(4000), this);
        menuFadeOut.setFromValue(1);
        menuFadeOut.setToValue(0);
        menuFadeOut.setCycleCount(1);

        error.setTextFill(Color.web("#ff0000"));
        error.setVisible(false);
        vBox.getChildren().add(error);

        this.setCenter(vBox);
        this.setBackground(new Background(new BackgroundFill(Color.web("#1a1a1a"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public Button getButton(){
        return button;
    }

    public Label getError(){
        return error;
    }

    public TextField getNickname(){
        return nickname;
    }

    public FadeTransition getMenuFade() {
        return menuFade;
    }

    public FadeTransition getMenuFadeOut() {
        return menuFadeOut;
    }
}
