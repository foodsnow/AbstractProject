public class DefenceCommand implements Command {
    Hero hero;

    DefenceCommand(Hero hero){
        this.hero = hero;
    }

    @Override
    public void execute() {
        this.hero.setIsDefenced(true);
        System.out.println("Defence was used");
    }
}
