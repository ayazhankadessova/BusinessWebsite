package td;

import td.monster.Monster;
import td.tower.ArcheryTower;
import td.tower.CatapultTower;
import td.tower.LaserTower;
import td.tower.Tower;

import javax.swing.plaf.synth.SynthTextPaneUI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * This is the controller class. It governs the game logic. 
 * 
 */
public class Game {
    /**
     * The height of the game. Use this constant whenever possible
     */
    public static final int HEIGHT = 4;
    /**
     * The width of the game. Use this constant whenever possible
     */
    public static final int WIDTH = 12;

    /**
     * How many monster killed
     */
    private int score;
    /**
     * How much money the player has. Money is required for building tower
     */
    private int money;
    /**
     * A list of tower/monster that the game is recording.
     * This list should be maintained a structure of first-tower-then-monster, i.e.
     * | Tower1 | Tower2 | Tower3 | Monster1 | Monster 2 | Monster 3 | Monster 4 |
     * There is no additional requirement for ordering the towers or ordering monsters, i.e.
     * the above list can also be recorded in implementation as
     * | Tower3 | Tower1 | Tower2 | Monster4 | Monster3 | Monster 1 | Monster 2 |
     *
     *
     */
    List<Block> blocks;
    /**
     * A "View" so that it display the content to user and also take input from users.
     * The display should interact with Game properly. 
     */
    private Displayable display;
    /**
     * The constructor of the game
     */
    public Game(Displayable display) {
        this.score = 0;
        this.display = display;
        this.blocks = new ArrayList();
        // money is 10 at start
        this.money = 10;

    }
    /**
     * This governs the main logic of the game.
     */
    public void run() {
        blocks.add(new Monster(ThreadLocalRandom.current().nextInt(0, HEIGHT), 0, 10)); //increase HP per each 3 turn

        for (int turn = 0; !gameOver(); turn++) {
            //1. User input
            display.display();
            display.userInput();
            //2. Block moves
            for (Block b: blocks) {
                b.action((blocks));
            }
            //3. Clean up towers and monsters
            for (int i = blocks.size() - 1; i >= 0 ; i--) {
                Block b = blocks.get(i);
                if (!b.isToRemove())
                    continue;
                if (b instanceof Monster) {
                    money += 5;
                    score++;
                }
                blocks.remove(b);
            }
            //4. Generate new monsters
            int row = ThreadLocalRandom.current().nextInt(0, HEIGHT);
            blocks.add(new Monster(row, 0, 10 + 2 * (turn / 3))); //increase 2 HP per each 3 turn
        }
        display.gameOver();
    }

    /**
     * Check if any monster reaches "Home".
     */
    private boolean gameOver() {

        // STEPS:
        // 1. Iterate through all the blocks
        // 2. If we have iterated till column 12 and did not meet any monsters, not over
        // 3. If we have not finished iterating and met a monster, over
        Block iterBlock;
        Iterator iteratorBlocks = blocks.iterator();
        do {
            if (iteratorBlocks.hasNext() == false) {
                return false;
            }
            iterBlock = (Block)iteratorBlocks.next();
        } while(iterBlock.getCol() < WIDTH || !(iterBlock instanceof Monster));
        return true;
    }

    /**
     * Getter for score
     */
    public int getScore() {
        return score;
    }
    /**
     * Getter for money
     */
    public int getMoney() {
        return money;
    }
    /**
     * Given a coordinate return the block at that position.
     *
     * If there is no block is located at that position, return null
     * According to the game logic, there will be only one tower
     * or monster on a block at each time.
     *
     * @param row - row
     * @param col - column
     * @return - return the block which at that position.
     */
    public Block getBlockByLocation(int row, int col) {
        Iterator iteratorBlocks = blocks.iterator();

        // STEPS:
        // 1. Iterate through all the blocs
        // 2. If we find a match, return that block
        // 3. If not, return null
        Block iterBlock;
        do {
            if (!iteratorBlocks.hasNext()) {
                return null;
            }
            iterBlock = (Block)iteratorBlocks.next();
        } while(!(iterBlock.row == row && iterBlock.col == col));

        return iterBlock;
    }

    // IMPORTANT: When we add Towers, We add them in the beginning!
    /**
     * Called from Display to build a tower.
     * Please note that a tower cannot be built
     * - on column 0;
     * - on a monster;
     * - on another tower; or
     * - if insufficient money.
     *
     * @param tower - which tower. 1 = ArcheryTower; 2 = LaserTower; 3 = CatapultTower
     * @param row - which row to build
     * @param col - which column to build
     *
     * @return true if success, false if fail. Fail can be due to incorrect coordinate
     * (out-of-bound/build on a monster/build on another tower..) or insufficient money.
     */
    public boolean build(int tower, int row, int col) {

        if (col == 0) {
            return false;
            // input is within the range, but
        } else if(row>=0 && row< HEIGHT && col >=0 && col < WIDTH) {
            // this spot is taken
            if (this.getBlockByLocation(row, col) != null) {
                return false;
                // lets check if we have enough money
            } else {
                if(tower == 1) {
                    // if not enough money. Archery cost - 5
                    if (this.money < 5) {
                        return false;
                    }
                    ArcheryTower newArchery = new ArcheryTower(row,col);
                    // add in the beginning
                    this.money -= newArchery.getCost();
                    this.blocks.add(0, newArchery );
                } else if (tower == 2) {
                    // if not enough money. Laser Cost = 7
                    if (this.money < 7) {
                        return false;
                    }
                    LaserTower newLaser = new LaserTower(row,col);
                    this.money -= newLaser.getCost();
                    this.blocks.add(0, newLaser);
                } else if (tower ==3) {
                    // if not enough money. Catapult Cost = 7
                    if (this.money < 7) {
                        return false;
                    }
                    CatapultTower newCatapult = new CatapultTower(row,col);
                    this.money -= newCatapult.getCost();
                    this.blocks.add(0, newCatapult );
                }
                return true;
            }
            // not in range
        } else {
            return false;
        }
    }

    /**
     * Called from Display to upgrade a tower.
     *
     * @param row - the row of the tower be upgraded
     * @param col - the column of the tower be upgraded
     * @return - true if upgrade is success, false if  upgrade is not possible.
     * Fail can be due to incorrect position or insufficient money.
     */
    public boolean upgrade(int row, int col) {

        // Get the block
        Block thisblock = getBlockByLocation(row, col);

        // check if there is tower in this block
        if (thisblock instanceof  Tower) {
            // If yes, cast to Tower
            Tower thisTower = (Tower) thisblock;
            // check if we have enough money for upgrade
            if (this.money >= thisTower.getUpgradeCost()) {
                // reduce money by upgrade cost for the tower
                money -= thisTower.getUpgradeCost();
                thisTower.upgrade();
                // return success
                return true;
            }
        }
        return false;
    }
}
