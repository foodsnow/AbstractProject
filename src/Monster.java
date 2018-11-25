public class Monster implements GameUnit{
    private double attack = 5,
            defence = 250,
            health = 100,
            criticalChance = 0.001;
    private boolean isDead = false;


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
    public void setCriticalChance(double chance) {this.criticalChance = chance;}
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
            System.out.println("Monster is dead");
        }
    }

    @Override
    public double getDamage() {
        if(!isDead)
            return this.attack;
        else
            return 0;
    }


}
