public class DefenceCommand implements Command {
    Hero hero;
    Monster monster;

    DefenceCommand(Hero hero, Monster monster){
        this.hero = hero;
        this.monster = monster;
    }

    @Override
    public void execute() {
        this.hero.setIsDefenced(true);

        double monster_dam =  monster.getDamage();
        hero.setGotDamage(monster_dam);
        hero.setHealth(hero.getHealth() - hero.getGotDamage());

        this.monster.setStunned();
        System.out.println("Defence was used");
    }
}
