import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

public class FactoryMonster {
    int category = 1;

    FactoryMonster(int category){
        this.category = category;
    }

    public Drop getDrop(int x){
        ArrayList<Attribute> array = new ArrayList<>();
        for (int i = 0; i < x; i++){
            double ran = Math.random();
            if (ran <= 0.5){
                array.add(new HealthAttribute());
            }else{
                array.add(new ManaAttribute());
            }
        }
        int itemID = 1 + (int)(Math.random()*4);
        System.out.println(array.size());
        System.out.println(itemID);
        Drop drop = new Drop(array, itemID);
        return drop;
    }
    public void setCategory(int category){
        this.category = category;
    }

    public Monster getNewMonster(){
        Monster monster;
        if (category == 1){
            Drop drop = getDrop(3);

            monster = new Monster(5, 80, 50, drop, new Image("images/skeleton1.gif"));
        }
        else if (category == 2){
            Drop drop = getDrop(5);

            monster = new Monster(10, 100, 100, drop, new Image("images/dragon.gif"));
        }
        else{
            Drop drop = getDrop(10);

            monster = new Monster(15, 200, 200, drop, new Image("images/monster3.gif"));
        }
        return monster;
    }
    public MediaPlayer getSong(){
        MediaPlayer mediaPlayer;
        if (category == 1 ){
            Media media = new Media(getClass().getResource("music/Undertale - 039 - Spookwave.mp3").toString());
            mediaPlayer = new MediaPlayer(media);
        }else if (category == 2){
            Media media = new Media(getClass().getResource("music/Undertale - 010 - Ghost Fight.mp3").toString());
            mediaPlayer = new MediaPlayer(media);
        } else{
            Media media = new Media(getClass().getResource("music/Undertale - 068 - Death by Glamour.mp3").toString());
            mediaPlayer = new MediaPlayer(media);
        }
        return mediaPlayer;
    }
    public ImageView getMagic(){
        ImageView view;
        if (category == 1){
            view = new ImageView("images/black_magic2.gif");
        }
        else {
            view = new ImageView("images/black_magic3.gif");
        }
        return view;
    }
    public String getBackground(){
        if (category == 1){
            return "maiN1";
        }else if (category == 2)
            return "maiN2";
        else
            return "maiN3";
    }
}
