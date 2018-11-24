public interface GameUnit {
    double getHealth();
    void setHealth(double health);
    double getAttack();
    void setAttack(double attack);
    double getDamage();
    double getDefence();
    void setDefence(double defence);
    double getCriticalChance();
    void setCriticalChance(double chance);
    void increaseCriticalChance(double chance);
    void decreaseCriticalChance(double chance);
}
