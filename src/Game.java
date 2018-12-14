import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Application {
    private int category = 1;
    boolean isit = true;

    private Stage primaryStage;
    Scene scene;
    private MenuPane menu = new MenuPane();
    private YourAreDeadScene dead_pane = new YourAreDeadScene();

    Hero hero = new Hero();
    Invoker invoker;
    private LinkClass linkClass;
    private Inventory inventory = hero.getInventory();
    private FactoryMonster factoryMonster = new FactoryMonster(category);
    private MediaPlayer background_music;
    private MediaPlayer menu_player;


    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        System.out.println(hero.getClass().toString());

        //classes
        Monster monster = factoryMonster.getNewMonster();
        linkClass = new LinkClass(hero, monster);

        invoker = new Invoker();

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
        ImageView monster_image = new ImageView(monster.getImage());
        ImageView treasure = new ImageView(monster.getTreasureImage());
        treasure.setVisible(false);
        treasure.setFitHeight(200);
        treasure.setFitWidth(200);
        ImageView arrow = new ImageView("images/arrow.png");
        arrow.setFitWidth(70);
        arrow.setFitHeight(50);

        ImageView magic_animation = new ImageView("images/magic6.gif");
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
        Thread menu_music = new Thread(new Runnable() {
            @Override
            public void run() {
                menu_player = menu.media;
                menu_player.setCycleCount(Timeline.INDEFINITE);
                menu_player.setVolume(0.5);
                menu_player.play();
            }
        });


        ///Main menu
        TextField nickname = menu.getNickname();
        Label error = menu.getError();
        Button button = menu.getButton();
        //Main menu end

        //DEAD
        dead_pane.getStyleClass().add("over");
        ///Game over go home button
        Button goHome = dead_pane.getButton();

        //Action
        StackPane scene_pane = new StackPane();
        BorderPane borderPane = new BorderPane();
        GridPane action_scene = new GridPane();
        if (isit) {
            scene = new Scene(menu, Color.BLACK);
            menu_music.start();
            isit = false;
        }
        else{
            scene = new Scene(scene_pane, Color.BLACK);
            music.start();
        }
        scene.getStylesheets().addAll("style.css");

        HBox top = new HBox(5);
        BorderPane upper = new BorderPane();
        Label descriptioner = new Label("Ready?");
        descriptioner.setStyle("-fx-padding: 8;");
        Pane desc_table = new Pane(descriptioner);
        desc_table.setPrefHeight(100);
        desc_table.prefWidthProperty().bind(scene_pane.widthProperty().divide(2));

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
        Button defence = new Button("Defend");
        Button HPitem = new Button("HP +10 boost"+"("+inventory.numOfHP()+")");
        Button MPitem = new Button("MP +15 boost"+"("+inventory.numOfMP()+")");

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

        StackPane monster_image_effects = new StackPane(monster_image, magic_animation, attack_animation, treasure);
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

        FadeTransition fadeOut = new FadeTransition(Duration.millis(4000), scene_pane);
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(true);
        disableAll(attack, magic, defence, HPitem, MPitem);
        fadeOut.play();
        fadeOut.setOnFinished(e ->{
            enableAll(attack, magic, defence, HPitem, MPitem);
        });
        FadeTransition fadeIn = new FadeTransition(Duration.millis(4000), scene_pane);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(true);

        menu.getMenuFade().play();
        ///


        scene_pane.getChildren().addAll(borderPane);
        borderPane.setCenter(action_scene);
        borderPane.setBottom(hBox1);
        borderPane.setTop(upper);
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
        upper.setRight(next);
        upper.setAlignment(next, Pos.CENTER_RIGHT);
        upper.setAlignment(desc_table, Pos.CENTER);
        upper.setCenter(desc_table);


        //Class list
        borderPane.getStyleClass().add(factoryMonster.getBackground());
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
        desc_table.getStyleClass().add("bottomNav");
        descriptioner.getStyleClass().add("text");
        descriptioner.setStyle("-fx-font-size: 18; -fx-padding: 10;");
        //

        manaBar.setProgress(100);
        healthBar.setProgress(100);
        monsterHealthBar.setProgress(100);


        //Binding
        cell1.prefWidthProperty().bind(action_scene.widthProperty());
        cell2.prefWidthProperty().bind(action_scene.widthProperty());
        cell1.prefHeightProperty().bind(action_scene.heightProperty());
        cell2.prefHeightProperty().bind(action_scene.heightProperty());
        vBox1.prefWidthProperty().bind(cell1.widthProperty().divide(2));
        vBox3.prefWidthProperty().bind(scene.widthProperty().divide(5));
        vBox3.prefHeightProperty().bind(scene.heightProperty().divide(4));
        hBox2.prefWidthProperty().bind(scene.widthProperty().divide(5).multiply(4));
        hBox2.prefHeightProperty().bind(scene.heightProperty().divide(4));
        vBox4.prefWidthProperty().bind(scene.widthProperty().divide(4));
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
                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, HPitem, MPitem);
                    disableAll(attack,defence,magic,HPitem,MPitem);

                    attack_animation.setVisible(true);
                    descriptioner.setText(hero.getName()+": "+hero.attack_desc);

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
                            healthBar.setProgress(hero.getHealth()/100);

                        }

                        if (hero.isDead()){
                            OnHeroDeath(hero_image);
                            disableAll(attack, magic, defence, HPitem, MPitem);
                            background_music.stop();
                            music.interrupt();
                            fadeIn.play();
                            fadeIn.setOnFinished(e2 -> {
                                primaryStage.getScene().setRoot(dead_pane);
                                dead_pane.getFade().play();
                            });
                        }

                        enableAll(attack,defence,magic,HPitem,MPitem);
                        if (hero.isEnoughMana()){
                            magic.setDisable(false);
                        }else {
                            magic.setDisable(true);
                        }
                        if (monster.isDead()) {
                            descriptioner.setText("CHEST:NICE JOB, BODY. I have " + getLootList(monster));
                            disableAll(attack,defence,magic,HPitem,MPitem);
                        }else
                            descriptioner.setText("...");
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
                        monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());

                        damage_monster_shake.shake();
                        monster_transition.play();

                        if (monster.isDead()){
                            OnMonsterDeath(monster_image, treasure);
                            next.setDisable(false);
                        }
                        if (!isStunned){
                            descriptioner.setText("Monster: Curse will reach you soon, "+hero.getName()+"...");
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

                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, HPitem, MPitem);
                    disableAll(attack,defence,magic,HPitem,MPitem);
                    descriptioner.setText(hero.getName()+": "+hero.defence_decs);
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
                            OnHeroDeath(hero_image);
                            disableAll(attack, magic, defence, HPitem, MPitem);
                            background_music.stop();
                            music.interrupt();
                            fadeIn.play();
                            fadeIn.setOnFinished(e2 -> {
                                primaryStage.getScene().setRoot(dead_pane);
                                dead_pane.getFade().play();
                            });
                        }

                        stunned.setVisible(true);

                        enableAll(attack,defence,magic,HPitem,MPitem);
                        if (hero.isEnoughMana()){
                            magic.setDisable(false);
                        }else {
                            magic.setDisable(true);
                        }
                        descriptioner.setText("...");
                    });

                    attack_effect.setOnFinished(e -> {

                        if (!isStunned){
                            descriptioner.setText("Monster: Curse will reach you soon, "+hero.getName()+"...");
                            black_magic_animation.setVisible(true);
                            healthBar.setProgress(hero.getHealth()/100);
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
                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, HPitem, MPitem);
                    disableAll(attack,defence,magic,HPitem,MPitem);
                    descriptioner.setText(hero.getName()+": "+hero.magic_desc);
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
                            healthBar.setProgress(hero.getHealth()/100);
                        }

                        if (hero.isDead()){
                            OnHeroDeath(hero_image);
                            disableAll(attack, magic, defence, HPitem, MPitem);
                            background_music.stop();
                            music.interrupt();
                            fadeIn.play();
                            fadeIn.setOnFinished(e2 -> {
                                primaryStage.getScene().setRoot(dead_pane);
                                dead_pane.getFade().play();
                            });

                        }

                        enableAll(attack,defence,magic,HPitem,MPitem);

                        descriptioner.setText("...");
                        if (monster.isDead()) {
                            descriptioner.setText("CHEST:NICE JOB, BODY. I have " + getLootList(monster));
                            disableAll(attack,defence,magic,HPitem,MPitem);
                        }
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
                        monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());
                        manaBar.setProgress(hero.getMana()/100);

                        monster_transition.play();
                        damage_monster_shake.shake();

                        if (monster.isDead()){
                            OnMonsterDeath(monster_image, treasure);
                            next.setDisable(false);
                        }

                        if (!isStunned){
                            descriptioner.setText("Monster: Curse will reach you soon, "+hero.getName()+"...");
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
                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, HPitem, MPitem);
                    healthBar.setProgress(hero.getHealth()/100);
                    if (hero.isEnoughMana()){
                        magic.setDisable(false);
                    }else {
                        magic.setDisable(true);
                    }
                }
            });
        });
        MPitem.setOnAction(e -> {
            invoker.ButtonWasPressed(4);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, HPitem, MPitem);
                    manaBar.setProgress(hero.getMana()/100);
                    if (hero.isEnoughMana()){
                        magic.setDisable(false);
                    }else {
                        magic.setDisable(true);
                    }
                }
            });
        });
        treasure.setOnMouseClicked(e -> {
            if (monster.isDead()){
                getLoot();
                setHero(linkClass.getItemID());
                update(hero, monster, hero_hp, monster_hp, hero_mana, damage, damage_monster, HPitem, MPitem);
                healthBar.setProgress(hero.getHealth()/100);
                manaBar.setProgress(hero.getMana()/100);
                treasure.setDisable(true);
                monsterHealthBar.setProgress(monster.getHealth()/monster.getFullHP());
            }
        });
        next.setOnAction(e -> {
            this.category += 1;
            factoryMonster.setCategory(this.category);
            background_music.stop();
            music.interrupt();
            hero.setHealth(100);
            hero.setMana(100);
            try {
                start(primaryStage);
            }catch (Exception ex){}
        });
        goHome.setOnAction(e ->{
            category = 1;
            factoryMonster.setCategory(category);
            isit = true;
            hero = new Hero();
            try {
                start(primaryStage);
            }catch (Exception ex){}
        });
        button.setOnAction(e -> {
            if (nickname.getText().equals("")){
                error.setVisible(true);
            }else{
                hero.setName(nickname.getText());
                error.setVisible(false);
                menu_player.pause();
                menu_music.interrupt();
                music.start();
                menu.menuFadeOut.play();
                menu.menuFadeOut.setOnFinished(e1 -> {
                    fadeOut.play();
                    primaryStage.getScene().setRoot(scene_pane);
                });
            }
        });
    }
    void update(Hero hero, Monster monster, Label hero_hp, Label monster_hp, Label hero_mana, Label damage,
                Label damage_monster, Button HPitem, Button MPitem){
        hero_hp.setText("Hero HP " + Math.round(hero.getHealth()*100)/100.0+"/"+"100");
        monster_hp.setText("Monter HP "+ Math.round(monster.getHealth()*100)/100.0+"/"+"100");
        hero_mana.setText("Hero MP "+ Math.round(hero.getMana()*100)/100.0 +"/"+"100");
        damage.setText("-" + Math.round(hero.getGotDamage()*100)/100.0);
        damage_monster.setText("-" +Math.round(monster.getGotDamage()*100)/100.0);
        HPitem.setText("HP +10 boost"+"("+inventory.numOfHP()+")");
        MPitem.setText("MP +15 boost"+"("+inventory.numOfMP()+")");
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
    void setHero(int i){
        if (i == 1){
            hero = new Axe(hero);
        }else if(i == 2){
            hero = new Palochka(hero);
        }else if (i == 3){
            hero = new Armor(hero);
        }
    }
    String getLootList(Monster monster){
        Drop drop = monster.giveDrop();
        String item = "";
        int HP = 0;
        int MP = 0;
        for (Attribute i: drop.getDrop()){
            if (i.getAttributeID() == 0)
                HP++;
            else if (i.getAttributeID() == 1)
                MP++;
        }
        int i = drop.getItemID();
        if (i == 1){
            item = "Axe +10 to attack";
        }else if(i == 2){
            item = "PALOCHKA +8 TO MAGIC!!!!";
        }else if (i == 3){
            item = "Armor +250 to defence";
        }else
            item = "...THAT's ALL!!!";
        return HP+" HP," + MP + " MP," + item + ".BYE!!HaHaHa!!";
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
    void OnMonsterDeath(ImageView monster_image, ImageView treasure){
        Monster monster = linkClass.getMonster();
        if (monster.isDead()){
            monster_image.setVisible(false);
            treasure.setVisible(true);
        }
    }
    void OnHeroDeath(ImageView hero_image){
        if (hero.isDead()){
            hero_image.setVisible(false);
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
