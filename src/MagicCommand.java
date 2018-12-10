public class MagicCommand implements Command {
    Hero hero;
    Monster monster;

    MagicCommand(Hero hero, Monster monster){
        this.hero = hero;
        this.monster = monster;
    }

    @Override
    public void execute() {
        if(hero.isEnoughMana()) {
            double magicDamage = this.hero.useMagic();
            monster.setGotDamage(magicDamage);
            this.monster.setHealth(monster.getHealth() - magicDamage);
            System.out.println("Magic Spell "+hero.getMana());
        }else
            System.out.println("Not enough mana");
    }
}
