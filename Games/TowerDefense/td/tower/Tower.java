
package td.tower;
import td.Block;
import td.monster.Monster;

/**
 * A Tower class.
 */
public abstract class Tower extends Block {

    private int power;
    private int cost;
    private int upgradeCost;
    private int range;
    private int upgradePower;

    /**
     * Constructor of tower class.
     */
    public Tower(int row, int col, int power, int cost, int upgradeCost, int upgradePower, int range) {
        super(row, col);
        this.power = power;
        this.cost = cost;
        this.upgradeCost = upgradeCost;
        this.upgradePower = upgradePower;
        this.range = range;
    }

    /**
     * A toString method.
     */
    @Override
    public String toString() {
        return "Tower [" + getSymbol() + "] Power: " + getPower() + " Upgrade cost: " + getUpgradeCost();
    }

    /**
     * To determine if a block is in its range. This method will help 
     * the display to shade the shooting range of a tower.
     *  Use distance from Block class.
     */
    public boolean isInRange(Block b) {
        return distance(b) <= range;
    }

    /**
     * To get the power of the tower
     * @return - attack power
     */
    public int getPower() {
        return power;
    }
    /**
     * To get the building cost of the tower
     * @return - building cost
     */
    public int getCost() {
        return cost;
    }
    /**
     * To get the upgrade cost of the tower
     * @return - upgrade cost
     */
    public int getUpgradeCost() {
        return upgradeCost;
    }
    /**
     * To upgrade the tower. 
     */
    public void upgrade() {
        power += upgradePower;
    }
}
