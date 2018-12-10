public class HealthItemCommand extends ItemCommand {
    Hero hero;

    HealthItemCommand(Hero hero){
        this.hero = hero;
    }

    @Override
    public void execute() {
        Inventory inventory = hero.getInventory();
        inventory.useHp();
    }
}
