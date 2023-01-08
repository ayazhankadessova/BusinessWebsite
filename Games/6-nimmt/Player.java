import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    /**
     * The cards held on a player hand
     */
    private Card[] hand;
    /**
     * The number of card held by the player. This variable should be maintained
     * to match array hand.
     */
    private int handCount;
    /**
     * A dynamic array that holds the score pile.
     */
    private Card[] pile;
    /**
     * The name of the player
     */
    private String name;
    /**
     * A static variable that tells how many player has been initialized
     */
    private static int count = 1;

    /**
     * Other constructor that specify the name of the player.
     */
    public Player(String name) {
        // player name
        this.name = name;
        // cards on a players hand - initially 10 (but we don't know what cards these are at first).
        this.hand = new Card[10];
        // before the cards are dealt, the number is set to zero.
        this.handCount = 0;
        // no cards in the scores pile in the beginning
        this.pile = new Card[0];
    }

    /**
     * Default constructor that set the name of the player as "Computer #1",
     * "Computer #2", "Computer #3"...
     * The number grows when there are more computer players being created.
     */
    public Player() {
        // Give names to players who are computers based on count, which is a static var, so it is changed for the whole class, not for only one object
        this("Computer #" + count);
        // now we have one more players
        count ++;
    }

    /**
     * Getter of name
     * 
     * @return - the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method is called when a player is required to take the card from a stack
     * to his score pile. The variable pile should be treated as a dynamic array so
     * that the array will auto-resize to hold enough number of cards. The length of
     * pile should properly record the total number of cards taken by a player.
     * 
     * Important: at the end of this method, remove all cards
     * from the parameter array "cards".
     * 
     * 
     * 
     * @param cards - an array of cards taken from a stack
     * @param count - number of cards taken from the stack
     */
    public void moveToPile(Card[] cards, int count) {
        // create new pile to hold current cards from pile +count number of Cards from stack
        int newLength = this.pile.length + count;
        Card[] newpile = new Card[newLength];
        System.out.print(this.name + " takes the card: ");

        int i = 0;
        int j = 0;

        // copy all cards that are in current pile to new pile
        for(; i < this.pile.length; ++i) {
            newpile[i] = this.pile[i];
        }
        
        // copy cards from stack to new pile starting from index that is equal to this pile's length
        for(i = this.pile.length; i < newLength; i++) {
            newpile[i] = cards[j];
            // print all the cards that are taken
            cards[j].print();
            // remove cards from stack
            cards[j] = null;
            j++;
        }
        System.out.println();

        // point this pile to new pile -> we resized the pile & added cards from stack !
        this.pile = newpile;
    }

    /**
     * Get card to play based on the index.
     * Update hand if the card exists.
     * Return null if  the card does not exist at that index
     * 
     * @return - card or null
     */

    private Card cardToPlay(int index) {
        // check if the index is within the handcount range
        if (index >= 0 && index < this.handCount) {
            // get card at that index
            Card playingCard = this.hand[index];

            // Change Hand -> remove card we want to play from hand
            // copy cards moving to left
            for(int i = index; i < this.handCount - 1; ++i) {
                this.hand[i] = this.hand[i + 1];
            }
            // remove thant card from hand -> make last card null
            this.hand[--this.handCount] = null;
            return playingCard;
        }
        // if not within range, return null
        else {
            return null;
        }
    }

    /**
     * This method prompts a human player to play a card. It first prints
     * all cards on his hand. Then the player will need to select a card
     * to play by entering the INDEX of the card.
     * 
     * @return - the card to play
     */
    public Card playCard() 
    {
        Card cardtoPlay;
        do {
            // print cards in hand
            for(int i = 0; i < this.handCount; i++) {
                System.out.print(i + ": ");
                this.hand[i].print();
                System.out.println();
            }
            // Ask player to select a card
            System.out.println(this.name + ", please select a card to play: ");
            Scanner in = new Scanner(System.in);
            // get cardtoPlay with checkings, will return null if there is no card in that index
            // Otherwise, will return card the player will play and make the player's hand shorter
            cardtoPlay = this.cardToPlay(in.nextInt());
            // keep asking until players gives valid index
        } while(cardtoPlay == null);

        return cardtoPlay;
    }

    /**
     * This method let a computer player to play a card randomly. The computer
     * player will pick any available card to play in a random fashion.
     * 
     * @return - card to play
     */
    public Card playCardRandomly() {
        // random int between 0-handcount
        int randomInt = ThreadLocalRandom.current().nextInt(0, this.handCount);
        // get random card from hand
        return this.getHandCard(randomInt);
    }

    /**
     * Deal a card to a player. This should add a card to the variable hand and
     * update the variable handCount. During this method, no need to resize
     * the array. Assume that a player will be dealt with at most 10 cards.
     * That is, the method will only be called 10 times on a player.
     * 
     * After each call of this method, the hand should be sorted properly according
     * to the number of the card.
     * 
     * @param card - a card to be dealt
     */
    public void dealCard(Card card) {
        // if there is already 10 cards, cannot add more
        if (this.handCount != 10) {

            // create newHand with one more place for card
            Card[] newHand = new Card[this.handCount + 1];

            // find where to put this card
            int check = 0;

            // iterate through hand
            for (int i = 0; i < this.handCount; i++) {
                // if card is less than some card at some index
                if (card.getNumber() < this.hand[i].getNumber()) {
                    // get that index
                    check = i;
                    break;
                } else {
                    // if not found, we will put card at the end
                    check = this.handCount;
                }
            }
            // fill the hand as old till that index
            int i = 0;
            while (i < check) {
                newHand[i] = this.hand[i];
                i++;
            }
            // put new card in the index
            newHand[check] = card;

            // put remaining cards
            for (i = check; i < this.handCount; i++) {
                newHand[i + 1] = this.hand[i];
            }

            // point hand to newHand
            hand = newHand;
            // increment hand
            this.handCount++;
        }
    }

    /**
     * Get the score of the player by counting the total number of Bull Head in the
     * score pile.
     * 
     * @return - score, 0 or a positive integer
     */
    public int getScore() {
        int score = 0;

        // iterate through the pile
        for(int i = 0; i < this.pile.length; ++i) {
            // ad bullheads for every cards
            score += this.pile[i].getBullHead();
        }

        return score;
    }

    /**
     * To print the score pile. 
     */
    public void printPile() {
        for (Card c : pile) {
            c.print();
        }
        System.out.println();
    }

    /**
     * This is a getter of hand's card. 
     * 
     * @param index - the index of card to take
     * @return - the card from the hand or null
     */
    public Card getHandCard(int index) {
        if (index < 0 || index >= handCount)
            return null;
        return hand[index];
    }
}
