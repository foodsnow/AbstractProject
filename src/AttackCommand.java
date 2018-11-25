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
        monster.setHealth(monster.getHealth() - damege);
        System.out.println("Attack was used -"+damege);
    }
}
