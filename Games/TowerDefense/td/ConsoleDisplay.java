package td;

import java.util.Iterator;
import java.util.Scanner;

import td.tower.*;

import static td.GUIDisplay.DisplayBlock.HEIGHT;
import static td.GUIDisplay.DisplayBlock.WIDTH;

/**
 * A View class. To display the information on screen and also 
 * to take user's control.
 */
public class ConsoleDisplay implements Displayable {
    /**
     * The controller object game.
     */
    protected Game game;

    /**
     * Entry point. Don't touch
     */
    public static void main(String[] args) {
        new ConsoleDisplay();
    }

    /**
     * Constructor. To construct the game object and call game.run();
     */
    public ConsoleDisplay() {
        this.game = new Game(this);
        game.run();
    }

    /**
     * To display the score, money, map and character on screen.
     */
    @Override
    public void display() {
        // Display Score and Money
        System.out.printf("Score: %d | Money: %d\n", this.game.getScore(), this.game.getMoney());
        // put null, in the future we will re-use this method
        this.displayMap((Block)null);
    }

    private void displayMap(Block block) {
        for(int i = 0; i < 16; ++i) {
            System.out.print("-");
        }

        System.out.println();

        // create 2d array
        char[][] map = new char[Game.HEIGHT][Game.WIDTH];
        int row;
        int col;
        // If we want to display Tower
        if (block != null && block instanceof Tower) {
            for(row = 0; row < Game.HEIGHT; ++row) {
                for(col = 0; col < Game.WIDTH; ++col) {
                    // Check ranges
                    if (((Tower)block).isInRange(new ArcheryTower(row, col))) {
                        map[row][col] = '#';
                    }
                }
            }
        }

        // If not empty, show *
        if (block != null) {
            map[block.getRow()][block.getCol()] = '*';
        }

        Iterator iteratorBlocks = this.game.blocks.iterator();

        // Assign symbols to map that we will use for display
        while(iteratorBlocks.hasNext()) {
            Block iterblock = (Block)iteratorBlocks.next();
            if (iterblock.getCol() >= 0 && iterblock.getCol() <= WIDTH && iterblock.getRow() >= 0 && iterblock.getRow() <= HEIGHT) {
                map[iterblock.getRow()][iterblock.getCol()] = iterblock.getSymbol();
            }
        }

        // If any element is 0, no value -> empty print
        // If not 0, print corresponding symbol
        for(row = 0; row < Game.HEIGHT; ++row) {
            for(col = 0; col < Game.WIDTH; ++col) {
                if (map[row][col] == 0) {
                    System.out.print(' ');
                } else {
                    System.out.print(map[row][col]);
                }
            }
            System.out.println("oooo");
        }

        // Print information about the block, eg Tower [C] Power: 4 Upgrade cost: 3
        if (block != null) {
            System.out.println(block);
        }
    }
    /**
     * To accept user input (build tower, upgrade tower, view blocks).
     * 
     */
    @Override
    public void userInput() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            printInstruction();
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        return;
                    default:
                        throw new InvalidInputException("Invalid option! Pick only 1-4");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            display();
        }
    }

    @Override
    public void gameOver() {
        System.out.println("Game over!");
    }

    /**
     * Instrictions method.
     * 
     */
    private void printInstruction() {
        System.out.println("Please pick one of the following: ");
        System.out.println("1. View a tower/monster");
        System.out.println("2. Build a new Tower");
        System.out.println("3. Upgrade a Tower");
        System.out.println("4. End a turn");
    }
    // Option 1: View a Tower or a Monster
    private void option1() throws td.InvalidInputException {
        System.out.println("Enter the coordinate of the tower/monster row followed by column");
        Scanner in = new Scanner(System.in);
        int row = in.nextInt();
        int col = in.nextInt();
        Block displayBlock = game.getBlockByLocation(row, col);
        // if there is something in this location, display that block
        if (displayBlock!=null) {
            // re-using method here!
            this.displayMap(displayBlock);
        } else {
            // thow exception
            throw new td.InvalidInputException();
        }
    }

    // Option 2: Build Towers
    private void option2() throws td.InvalidInputException {
        // Print prices
        System.out.println("You can build the following towers:");
        System.out.println("1. ArcheryTower ($5); 2. LaserTower ($7);3. CatapultTower ($7)");
        Scanner in = new Scanner(System.in);
        int choiceTower = in.nextInt();
        System.out.println("Which row?");
        int row = in.nextInt();
        System.out.println("Which column?");
        int col = in.nextInt();
        // Check if we can build
        if (this.game.build(choiceTower, row, col) == false) {
            throw new td.InvalidInputException("Sorry, the option is invalid. Please check if you have enough money. You can only build on a cell without any monster or tower. You cannot build on column 0 too!");
        }
    }

    // Option 3: Upgrade Tower
    private void option3() throws td.InvalidInputException{
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the row of your tower: ");
        int row = in.nextInt();
        System.out.println("Which column? ");
        int col = in.nextInt();

        // Check if we can upgrade
        if (game.upgrade(row, col) == false) {
            // copy from jar
            throw new td.InvalidInputException("Sorry, the option is invalid. Please check if you have enough money to upgrade and there is already a tower for you to upgrade.");        }
    }

}
