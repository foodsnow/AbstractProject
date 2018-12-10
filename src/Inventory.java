import java.util.ArrayList;

public class Inventory {
    ArrayList<Attribute> bagMP;
    ArrayList<Attribute> bagHP;
    Hero hero;

    Inventory(Hero hero){
        this.bagHP = new ArrayList<>();
        this.bagMP = new ArrayList<>();
        this.hero = hero;
    }
    public void loot(Drop drop){
        for (Attribute i: drop.getDrop()){
            if (i.getAttributeID() == 0){
                bagHP.add(i);
            }
            else if (i.getAttributeID() == 1){
                bagMP.add(i);
            }
        }
    }
    public void useHp(){
        if (bagHP.size() > 0){
            bagHP.get(0).add(hero);
            bagHP.remove(0);
        }else{
            System.out.println("Zero HP attr");
        }
    }
    public void useMp(){
        if (bagMP.size() > 0){
            bagMP.get(0).add(hero);
            bagMP.remove(0);
        }else{
            System.out.println("Zero MP attr");
        }
    }
    public int numOfHP(){
        return bagHP.size();
    }
    public int numOfMP(){
        return bagMP.size();
    }

}