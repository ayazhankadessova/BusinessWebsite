package td;

import java.util.List;

/**
 * A generic parent class of Monster and Tower.
 */
public abstract class Block {
    /**
     * which column the block is located
     */
    protected int col;
    /**
     * which row the block is located
     */
    protected int row;
    /**
     * to decide if the block is to be removed in the upcoming round
     */
    private boolean toRemove;
    /**
     * Is the block subjected to be removed in the upcoming round
     */
    public boolean isToRemove() {
        return toRemove;
    }
    /**
     * To remove a block in the upcoming round
     */
    public void remove() {
        toRemove = true;
    }
    /**
     * getter of row
     * @return - row of the block
     */
    public int getRow() {
        return row;
    }
    /**
     * getter of column
     * @return - column of the block
     */
    public int getCol() {
        return col;
    }
    /**
     * Constructor of the block
     */
    public Block(int row, int col) {
        this.row = row;
        this.col = col;
        // default is false
        this.toRemove = false;
    }
    /**
     * To compute the distance between two blocks.
     * We are using Manhanttan Distance, i.e., summing the absolute different of col and 
     * absolute different of row. For example, Block A at [3, 6] , Block B at [1, 3],
     * Distance = | 3 - 1 | + | 6 - 3 | = 2 + 3 = 5
     * 
     * @param b - the other block to measure from
     * @return - the distance between the block
     */
    public int distance(Block b) {
        return Math.abs(row - b.row) + Math.abs(col - b.col);
    }
    
    // EVERY Tower has different Action & Monster too
    /**
     * Each block should support its own action. A tower action means to fire.
     * A monster action means to walk.
     */
    public abstract void action(List<Block> blocks);
    /**
     * To return the character symbol that to display on screen.
     */
    public abstract char getSymbol();
}
