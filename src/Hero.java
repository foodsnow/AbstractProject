public class Hero implements GameUnit {
    private double attack = 5,
                    defence = 250,
                    health = 100,
                    mana = 100,
                    magicDamage = 12,
                    gotDamage = 0;
    private int miss = 5,
                criticalChance = 5;
    private boolean isDefence = false,
                    isDead = false,
                    isMiss = false,
                    isCritical = false;
    private Inventory inventory;


    Hero(){
        inventory = new Inventory(this);
    }


    public double getMana(){return this.mana;}
    public void setMana(double mana){this.mana = mana;}
    @Override
    public double getAttack() {return this.attack;}
    @Override
    public double getCriticalChance() {return this.criticalChance;}
    @Override
    public double getDefence() {return this.defence;}
    @Override
    public double getHealth() {return this.health;}
    @Override
    public void setAttack(double attack) { this.attack = attack;}
    @Override
    public void setCriticalChance(int chance) {this.criticalChance = chance;}
    @Override
    public void increaseCriticalChance(double chance) {this.criticalChance += chance;}
    @Override
    public void decreaseCriticalChance(double chance) {this.criticalChance -= chance;}
    @Override
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

    @Override
    public double getDamage() {
        int mm = (int)(Math.random()*6);
        if (mm != this.miss){
            int cc = (int)(Math.random()*6);
            if (cc == this.criticalChance){
                isCritical = true;
                System.out.println("Critical");
                return this.attack*1.2;
            }
            else
                return this.attack;
        }
        else{
            isMiss = true;
            System.out.println("Miss");
            return 0;
        }
    }
    public void setGotDamage(double d){
        double hpPlus = defence/1000*1.5;
        if (isDefence) {
            gotDamage = (d - hpPlus)*0.4;
            isDefence = false;
        }
        else {
            gotDamage = d - hpPlus;
        }
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
    public void cleanAll(){
        this.cleanCritical();
        this.cleanMiss();
    }

    public boolean isEnoughMana(){
        return mana >= 20;
    }

    public double useMagic(){
        if (isEnoughMana()) {
            int mm = (int)(Math.random()*6);
            if (mm != miss) {
                mana -= 20;
                return this.magicDamage;
            } else {
                isMiss = true;
                mana -= 20;
                return 0;
            }
        }else return -1;
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
    public boolean isDead(){
        return this.isDead;
    }

}
