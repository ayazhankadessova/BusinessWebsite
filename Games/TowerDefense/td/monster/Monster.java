package td.monster;

import td.Block;

import java.util.Iterator;
import java.util.List;
import td.tower.Tower;
/**
 * Monster class.
 * 
 * On each turn it moves to the right by one cell. If it steps on 
 * a tower (i.e., share the same coordinate as the tower), the tower
 * will be destroyed.
 * 
 * A monster reaches home will end the game. 
 * 
 * If a monster has health point 0 or negative, it cannot move anymore
 * and shall be removed from the game.
 * 
 * A monster shall return the last digit of its health as its symbol.
 * For example, if a monster has a health 10, it should return the character '0'
 * If a monster has a health of 31, it should return the character '1'
 * 
 * 
 * 
 * 
 */
public class Monster extends Block {
    protected int hp;

    /**
     *
     * @return health points of a monster
     */
    public int getHp() {
        return hp;
    }

    /**
     *
     * Constructor method
     * @param row - goes to super class
     * @param column - goes to superclass
     * @param hp - specific to Monster
     */
    public Monster(int row, int column, int hp) {
        super(row, column);
        this.hp = hp;
    }


    /**
     *
     * @param blocks
     */
    @Override
    public void action(List<Block> blocks) {
        // If monster does need to be removed, move it left
        if (this.isToRemove() == false) {
            // by incrementing column
            this.col++;
            Iterator blockIterator = blocks.iterator();

            // Iterate through Blocks
            Block iterBlock;
            do {
                // while there are next blocks
                if (blockIterator.hasNext() == false) {
                    return;
                }
                // current block is next block now
                iterBlock = (Block) blockIterator.next();
                // do action while iterator does not reach monster's row/column or stepped on Tower
            } while(!(iterBlock instanceof Tower) || iterBlock.getRow() != this.row || iterBlock.getCol() != this.col );

            // if steps on the tower, remove tower
            iterBlock.remove();
        }
    }

    /**
     * To get the last digit of health points for displaying the Monster
     * @param number
     * @return last digit
     */
    private int lastDigit(int number) {
        return number % 10;
    }

    /**
     *
     * @return the last digit of health points
     */
    @Override
    public char getSymbol() {
        int lastDigitToChar = 48 + lastDigit(hp);
        return (char)lastDigitToChar;
    }

    /**
     *
     * @param damage - specified damage each tower can inflict
     */
    public void reduceHP(int damage) {
        // reduce health points by the specified damage each tower can inflict
        this.hp = hp - damage;

        // if health points is - or negative, remove monster from the game
        if (this.hp <= 0) {
            this. remove();
        }
    }

    @Override
    public String toString() {
        return "Monster: " + this.getSymbol() + " [" + this.getHp() + "]";
    }


}

