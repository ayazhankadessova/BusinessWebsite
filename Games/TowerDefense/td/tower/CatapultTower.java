package td.tower;

import td.Block;
import td.Game;
import td.monster.Monster;

import java.util.Iterator;
import java.util.List;

/**
 * Catapult
 * 
 * A catapult works in the following way. It will target on
 * one monster among all monsters that are in range. When there
 * are more than one monsters in range, pick ANY monster with 
 * highest remaining health point.
 * 
 * Then, it hits the target monster and other monsters located in
 * its 8 neighthor adjacent cells. For example,
 * ----------------------
 * | a | b | c | e |
 * | d | f | g | h |
 * | i | j | k | l |  ...
 * | m | n | o | p |
 * ----------------------
 * * If g is the target monster, monsters <b, c, e, f, g, h, j, k, l>
 * will receive damage.
 * * If m is the target monster, monsters <i, j, m, n>
 * will receive damage.
 * 
 * Note: In the first example, even if monster b is out of the range 
 * of the Tower, as long as Tower can hit g, b will also receive damage.
 * 
 * Propoerty of Catapult:
 * Symbol : 'C'
 * Inital power: 4
 * Range : 6
 * cost : 7
 * upgrade power: 2 
 * upgrade cost: 3
 * 
 */
public class CatapultTower extends Tower {

    // Declare fields based on Catapult Tower properties
    private final char symbol = 'C';
    private static final int power = 4;
    private static final int range = 6;
    private static final int upgradePower = 2;
    private static final int upgradeCost = 3;
    private static final int cost = 7;

    /**
     *
     * @param row - goes to superclass
     * @param col - goes to superclass
     *  Other fields that we have declared as final so go to superclass
     */
    public CatapultTower(int row, int col) {
        super(row, col, power, cost, upgradeCost, upgradePower, range);
    }

//        STEPS:
//        1. Iterate through all the blocks
//        2. When we find a monster that is in range, assign it to a variable 'monster'
//        3. Keep iterating and when meet next suitable monster, compare hp of new and old monster -> If new has more hp, re assign
//        4. When no more blocks, check if we found any monster
//        5. Attack the monsters & damage adjacent monsters too by checking rows and columns of every block.
    @Override
    public void action(List<Block> blocks) {
        Monster monster = null;
        Iterator blockIterator = blocks.iterator();

        // Iterate through all blocks because we need to find Monster with highest hp
        while (true) {
            Monster iterMonster;
            do {
                Block iterBlock;
                do {
                    do {
                        // if it is the end of all blocks, check if we found a monster that suits
                        if (!blockIterator.hasNext()) {
                            // if not return
                            if (monster == null) {
                                return;
                            }

                            // start iterating again
                            blockIterator = blocks.iterator();
                            // if there is monster, attack adjacent monsters too
                            while (blockIterator.hasNext()) {
                                iterBlock = (Block) blockIterator.next();
                                // check if adjacent to monster
                                if (iterBlock instanceof Monster && Math.abs(iterBlock.getRow() - monster.getRow()) <= 1 && Math.abs(iterBlock.getCol() - monster.getCol()) <= 1) {
                                    ((Monster) iterBlock).reduceHP(this.getPower());
                                }
                            }
                            return;
                        }
                        // if we haven't reached the end, keep iterating also until we find the Monster
                        iterBlock = (Block) blockIterator.next();
                    } while (!(iterBlock instanceof Monster));
                    // which needs to be in range
                } while (!this.isInRange(iterBlock));
                iterMonster = (Monster) iterBlock;
            } while (monster != null && monster.getHp() >= iterMonster.getHp());
            // if we found a monster, compare it to previous found one
            // If new has more hp, change the monster
            monster = iterMonster;
        }
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}

