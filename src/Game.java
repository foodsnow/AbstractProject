import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
    LinkClass linkClass;
    Inventory inventory = hero.getInventory();
    FactoryMonster factoryMonster = new FactoryMonster(category);
    MediaPlayer background_music;

    public void start(Stage primaryStage) throws Exception {
        System.out.println(hero.getClass().toString());

        //classes
        Monster monster = factoryMonster.getNewMonster();
        linkClass = new LinkClass(hero, monster);

        Invoker invoker = new Invoker();

        AttackCommand attackCommand = new AttackCommand(hero, monster);
        DefenceCommand defenceCommand = new DefenceCommand(hero, monster);
        MagicCommand magicCommand = new MagicCommand(hero, monster);
        ItemCommand healthCommand = new HealthItemCommand(hero);
        ItemCommand manaCommand = new ManaItemCommand(hero);
        invoker.setCommand(0, attackCommand);
        invoker.setCommand(1, defenceCommand);
        invoker.setCommand(2, magicCommand);
        invoker.setCommand(3, healthCommand);
        invoker.setCommand(4, manaCommand);
        //

        ImageView hero_image = new ImageView(hero.getImage());
        ImageView monster_image = factoryMonster.getImage();
        ImageView treasure = new ImageView("images/loot1.gif");
        treasure.setFitHeight(100);
        treasure.setFitWidth(100);
        ImageView arrow = new ImageView("images/arrow.png");
        arrow.setFitWidth(70);
        arrow.setFitHeight(50);

        ImageView magic_animation = new ImageView("images/magic4.gif");
        magic_animation.setFitHeight(300);
        magic_animation.setFitWidth(300);
        magic_animation.setVisible(false);

        ImageView attack_animation = new ImageView("images/attack4.gif");
        attack_animation.setFitWidth(300);
        attack_animation.setFitHeight(300);
        attack_animation.setVisible(false);

        ImageView shield_animation = new ImageView("images/shield2.gif");
        shield_animation.fitWidthProperty().bind(hero_image.fitWidthProperty());
        shield_animation.fitHeightProperty().bind(hero_image.fitHeightProperty());
        shield_animation.setVisible(false);

        ImageView black_magic_animation = factoryMonster.getMagic();
        black_magic_animation.fitWidthProperty().bind(hero_image.fitWidthProperty());
        black_magic_animation.fitHeightProperty().bind(hero_image.fitHeightProperty());
        black_magic_animation.setVisible(false);

        Thread music = new Thread(new Runnable() {
            @Override
            public void run() {
                background_music = factoryMonster.getSong();
                background_music.setCycleCount(Timeline.INDEFINITE);
                background_music.setVolume(0.5);
                background_music.play();
            }
        });
        music.start();

        YourAreDeadScene dead_pane = new YourAreDeadScene();
        dead_pane.getStyleClass().add("over");
        Scene scene_over = new Scene(dead_pane);
        scene_over.getStylesheets().addAll("style.css");

        StackPane scene_pane = new StackPane();
        BorderPane borderPane = new BorderPane();
        GridPane action_scene = new GridPane();
        Scene scene = new Scene(scene_pane);
        scene.getStylesheets().addAll("style.css");

        HBox top = new HBox(5);
        Button next = new Button();
        next.setDisable(true);
        Button monster_treasure = new Button();
        monster_treasure.setDisable(true);
        monster_treasure.setGraphic(monster_image);


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
        Label stunned = new Label("Stunned");

        StackPane monster_image_effects = new StackPane(monster_image, magic_animation, attack_animation);
        monster_image_effects.setAlignment(Pos.BOTTOM_CENTER);
        StackPane hero_image_effects = new StackPane(hero_image, shield_animation, black_magic_animation);
        hero_image_effects.setAlignment(Pos.BOTTOM_CENTER);

        VBox hero_desk = new VBox(miss, critical, damage);
        VBox monster_desk = new VBox(miss_monster, critical_monster, damage_monster);

        //Animation
        ///
        ///
        Shake damage_shake = new Shake(damage);
        Shake miss_shake = new Shake(miss);
        Shake critical_shake = new Shake(critical);

        Shake damage_monster_shake = new Shake(damage_monster);
        Shake miss_monster_shake = new Shake(miss_monster);
        Shake critical_monster_shake = new Shake(critical_monster);

        TranslateTransition hero_transition = new TranslateTransition(Duration.millis(300), hero_image_effects);
        hero_transition.setFromX(0f);
        hero_transition.setToX(-20f);
        hero_transition.setCycleCount(2);
        hero_transition.setAutoReverse(true);

        TranslateTransition monster_transition = new TranslateTransition(Duration.millis(300), monster_image_effects);
        monster_transition.setFromX(0f);
        monster_transition.setToX(20f);
        monster_transition.setCycleCount(2);
        monster_transition.setAutoReverse(true);

        stunned.setOpacity(0);
        stunned.setVisible(false);
        FadeTransition stunned_effect = new FadeTransition(Duration.millis(500), stunned);
        stunned_effect.setCycleCount(Timeline.INDEFINITE);
        stunned_effect.setAutoReverse(true);
        stunned_effect.setFromValue(0);
        stunned_effect.setToValue(1);
        stunned_effect.play();
        ///


        scene_pane.getChildren().addAll(borderPane);
        borderPane.setCenter(action_scene);
        borderPane.setBottom(hBox1);
        borderPane.setTop(top);
        vBox1.getChildren().addAll(hero_desk,healthBar,manaBar,hero_image_effects);
        vBox2.getChildren().addAll(stunned,monster_desk,monsterHealthBar,monster_image_effects);
        cell1.getChildren().addAll(vBox1);
        cell2.getChildren().addAll(vBox2);
        action_scene.add(cell1,0,2);
        action_scene.add(cell2, 2,2);
        hBox1.getChildren().addAll(vBox3,hBox2);
        vBox3.getChildren().addAll(attack,defence,magic);
        vBox4.getChildren().addAll(HPitem, MPitem);
        vBox5.getChildren().addAll(hero_hp,hero_mana);
        vBox6.getChildren().addAll(monster_hp);
        hBox2.getChildren().addAll(vBox4,vBox5,vBox6);
        next.setGraphic(arrow);
        top.getChildren().add(next);


        //Class list
        borderPane.getStyleClass().add("maiN");
        action_scene.getStyleClass().addAll("box");
        top.getStyleClass().add("toRight");
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
        hero_hp.getStyleClass().addAll("text");
        monster_hp.getStyleClass().addAll("text");
        hero_mana.getStyleClass().addAll("text");
        miss.getStyleClass().add("miss");
        miss_monster.getStyleClass().add("miss");
        critical.getStyleClass().add("critical");
        critical_monster.getStyleClass().add("critical");
        damage.getStyleClass().add("damage");
        damage_monster.getStyleClass().add("damage");
        stunned.getStyleClass().addAll("stunned");
        hero_desk.getStyleClass().addAll("toRight");
        monster_desk.getStyleClass().addAll("toLeft");
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
        vBox3.prefWidthProperty().bind(scene.widthProperty().divide(4));
        vBox3.prefHeightProperty().bind(scene.heightProperty().divide(4));
        hBox2.prefWidthProperty().bind(scene.widthProperty().divide(4).multiply(3));
        hBox2.prefHeightProperty().bind(scene.heightProperty().divide(4));
        vBox4.prefWidthProperty().bind(scene.widthProperty().divide(5));
        vBox5.prefWidthProperty().bind(scene.widthProperty().divide(4));



        primaryStage.setScene(scene);

        primaryStage.setFullScreen(true);
        primaryStage.show();


        PauseTransition attack_effect = new PauseTransition(Duration.millis(1000));
        PauseTransition pause = new PauseTransition(Duration.millis(1000));

        attack.setOnAction((ActionEvent e) -> {
            boolean isStunned = monster.isStunned();
            invoker.ButtonWasPressed(0);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, healthBar, manaBar, monsterHealthBar);
                    disableAll(attack,defence,magic,HPitem,MPitem);

                    attack_animation.setVisible(true);

                    pause.setOnFinished(e1 -> {
                        black_magic_animation.setVisible(false);
                        if (!isStunned) {
                            if (monster.isMissed()){
                                miss_monster_shake.shake();
                                monster.cleanMiss();
                            }
                            if (monster.isCritical()){
                                critical_monster_shake.shake();
                                monster.cleanCritical();
                            }
                            damage_shake.shake();
                            hero_transition.play();
                        }

                        if (hero.isDead()){
                            hero_image.setVisible(false);
                        }

                        enableAll(attack,defence,magic,HPitem,MPitem);
                        if (hero.isEnoughMana()){
                            magic.setDisable(false);
                        }else {
                            magic.setDisable(true);
                        }
                    });

                    attack_effect.setOnFinished(e -> {
                        attack_animation.setVisible(false);
                        if(hero.isMissed()){
                            miss_shake.shake();
                            hero.cleanMiss();
                        }
                        if (hero.isCritical()){
                            critical_shake.shake();
                            hero.cleanCritical();
                        }

                        damage_monster_shake.shake();
                        monster_transition.play();

                        if (monster.isDead()){
                            monster_treasure.setGraphic(treasure);
                            monster_treasure.setDisable(false);
                        }
                        if (!isStunned){
                            black_magic_animation.setVisible(true);
                        }

                        pause.play();

                    });
                    attack_effect.play();
                    stunned.setVisible(false);
                }
            });
        });
        defence.setOnAction(e -> {
            boolean isStunned = monster.isStunned();
            invoker.ButtonWasPressed(1);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, healthBar, manaBar, monsterHealthBar);
                    disableAll(attack,defence,magic,HPitem,MPitem);
                    shield_animation.setVisible(true);

                    pause.setOnFinished(e -> {
                        black_magic_animation.setVisible(false);
                        if (!isStunned) {
                            if (monster.isMissed()){
                                miss_monster_shake.shake();
                                monster.cleanMiss();
                            }
                            if (monster.isCritical()){
                                critical_monster_shake.shake();
                                monster.cleanCritical();
                            }
                            hero_transition.play();
                            damage_shake.shake();

                        }

                        shield_animation.setVisible(false);


                        if (hero.isDead()){
                            hero_image.setVisible(false);
                        }

                        stunned.setVisible(true);

                        enableAll(attack,defence,magic,HPitem,MPitem);
                        if (hero.isEnoughMana()){
                            magic.setDisable(false);
                        }else {
                            magic.setDisable(true);
                        }
                    });

                    attack_effect.setOnFinished(e -> {

                        if (!isStunned){
                            black_magic_animation.setVisible(true);
                        }
                        pause.play();
                    });
                    attack_effect.play();
                }
            });

        });
        magic.setOnAction(e -> {
            boolean isStunned = monster.isStunned();
            invoker.ButtonWasPressed(2);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, healthBar, manaBar, monsterHealthBar);
                    disableAll(attack,defence,magic,HPitem,MPitem);

                    magic_animation.setVisible(true);

                    pause.setOnFinished(e1 -> {
                        black_magic_animation.setVisible(false);
                        if (!isStunned) {
                            if (monster.isMissed()){
                                miss_monster_shake.shake();
                                monster.cleanMiss();
                            }
                            if (monster.isCritical()){
                                critical_monster_shake.shake();
                                monster.cleanCritical();
                            }
                            damage_shake.shake();
                            hero_transition.play();
                        }

                        if (hero.isDead()){
                            hero_image.setVisible(false);
                        }

                        enableAll(attack,defence,magic,HPitem,MPitem);
                        if (hero.isEnoughMana()){
                            magic.setDisable(false);
                        }else {
                            magic.setDisable(true);
                        }
                    });

                    attack_effect.setOnFinished(e -> {
                        magic_animation.setVisible(false);
                        if(hero.isMissed()){
                            miss_shake.shake();
                            hero.cleanMiss();
                        }
                        if (hero.isCritical()){
                            critical_shake.shake();
                            hero.cleanCritical();
                        }

                        monster_transition.play();
                        damage_monster_shake.shake();

                        if (monster.isDead()){
                            monster_image.setVisible(false);
                        }

                        if (!isStunned){
                            black_magic_animation.setVisible(true);
                        }

                        pause.play();
                    });
                    attack_effect.play();
                    stunned.setVisible(false);

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
        treasure.setOnMouseClicked(e -> {
            if (monster.isDead()){
                getLoot();
            }
        });
    }
    void update(Hero hero, Monster monster, Label hero_hp, Label monster_hp, Label hero_mana, Label damage,
                Label damage_monster, ProgressBar healthBar,ProgressBar manaBar, ProgressBar monsterHealthBar){
        hero_hp.setText("Hero HP " + Math.round(hero.getHealth()*100)/100.0+"/"+"100");
        monster_hp.setText("Monter HP "+ Math.round(monster.getHealth()*100)/100.0+"/"+"100");
        hero_mana.setText("Hero MP "+ Math.round(hero.getMana()*100)/100.0 +"/"+"100");
        healthBar.setProgress(hero.getHealth()/100);
        manaBar.setProgress(hero.getMana()/100);
        damage.setText("-" + Math.round(hero.getGotDamage()*100)/100.0);
        damage_monster.setText("-" +Math.round(monster.getGotDamage()*100)/100.0);
        monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());
    }
    void disableAll(Button... butons){
        for (Button button : butons) {
            button.setDisable(true);
        }
    }
    void enableAll(Button... butons){
        for (Button button: butons){
            button.setDisable(false);
        }
    }

    FadeTransition trans(Label text){
        FadeTransition transition = new FadeTransition(Duration.millis(500), text);
        text.setOpacity(0);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);
        return transition;
    }

    TranslateTransition setShake(Label text){
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), text);
        transition.setFromY(-5f);
        transition.setByY(5f);
        transition.setCycleCount(2);
        transition.setAutoReverse(true);

        return transition;
    }

    void getLoot(){
        if (linkClass.isMonsterDead()){
            linkClass.giveDroptoHero();
        }
    }


    class Shake{
        TranslateTransition transition1;
        FadeTransition transition2;

        Shake(Label label){
            transition1 = setShake(label);
            transition2 = trans(label);
        }

        void shake(){
            transition2.play();
            transition1.play();
        }
    }

}
