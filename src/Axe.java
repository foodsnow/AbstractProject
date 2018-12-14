public class Axe extends Hero {
    private Hero hero;

    public Axe(Hero p){
        this.hero = p;
        this.setName(hero.getName());
        setInventory(hero.getInventory());
        getInventory().setHero(this);
    }

    public double getAttack(){
        return this.hero.getAttack() + 10;
    }

    public double getMagicDamage(){
        return this.hero.getMagicDamage();
    }

    public double getDefence() {
        return this.hero.getDefence();
    }

    @Override
    public Inventory getInventory() {
        return hero.getInventory();
    }
}
