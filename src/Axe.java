public class Axe extends Hero {
    Hero p;

    Axe(Hero p){
        this.p = p;
    }

    @Override
    public double getDamage() {return this.p.getAttack() + 50;}

}
