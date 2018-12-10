public class ManaItemCommand extends ItemCommand {
    Hero hero;

    ManaItemCommand(Hero hero){
        this.hero = hero;
    }

    @Override
    public void execute() {
        Inventory inventory = hero.getInventory();
        inventory.useMp();
    }
}
