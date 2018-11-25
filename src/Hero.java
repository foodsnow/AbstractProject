public class Hero implements GameUnit {
    private double attack = 5,
                    defence = 250,
                    health = 100,
                    criticalChance = 0.001,
                    mana = 100,
                    magicDamage = 12;
    private boolean isDefence = false,
                    isDead = false;


    public boolean isDefenced(){return isDefence;}
    public void setIsDefenced(boolean state){isDefence = state;}

    public double useMagic(){mana -= 50; return magicDamage;}
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
    public void setHealth(double health) {this.health = health;}

    @Override
    public double getDamage() {
        return this.attack;
    }
}
