package td.tower;

import td.Block;
import td.Game;
import td.monster.Monster;

import java.util.List;
import java.util.ListIterator;

/**
 * Laser Tower
 * 
 * A Laser tower can shoot all monsters on its left (whose has a
 * smaller col than the tower) on the same row. 
 * 
 * All monster will receive the same number of damage.
 * 
 * Propoerty of laser tower:
 * Symbol : 'L'
 * Inital power: 4
 * Range : N/A (can place any value here)
 * cost : 7
 * upgrade power: 2 
 * upgrade cost: 3
 */
public class LaserTower extends Tower{
    // Declare fields based on Laser Tower properties
    private final char symbol = 'L';
    private static final int power = 4;
    private static final int range = 12; // random number because no range
    private static final int upgradePower = 2;
    private static final int upgradeCost = 3;
    private static final int cost = 7;
    public LaserTower(int row, int col) {
        super(row, col, power, cost, upgradeCost, upgradePower, range);
    }

    @Override
    public void action(List<Block> blocks) {
        ListIterator<Block> listIterator = blocks.listIterator();

        while(listIterator.hasNext()) {
            Block iterBlock = listIterator.next();
            if (iterBlock instanceof Monster && iterBlock.getCol() <= this.col && iterBlock.getRow() == row) {
                ((Monster)iterBlock).reduceHP(this.getPower());
            }
        }
    }

    // same row, col should be less
//    public boolean damage(Block monster) {
//        return monster.getRow() == row && monster.getCol() <= col;
//    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
