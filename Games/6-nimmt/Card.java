public class Card {
    /**
     * The number of the card
     */
    private int number;
    /**
     * The number of bull head of the card
     */
    private int bullHead;

    /**
     * Other constructor
     * 
     * @param number - the value of the card
     */
    public Card(int number) {
        this.number = number;

        // 1 card with 7 bull heads—number 55
        if (number == 55) {
            this.bullHead = 7;
        }
        // 8 cards with 5 bull heads—multiples of 11 (except 55)
        else if (number % 11 == 0) {
            this.bullHead = 5;
        }
        // 10 cards with 3 bull heads—multiples of ten
        else if (number % 10 == 0) {
            this.bullHead = 3;
        }
        // 9 cards with 2 bull heads—multiples of five that are not 
        // multiples of ten (except 55)
        else if (number % 5 == 0) {
            this.bullHead = 2;
        } 
        // 76 cards with 1 bull head—the rest of the cards from 1 through 104
        else {
            this.bullHead = 1;
        }
    }
    /**
     * The getter of number
     * 
     * @return - the value of the number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * The getter of bull head
     * 
     * @return - the number of bull head
     */
    public int getBullHead() {
        return this.bullHead;
    }

    /**
     * To print a card. This method has been done for you.
     * 
     * You don't need to change it and you should not change it.
     */
    public void print() {
        System.out.printf("%d(%d)", number, bullHead);
    }

    /**
     * To return the string of a card. This method has been done for you
     * 
     * You don't need ot change it and you should not change it.
     */
    public String toString() {
        return number + "(" + bullHead + ")";
    }
}
