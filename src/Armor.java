public class Armor extends Hero {
    private Hero hero;
    public Armor(Hero p){
        this.hero = p;
        this.setName(hero.getName());
        setInventory(hero.getInventory());
        getInventory().setHero(this);
    }
    @Override
    public double getDefence() {
        return this.hero.getDefence() + 250;
    }

    public double getAttack(){
        return this.hero.getAttack();
    }

    public double getMagicDamage(){
        return this.hero.getMagicDamage();
    }
    public Inventory getInventory() {
        return hero.getInventory();
    }
}
