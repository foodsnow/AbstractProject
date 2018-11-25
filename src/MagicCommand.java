public class MagicCommand implements Command {
    Hero hero;
    Monster monster;

    MagicCommand(Hero hero, Monster monster){
        this.hero = hero;
        this.monster = monster;
    }

    @Override
    public void execute() {
        double magicDamage = this.hero.useMagic();
        this.monster.setHealth(monster.getHealth() - magicDamage);
        System.out.println("Magic Spell -50");
    }
}
