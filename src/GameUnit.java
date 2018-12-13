public interface GameUnit {
    double getHealth();
    void setHealth(double health);
    double getAttack();
    void setAttack(double attack);
    double getDamage();
    double getDefence();
    void setDefence(double defence);
    double getCriticalChance();
    void setCriticalChance(int chance);
    void setGotDamage(double d, boolean missed);
    double getGotDamage();
    boolean isCritical();
    void cleanCritical();
    boolean isMissed();
    void cleanMiss();
}
