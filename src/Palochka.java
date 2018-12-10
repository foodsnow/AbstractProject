public class Palochka extends Hero {
    Hero hero;

    Palochka(Hero p){
        this.hero = p;
    }


    public double useMagic() {
        return this.hero.useMagic() + 88;
    }


}