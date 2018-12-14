import javafx.scene.image.Image;

public class Hero implements GameUnit {
    private String name = "player";
    String attack_desc = "ESHKERE!!!!!!";
    String defence_decs = "My grandma beats me harder";
    String magic_desc = "AVADA KETABRA";
    private double attack = 7,
                    defence = 250,
                    health = 100,
                    mana = 100,
                    magicDamage = 12,
                    gotDamage = 0,
                    manaCost = 20;
    private int miss = 5,
                criticalChance = 5;
    private boolean isDefence = false,
                    isDead = false,
                    isMiss = false,
                    isCritical = false;
    private Image image = new Image("images/hero1.gif");
    private Inventory inventory;

    Hero(String name, Inventory inventory){
        inventory = new Inventory(this);
        this.name = name;
    }
    Hero(){
        inventory = new Inventory(this);
    }

    public void setName(String name ){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public double getMana(){return this.mana;}

    public void setMana(double mana){this.mana = mana;}

    public double getAttack() {return this.attack;}

    public double getCriticalChance() {return this.criticalChance;}

    public double getDefence() {
        return this.defence;
    }

    public double getHealth() {return this.health;}

    public void setAttack(double attack) { this.attack = attack;}

    public void setCriticalChance(int chance) {this.criticalChance = chance;}
    public void setDefence(double defence) {this.defence = defence;}
    @Override
    public void setHealth(double health) {
        this.health = health;
        if(this.health <= 0) {
            this.isDead = true;
            this.health = 0;
            System.out.println("Hero is dead");
        }
    }
    public double getMagicDamage(){
        return magicDamage;
    }
    @Override
    public double getDamage() {
        if (!isDead) {
            int mm = (int) (Math.random() * 6);
            if (mm != this.miss) {
                int cc = (int) (Math.random() * 6);
                if (cc == this.criticalChance) {
                    isCritical = true;
                    System.out.println("Critical");
                    return getAttack() * 1.2;
                } else
                    return getAttack();
            } else {
                isMiss = true;
                System.out.println("Miss");
                return 0;
            }
        }else
            return 0;
    }
    public void setGotDamage(double d, boolean missed){
        if (!isDead) {
            if (!missed) {
                double hpPlus = getDefence()/ 1000 * 1.5;
                if (isDefence) {
                    gotDamage = (d - hpPlus) * 0.4;
                    isDefence = false;
                } else {
                    gotDamage = d - hpPlus;
                }
            }else
                gotDamage = 0;
        }else
            gotDamage = 0;
    }
    public double getGotDamage(){
        return gotDamage;
    }

    public boolean isCritical(){
        return isCritical;
    }
    public void cleanCritical(){
        isCritical = false;
    }

    public boolean isMissed(){
        return isMiss;
    }
    public void cleanMiss(){
        isMiss = false;
    }

    public boolean isEnoughMana(){
        return mana >= manaCost;
    }

    public double useMagic(){
        if (!isDead) {
            if (isEnoughMana()) {
                int mm = (int) (Math.random() * 6);
                if (mm != miss) {
                    mana -= manaCost;
                    return getMagicDamage();
                } else {
                    isMiss = true;
                    mana -= manaCost;
                    return 0;
                }
            } else return 0;
        }else
            return 0;
    }

    public boolean isDefenced(){
        return isDefence;
    }

    public void setIsDefenced(boolean state){
        isDefence = state;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory){
        this.inventory = inventory;
    }
    public boolean isDead(){
        return this.isDead;
    }

    public Image getImage(){
        return image;
    }

}
