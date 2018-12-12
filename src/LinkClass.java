public class LinkClass {
    private Hero hero;
    private Monster monster;

    LinkClass(Hero hero, Monster monster){
        this.hero = hero;
        this.monster = monster;
    }

    public boolean isMonsterDead(){
        return monster.isDead();
    }
    public boolean isHeroDead(){
        return hero.isDead();
    }

    public void giveDroptoHero(){
        if (isMonsterDead()){
            hero.getInventory().loot(monster.giveDrop());
        }
    }
}
