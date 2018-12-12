public class AttackCommand implements Command {
    Hero hero;
    Monster monster;

    AttackCommand(Hero hero, Monster monster){
        this.hero = hero;
        this.monster = monster;
    }

    @Override
    public void execute() {
        double damege = hero.getDamage();
        monster.setGotDamage(damege);
        monster.setHealth(monster.getHealth() - monster.getGotDamage());

        double monster_dam =  monster.getDamage();
        hero.setGotDamage(monster_dam);
        hero.setHealth(hero.getHealth() - hero.getGotDamage());

        System.out.println("Attack was used -"+damege);
    }
}
