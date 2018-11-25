import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;



public class Game extends Application {

    public void start(Stage primaryStage) throws Exception {
        Hero hero = new Hero();
        Monster monster = new Monster();
        Invoker invoker = new Invoker();

        AttackCommand attackCommand = new AttackCommand(hero, monster);
        DefenceCommand defenceCommand = new DefenceCommand(hero);
        MagicCommand magicCommand = new MagicCommand(hero, monster);
        invoker.setCommand(0, attackCommand);
        invoker.setCommand(1, defenceCommand);
        invoker.setCommand(2, magicCommand);


        BorderPane borderPane = new BorderPane();
        BorderPane action_scene = new BorderPane();
        borderPane.setCenter(action_scene);

        action_scene.setStyle("-fx-background-image:url('game_back.png');" +
                "-fx-background-size: cover;");


        VBox vBox1 = new VBox(5);
        Button attack = new Button("Attack");
        Button magic = new Button("Magic");
        Button defence = new Button("Defence");
        Button item = new Button("item");
        vBox1.getChildren().addAll(attack, defence, magic, item);

        ProgressBar healthBar = new ProgressBar();
        ProgressBar monsterHealthBar = new ProgressBar();
        monsterHealthBar.setStyle("-fx-accent: red;");
        monsterHealthBar.setProgress(100);
        healthBar.setProgress(100);
        VBox vBox2 = new VBox();
        vBox2.getChildren().addAll(healthBar,monsterHealthBar);

        HBox bar = new HBox();
        bar.setStyle("-fx-background-color: black;");
        bar.getChildren().addAll(vBox1, vBox2);

        borderPane.setBottom(bar);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);

        primaryStage.show();

        attack.setOnAction(e -> {
            invoker.ButtonWasPressed(0);
            hero.setHealth(hero.getHealth() - monster.getDamage());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    healthBar.setProgress(hero.getHealth()/100);
                    monsterHealthBar.setProgress(monster.getHealth()/100);
                }
            });
        });
        defence.setOnAction(e -> {
            invoker.ButtonWasPressed(1);
            if (!hero.isDefenced())
                hero.setHealth(hero.getHealth()-monster.getDamage());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    healthBar.setProgress(hero.getHealth()/100);
                    monsterHealthBar.setProgress(monster.getHealth()/100);
                }
            });
        });
        magic.setOnAction(e -> {
            invoker.ButtonWasPressed(2);
            hero.setHealth(hero.getHealth()-monster.getDamage());
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    healthBar.setProgress(hero.getHealth()/100);
                    monsterHealthBar.setProgress(monster.getHealth()/100);
                }
            });
        });

    }
}
