public class Monster implements GameUnit{
    private double attack,
                defence,
                health,
                fullHP,
                gotDamage = 0;
    private int miss = 5,
                criticalChance = 5;
    private boolean isDead = false,
                    isMiss = false,
                    isCritical = false,
                    isStunned = false,
                    wasStunned = false;
    private Drop drop;

    Monster(double attack, double health, double defence, Drop drop){
        this.attack = attack;
        this.health = health;
        this.defence = defence;
        this.drop = drop;
        this.fullHP = health;
    }

    public double getFullHP(){
        return fullHP;
    }

    public double getAttack() {return this.attack;}

    public double getCriticalChance() {return this.criticalChance;}

    public double getDefence() {return this.defence;}

    public double getHealth() {return this.health;}

    public void setAttack(double attack) { this.attack = attack;}

    public void setCriticalChance(int chance) {this.criticalChance = chance;}

    @Override
    public void setDefence(double defence) {this.defence = defence;}
    @Override
    public void setHealth(double health) {
        this.health = health;
        if(this.health <= 0) {
            this.isDead = true;
            this.health = 0;
            System.out.println("Monster is dead");
        }
    }
    @Override
    public double getDamage() {
        if (!isDead) {
            if (!isStunned) {
                int mm = (int) (Math.random() * 6);
                if (mm != this.miss) {
                    int cc = (int) (Math.random() * 6);
                    if (cc == this.criticalChance) {
                        isCritical = true;
                        System.out.println("Critical");
                        return this.attack * 1.2;
                    } else{
                        System.out.println("Monster damage success");
                        return this.attack;}
                } else {
                    isMiss = true;
                    System.out.println("Miss");
                    return 0;
                }
            } else {
                cleanStunned();
                wasStunned = true;
                return 0;
            }
        }
        else
            return 0;
    }

    public Drop giveDrop(){
        return this.drop;
    }
    public void setGotDamage(double d, boolean missed){
        if (!isDead) {
            if (!missed) {
                if (!wasStunned) {
                    double hpPlus = defence / 1000 * 1.5;
                    gotDamage = d - hpPlus;
                } else {
                    gotDamage = d;
                    wasStunned = false;
                }
            }else {
                gotDamage = 0;
                wasStunned = false;
            }
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
    public boolean isStunned(){
        return isStunned;
    }
    public void cleanStunned(){
        isStunned = false;
    }
    public void setStunned(){
        isStunned = true;
    }

    public boolean isDead(){
        return this.isDead;
    }


}
