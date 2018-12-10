import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.beans.FeatureDescriptor;
import java.nio.file.Path;


public class Game extends Application {
    int category = 1;
    Hero hero = new Hero();
    Inventory inventory = hero.getInventory();
    FactoryMonster factoryMonster = new FactoryMonster(category);

    public void start(Stage primaryStage) throws Exception {
        System.out.println(hero.getClass().toString());

        //classes
        Monster monster = factoryMonster.getNewMonster();

        Invoker invoker = new Invoker();

        AttackCommand attackCommand = new AttackCommand(hero, monster);
        DefenceCommand defenceCommand = new DefenceCommand(hero);
        MagicCommand magicCommand = new MagicCommand(hero, monster);
        ItemCommand healthCommand = new HealthItemCommand(hero);
        ItemCommand manaCommand = new ManaItemCommand(hero);
        invoker.setCommand(0, attackCommand);
        invoker.setCommand(1, defenceCommand);
        invoker.setCommand(2, magicCommand);
        invoker.setCommand(3, healthCommand);
        invoker.setCommand(4, manaCommand);
        //

        ImageView hero_image = new ImageView("images/hero1.gif");
        ImageView monster_image = factoryMonster.getImage();

        MediaPlayer background_music = factoryMonster.getSong();
        background_music.setCycleCount(Timeline.INDEFINITE);
        background_music.setVolume(1);
        background_music.play();


        BorderPane borderPane = new BorderPane();
        GridPane action_scene = new GridPane();
        Scene scene = new Scene(borderPane);

        VBox vBox2 = new VBox();
        VBox vBox1 = new VBox();
        VBox vBox3 = new VBox();
        VBox vBox4 = new VBox(2);
        VBox vBox5 = new VBox();
        VBox vBox6 = new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        ProgressBar healthBar = new ProgressBar();
        ProgressBar manaBar = new ProgressBar();
        //Monster
        ProgressBar monsterHealthBar = new ProgressBar();
        //Hero
        Button attack = new Button("Attack");
        Button magic = new Button("Magic");
        Button defence = new Button("Defence");
        Button HPitem = new Button("HP boost"+"("+inventory.numOfHP()+")");
        Button MPitem = new Button("MP boost"+"("+inventory.numOfMP()+")");


        HBox cell1 = new HBox();
        HBox cell2 = new HBox();

        Label hero_hp = new Label("Hero HP "+(int)hero.getHealth()+"/100");
        Label hero_mana = new Label("Hero MP "+(int)hero.getMana()+"/100");
        Label monster_hp = new Label("Monster HP "+(int)monster.getHealth()+"/100");
        Label miss = new Label("Miss");
        Label critical = new Label("Critical");
        Label damage = new Label("");
        Label critical_monster = new Label("Critical");
        Label damage_monster = new Label("");
        Label miss_monster = new Label("Miss");

        Line line1 = new Line(0 , 0, 20, 0);
        line1.setVisible(false);
        Line line2 = new Line(0,0,20,0);
        line2.setVisible(false);
        Line line3 = new Line(0,0,20,0);
        line3.setVisible(false);
        Line line4 = new Line(0 , 0, 20, 0);
        line4.setVisible(false);
        Line line5 = new Line(0,0,20,0);
        line5.setVisible(false);
        Line line6 = new Line(0,0,20,0);
        line6.setVisible(false);
        StackPane pane1 = new StackPane();
        pane1.getChildren().addAll(line1,miss);
        StackPane pane2 = new StackPane();
        pane2.getChildren().addAll(line2,miss_monster);
        StackPane pane3 = new StackPane();
        pane3.getChildren().addAll(line3, critical);
        StackPane pane4 = new StackPane();
        pane4.getChildren().addAll(line4, critical_monster);
        StackPane pane5 = new StackPane();
        pane5.getChildren().addAll(line5, damage);
        StackPane pane6 = new StackPane();
        pane6.getChildren().addAll(line6, damage_monster);

        VBox stat1 = new VBox(pane5,pane3,pane1);
        stat1.setAlignment(Pos.CENTER);
        VBox stat2 = new VBox(pane6, pane4,pane2);
        stat2.setAlignment(Pos.CENTER);
        stat2.setSpacing(2);

        FadeTransition transition_miss = trans(miss);
        FadeTransition transition_miss_mons = trans(miss_monster);
        FadeTransition transition_critical = trans(critical);
        FadeTransition transition_critical_mons = trans(critical_monster);
        FadeTransition transition_damage = trans(damage);
        FadeTransition transition_damage_mons = trans(damage_monster);
        setLull(miss, line1);
        setLull(miss_monster, line2);
        setLull(critical, line3);
        setLull(critical_monster, line4);
        setLull(damage, line5);
        setLull(damage_monster, line6);


        borderPane.setCenter(action_scene);
        borderPane.setBottom(hBox1);
        vBox1.getChildren().addAll(healthBar,manaBar,hero_image);
        vBox2.getChildren().addAll(monsterHealthBar,monster_image);
        cell1.getChildren().addAll(vBox1,stat1);
        cell2.getChildren().addAll(stat2,vBox2);
        action_scene.add(cell1,0,2);
        action_scene.add(cell2, 2,2);
        hBox1.getChildren().addAll(vBox3,hBox2);
        vBox3.getChildren().addAll(attack,defence,magic);
        vBox4.getChildren().addAll(HPitem, MPitem);
        vBox5.getChildren().addAll(hero_hp,hero_mana);
        vBox6.getChildren().addAll(monster_hp);
        hBox2.getChildren().addAll(vBox4,vBox5,vBox6);

        //Class list
        borderPane.getStyleClass().add("maiN");
        action_scene.getStyleClass().addAll("box");
        manaBar.getStyleClass().addAll("mana");
        healthBar.getStyleClass().addAll("health");
        monsterHealthBar.getStyleClass().addAll("health");
        cell1.getStyleClass().addAll("cells");
        cell2.getStyleClass().addAll("cells");
        vBox3.getStyleClass().add("bottomNav");
        hBox2.getStyleClass().add("bottomNav");
        vBox1.getStyleClass().addAll("ver","left");
        vBox2.getStyleClass().addAll("ver","right");
        vBox5.getStyleClass().addAll("allBar");
        vBox4.getStyleClass().addAll("allBar");
        vBox6.getStyleClass().addAll("allBar");
        hero_hp.getStyleClass().addAll("button");
        monster_hp.getStyleClass().addAll("button");
        hero_mana.getStyleClass().addAll("button");
        miss.getStyleClass().add("miss");
        miss_monster.getStyleClass().add("miss");
        critical.getStyleClass().add("critical");
        critical_monster.getStyleClass().add("critical");
        damage.getStyleClass().add("damage");
        damage_monster.getStyleClass().add("damage");
        stat1.getStyleClass().addAll("cells","stat");
        stat2.getStyleClass().addAll("cells","stat");
        //

        manaBar.setProgress(100);
        healthBar.setProgress(100);
        monsterHealthBar.setProgress(100);

        action_scene.setGridLinesVisible(true);

        //Binding
        cell1.prefWidthProperty().bind(action_scene.widthProperty());
        cell2.prefWidthProperty().bind(action_scene.widthProperty());
        cell1.prefHeightProperty().bind(action_scene.heightProperty());
        cell2.prefHeightProperty().bind(action_scene.heightProperty());
        vBox1.prefWidthProperty().bind(cell1.widthProperty().divide(2));
        stat1.prefWidthProperty().bind(cell1.widthProperty().divide(2));
        vBox2.prefWidthProperty().bind(cell2.widthProperty().divide(2));
        stat2.prefWidthProperty().bind(cell2.widthProperty().divide(2));
        vBox3.prefWidthProperty().bind(scene.widthProperty().divide(4));
        vBox3.prefHeightProperty().bind(scene.heightProperty().divide(4));
        hBox2.prefWidthProperty().bind(scene.widthProperty().divide(4).multiply(3));
        hBox2.prefHeightProperty().bind(scene.heightProperty().divide(4));
        vBox4.prefWidthProperty().bind(scene.widthProperty().divide(5));
        vBox5.prefWidthProperty().bind(scene.widthProperty().divide(4));




        scene.getStylesheets().addAll("style.css");
        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.show();

        attack.setOnAction((ActionEvent e) -> {
            invoker.ButtonWasPressed(0);
            double monster_dam =  monster.getDamage();
            hero.setGotDamage(monster_dam);
            hero.setHealth(hero.getHealth() - monster_dam);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    hero_hp.setText("Hero HP "+(int)hero.getHealth()+"/"+"100");
                    monster_hp.setText("Monter Hp "+(int)monster.getHealth()+"/"+"100");
                    healthBar.setProgress(hero.getHealth()/100);
                    damage.setText("-" + hero.getGotDamage());
                    damage_monster.setText("-" + monster.getGotDamage());

                    moveTrans(hero.isMissed(), transition_miss, hero);
                    moveTrans(hero.isCritical(), transition_critical, hero);
                    transition_damage.play();

                    if (monster.isDead()){
                        monster_image.setVisible(false);
                    }

                    if (!hero.isEnoughMana()){
                        magic.setDisable(true);
                    }else {
                        magic.setDisable(false);
                    }
                    monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());

                    moveTrans(monster.isMissed(), transition_miss_mons, monster);
                    moveTrans(monster.isCritical(), transition_critical_mons, monster);
                    transition_damage_mons.play();

                    if (hero.isDead()){
                        hero_image.setVisible(false);
                    }

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
                    monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());
                }
            });
        });
        magic.setOnAction(e -> {
            invoker.ButtonWasPressed(2);
            double monster_dam =  monster.getDamage();
            hero.setHealth(hero.getHealth()-monster_dam);
            hero.setGotDamage(monster_dam);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    hero_hp.setText("Hero HP " + (int)hero.getHealth()+"/"+"100");
                    monster_hp.setText("Monter HP "+(int)monster.getHealth()+"/"+"100");
                    hero_mana.setText("Hero MP "+(int)hero.getMana()+"/"+"100");
                    healthBar.setProgress(hero.getHealth()/100);
                    manaBar.setProgress(hero.getMana()/100);
                    damage.setText("-" + hero.getGotDamage());
                    damage_monster.setText("-" + monster.getGotDamage());

                    moveTrans(hero.isMissed(), transition_miss, hero);
                    moveTrans(hero.isCritical(), transition_critical, hero);
                    transition_damage.play();

                    if (monster.isDead()){
                        monster_image.setVisible(false);
                    }

                    if (!hero.isEnoughMana()){
                        magic.setDisable(true);
                    }else {
                        magic.setDisable(false);
                    }
                    monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());

                    moveTrans(monster.isMissed(), transition_miss_mons, monster);
                    moveTrans(monster.isCritical(), transition_critical_mons, monster);
                    transition_damage_mons.play();

                    if (hero.isDead()){
                        hero_image.setVisible(false);
                    }
                }
            });
        });
        HPitem.setOnAction(e -> {
            invoker.ButtonWasPressed(3);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    healthBar.setProgress(hero.getHealth()/100);
                    HPitem.setText("HP boost"+"("+inventory.numOfHP()+")");
                }
            });
        });
        MPitem.setOnAction(e -> {
            invoker.ButtonWasPressed(4);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    manaBar.setProgress(hero.getMana()/100);
                    MPitem.setText("MP boost"+"("+inventory.numOfMP()+")");
                }
            });
        });
    }
    public void checkDead(){

    }
    //public void update(Hero hero, Monster monster, ){

    //}

    public void moveTrans(boolean bol, Transition transition, GameUnit unit){
        if (bol){
            transition.play();
            unit.cleanAll();
        }
    }

    public FadeTransition trans(Label text){
        FadeTransition transition = new FadeTransition(Duration.millis(350), text);
        text.setOpacity(0);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        return transition;
    }
    public void setLull(Label text, Line path){
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.millis(500));
        transition.setPath(path);
        transition.setNode(text);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setAutoReverse(true);
        transition.play();
    }

}
