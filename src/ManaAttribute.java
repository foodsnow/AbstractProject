public class ManaAttribute implements Attribute{
    int mana=5;
    int id = 1;

    @Override
    public int getAttributeID() {
        return id;
    }

    @Override
    public void add(Hero hero) {
        hero.setMana(hero.getMana()+mana);
    }
}
