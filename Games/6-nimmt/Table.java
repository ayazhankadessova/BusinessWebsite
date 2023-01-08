import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Table {
    /**
     * Total number of player. Use this variable whenever possible
     */
    private static final int NUM_OF_PLAYERS = 4;
    /**
     * Total number of cards used in this game. Use this variable whenever possible
     */
    private static final int TOTAL_NUMBER_OF_CARD = 104;
    /**
     * The four stacks of cards on the table.
     */
    private Card[][] stacks = new Card[4][6];
    /**
     * This number of cards of each stack on the table. For example, if the variable
     * stacks stores
     * -------------------------
     * | 0 | 10 13 14 -- -- -- |
     * | 1 | 12 45 -- -- -- -- |
     * | 2 | 51 55 67 77 88 90 |
     * | 3 | 42 -- -- -- -- -- |
     * -------------------------
     * 
     * stacksCount should be {3, 2, 6, 1}.
     * 
     */
    private int[] stacksCount = new int[4];
    /**
     * The array of players
     */
    private Player[] players = new Player[NUM_OF_PLAYERS];

    /**
     * Default constructor
     * 
     * 1. Initialize cards for play. Construct enough number of cards
     * to play. These cards should be unique (i.e., no two cards share the same
     * number). The value of card must be between 1 to 104. The number of bullHead
     * printed on each card can be referred to the rule.
     * 
     * 2. Initialize four player. The first player should be a human player, call
     * "Kevin". The other player should be a computer player. These computer player
     * should have the name "Computer #1", "Computer #2", "Computer #3".
     * 
     * 3. Deal randomly 10 cards to each player. A card can only be dealt to one
     * player. That is, no two players can have the same card.
     * 
     * 4. Deal a card on each stack. The card dealt on the stack should not be dealt
     * to any player. Card dealt on each stack should also be unique (no two stack
     * have the same card).
     * 
     */
    public Table() {
        Card[] cardsForPlay = this.initializeCards();
        // start boolean array not to allow duplicates
        boolean[] cardsBoolean = new boolean[TOTAL_NUMBER_OF_CARD];

        int i;
        int j;
        for(i = 0; i < NUM_OF_PLAYERS; ++i) {
            // Initialize four players. One player is real person, others are computers.
            if (i == 0) {
                this.players[i] = new Player("Ayazhan");
            } else {
                this.players[i] = new Player();
            }
            // Deal 10 cards for each player. Choose till we get a card that is still false in boolean, which means
            // it is not in the game yet + no two players can have same cards.
            for(j = 0; j < 10; ++j) {
                int randomChoice;
                do {
                    randomChoice = ThreadLocalRandom.current().nextInt(0, TOTAL_NUMBER_OF_CARD);
                    // if cardsBoolean[randomChoice] == true -> card is already is use
                } while(cardsBoolean[randomChoice]);

                // update boolean array to indicate that now this card is in use
                cardsBoolean[randomChoice] = true;
                // deal this card to player, hand will get sorted in ascending order & handcount will be incremented for the player
                this.players[i].dealCard(cardsForPlay[randomChoice]);
            }
        }

        int indexforStack;
        // Deal a card on each stack. There are 4 stacks.
        for(i = 0; i < 4; ++i) {
            // card should be unique, so keep trying while reach a card that is not in play
            do {
                indexforStack = ThreadLocalRandom.current().nextInt(0, TOTAL_NUMBER_OF_CARD);
            } while(cardsBoolean[indexforStack]);

            // update boolean array to indicate that now this card is in use
            cardsBoolean[indexforStack] = true;
            // update that stack 
            this.stacks[i][0] = cardsForPlay[indexforStack];
            // update number of cards in this stack -> now stack has one card
            this.stacksCount[i] = 1;
        }
    }

    /**
     * This method is to find the correct stack that a card should be added to
     * according to the rule. It should return the stack among which top-card of
     * that stack is the largest of those smaller than the card to be placed.
     * 
     * In case the card to be place is smaller than the top cards of all stacks,
     * return -1.
     * 
     * @param card - the card to be placed
     * @return the index of stack (0,1,2,3) that the card should be place or -1 if
     *         the card is smaller than all top cards
     */
    public int findStackToAdd(Card card) {
        int stack = -1;
        // keep track of largest stackcard
        int numberCard = -1;

        // check four stacks
        for(int i = 0; i < 4; ++i) {
            // get top card of the stack based on how many cards the stack has -1
            Card stackCard = this.stacks[i][this.stacksCount[i] - 1];
            // we need the largest top card, so if the current stack has number larger than previous...
            if (stackCard.getNumber() < card.getNumber() && stackCard.getNumber() > numberCard) {
                // ...change number card
                numberCard = stackCard.getNumber();
                // ...change stack
                stack = i;
            }
        }
        return stack;
    }

    /**
     * To print the stacks on the table. Please refer to the demo program for the
     * format. Within each stack, the card should be printed in ascending order,
     * left to right. However, there is no requirement on the order of stack to
     * print.
     */
    public void print() {
        System.out.println("--------------Table--------------");

        // print each stack
        for(int i = 0; i < 4; ++i) {
            System.out.print("Stack " + i + ":");

            // print every card in stack
            for(int j = 0; j < this.stacksCount[i]; ++j) {
                this.stacks[i][j].print();
            }
            System.out.println();
        }

        System.out.println("---------------------------");
    }

    /**
     * Initialize 104 cards to Play.
     */
    public Card[] initializeCards() {
        Card[] cards = new Card[TOTAL_NUMBER_OF_CARD];

        for(int i = 1; i < TOTAL_NUMBER_OF_CARD + 1; ++i) {
            // The value of cards must be 1-104.
            cards[i - 1] = new Card(i);
        }
        return cards;
    }

    /**
     * This method is the main logic of the game. Create a loop for 10
     * times (running 10 rounds). In each round all players will need to play a
     * card. These cards will be placed to the stacks from small to large according
     * to the rule of the game.
     * 
     * In case a player plays a card smaller than all top cards, he will be
     * selecting one of the stack of cards and take them to his/her own score pile.
     * If the player is a human player, he will be promoted for selection. If the
     * player is a computer player, the computer player will select the "cheapest"
     * stack, i.e. the stack that has fewest bull heads. If there are more than
     * one stack having fewest bull heads, selecting any one of them.
     */

    public void runApp() {
        
        for(int i = 0; i < 10; ++i) {
            // print Table
            this.print();
            // Keep track of what cards players play
            Card[] playersCards = new Card[4];
            
            // 1. Players Play a Card
            for(int j = 0; j < 4; ++j) {
                // Let real person play
                if (j == 0) {
                    playersCards[j] = this.players[j].playCard();
                } else {
                    // computer -> random pick
                    playersCards[j] = this.players[j].playCardRandomly();
                }
                // Print Every Players chosen card
                System.out.print(this.players[j].getName() + ":");
                playersCards[j].print();
                System.out.println();
            }

            // 2. Starting from the smallest card, put cards to stacks

            // Fine the smallest card
            int minimumNumber = 0;

            for(int indexMain = 0; indexMain < 4; ++indexMain) {
                int maximum = 104;
                int indexPlayer = -1;

                // check if card is more than minimum which will be changed everytime we put a card on stack
                // maximum will be checked and chenged, to find if any nuber is less than that
                for(int indexOfPlayer = 0; indexOfPlayer < 4; ++indexOfPlayer) {
                    if (playersCards[indexOfPlayer].getNumber() <= maximum && playersCards[indexOfPlayer].getNumber() > minimumNumber) {
                        // now this number is max, if there is something less than it, it will be max 
                        maximum = playersCards[indexOfPlayer].getNumber();
                        // get index
                        indexPlayer = indexOfPlayer;
                    }
                }

                // 3. get smallest card, Add to Stack
                Card smallestCard = playersCards[indexPlayer];
                System.out.println("Place the card " + smallestCard + " for " + this.players[indexPlayer].getName());
                int stackToAdd = this.findStackToAdd(smallestCard);
                // 4. if card is smaller than all stack top cards...
                if (stackToAdd == -1) {
                    // let player choose stack to take
                    if (indexPlayer == 0) {
                        Scanner in = new Scanner(System.in);

                        do {
                            do {
                                System.out.println("Pick a stack to collect the cards, between 0-3");
                                stackToAdd = in.nextInt();
                            } while(stackToAdd < 0);
                        } while(stackToAdd > 3);
                        // choose cheapest stack for computer, with the least number of bullheads
                    } else {
                        // base case -> choose numberOfBullHeads of First Stack and compare others itreatively
                        int numberOfBullheads = this.getBullHeads(0);
                        stackToAdd = 0;

                        // if next stack has less, change the vars
                        for(int indexOfStack = 1; indexOfStack < 4; ++indexOfStack) {
                            if (numberOfBullheads > this.getBullHeads(indexOfStack)) {
                                numberOfBullheads = this.getBullHeads(indexOfStack);
                                stackToAdd = indexOfStack;
                            }
                        }
                    }

                    // move cards from stack to players score pile
                    // will change players pile 
                    this.players[indexPlayer].moveToPile(this.stacks[stackToAdd], this.stacksCount[stackToAdd]);
                    // now that pile is empty
                    this.stacksCount[stackToAdd] = 0;
                }

                // max number of cards in stack is 6
                // if by adding it will exceed, move that pile to players score pile
                if (this.stacksCount[stackToAdd] == 5) {
                    this.players[indexPlayer].moveToPile(this.stacks[stackToAdd], this.stacksCount[stackToAdd]);
                    // now that pile is empty
                    this.stacksCount[stackToAdd] = 0;
                }

                // add card to the needed stack
                this.stacks[stackToAdd][this.stacksCount[stackToAdd]++] = smallestCard;
                // change minimumNumber to repeat the procedure for the larger card
                minimumNumber = smallestCard.getNumber();
            }
        }

        // print score
        for (Player p : players) {
            System.out.println(p.getName() + " has a score of " + p.getScore());
            // print scores pile
            p.printPile();
        }
    }

    /**
     * Programme main.
     * 
     * @param args - no use.
     */
    public static void main(String[] args) {
        new Table().runApp();
    }

     /**
     * Get number of bullheads for a stack with a given number. 
     * 
     * @param stackNumber - no of the stack.
     * @return - number of total bullheads in stack.
     */
    private int getBullHeads(int stackNumber) {
        int total = 0;

        for(int i = 0; i < this.stacksCount[stackNumber]; ++i) {
            total += this.stacks[stackNumber][i].getBullHead();
        }
        return total;
    }
}
