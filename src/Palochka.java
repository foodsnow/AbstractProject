public class Palochka extends Hero {
    private Hero hero;

    public Palochka(Hero p){
        this.hero = p;
        this.setName(hero.getName());
        setInventory(hero.getInventory());
        getInventory().setHero(this);
    }


    public double getDefence() {
        return this.hero.getDefence();
    }

    public double getAttack(){
        return this.hero.getAttack();
    }

    public double getMagicDamage() {
        return this.hero.getMagicDamage() + 8;
    }
    public Inventory getInventory() {
        return hero.getInventory();
    }

}