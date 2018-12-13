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
        hero.setGotDamage(monster_dam, monster.isMissed());
        hero.setHealth(hero.getHealth() - hero.getGotDamage());

        this.monster.setStunned();

        System.out.println("monster damage " + monster_dam);
        System.out.println("Defence was used");
    }
}
