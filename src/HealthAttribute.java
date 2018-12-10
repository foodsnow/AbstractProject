public class HealthAttribute implements Attribute {
    double health=10;
    int id = 0;

    @Override
    public int getAttributeID() {
        return id;
    }

    @Override
    public void add(Hero hero) {
        hero.setHealth(hero.getHealth()+health);
    }
}
