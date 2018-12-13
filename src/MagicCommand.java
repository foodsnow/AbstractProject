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
            monster.setGotDamage(magicDamage, hero.isMissed());
            this.monster.setHealth(monster.getHealth() - monster.getGotDamage());

            double monster_dam =  monster.getDamage();
            hero.setGotDamage(monster_dam, monster.isMissed());
            hero.setHealth(hero.getHealth() - hero.getGotDamage());


            System.out.println("Magic Spell "+hero.getMana());
            System.out.println("monster damage " + monster_dam);
        }else
            System.out.println("Not enough mana");
    }
}
