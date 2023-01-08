package td.tower;

import java.util.List;
import java.util.ListIterator;

import td.monster.Monster;
import td.Block;

/**
 * Archery 
 * 
 * The archery tower will aim only one monster that has positive, non-zero 
 * health point. If there are multiple monster that are in range,
 * pick the one that is nearest to "home".
 * 
 * Propoerty of Archery tower:
 * Symbol : 'A'
 * Inital power: 5
 * Range : 3
 * cost : 5
 * upgrade power: 1 
 * upgrade cost: 2
 */
public class ArcheryTower extends Tower {

    // Declare fields based on Archery Tower properties

    private final char symbol = 'A';
    private static final int power = 5;
    private static final int range = 3;
    private static final int upgradePower = 1;
    private static final int upgradeCost = 2;
    private static final int cost = 5;


    /**
     *
     * @param row - goes to superclass
     * @param col - goes to superclass
     *  Other fields that we have declared as final so go to superclass
     */
    public ArcheryTower(int row, int col) {
        super (row, col, power, cost, upgradeCost, upgradePower, range);
    }


    @Override
    public void action(List<Block> blocks) {
        Monster iterMonster = null;
        ListIterator<Block> listIterator = blocks.listIterator();

        // Iterate through blocks
        while(listIterator.hasNext()) {
            Block iterBlock = (Block) listIterator.next();
            // if the distance between block and Monster(check if block is Monster and has positive hp!) is less or equal to range
            if (iterBlock.distance((Block)this) <= range && iterBlock instanceof Monster && ((Monster)iterBlock).getHp() >= 0) {
                // if it is the first Monster within the range that we found, assign to variable
                if (iterMonster == null) {
                    iterMonster = (Monster) iterBlock;
                    // if it is not the first monster, check with the previous distance
                    // if new distance is less, change iterMonster
                } else if (iterBlock.getCol() > iterMonster.getCol()) {
                    iterMonster = (Monster) iterBlock;
                }
            }
        }
        // Reduce hp of the closest monster if such is found
        // Damage = power of tower
        if (iterMonster != null) {
            iterMonster.reduceHP(this.getPower());
        }
    }

    /**
     *
     * @return Tower's symbol -> 'A'
     */
    @Override
    public char getSymbol() {
        return symbol;
    }
}
