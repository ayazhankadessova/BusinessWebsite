import java.util.Scanner;


/**
 * @author: ______KADESSOVA Ayazhan_________
 */
public class Connect4 {

   /**
    * Total number of rows of the game board.
    */
   public static final int HEIGHT = 6;
   /**
    * Total number of columns of the game board.
    */
   public static final int WIDTH  = 8;

   /**
    * Main program.
    */
   public static void main(String[] args) {

      new Connect4Old().runOnce();
   }

   /**
    * Program entry.
    */
   void runOnce() {
      // HEIGHT and WIDTH are two constants defined above. These two constants are visible in the entire
      // program. They cannot be further modified, i.e., it is impossible to write
      // HEIGHT = HEIGHT + 1; or WIDTH = 0; anywhere in your code. However, you can
      // use these two constants as a reference, i.e., row = HEIGHT - 1, for example.

      int[][] board = new int[HEIGHT][WIDTH];
      char[] symbols = new char[]{'1', '2'};
      int player = 1;
      printBoard(board, symbols);

      Scanner scanner = new Scanner(System.in);
      boolean quit = false;
      while(!isGameOver(board) && !quit) {
         System.out.println("Player " + player + ", please enter a command. Press 'h' for help");
         char s = scanner.next().charAt(0);
         switch(s) {
            case 'h':
            case 'H':
               printHelpMenu();
               break;
            case 'c':
            case 'C':
               changeSymbol(player, symbols);
               break;
            case 'q':
            case 'Q':
               quit = true;
               System.out.println("Bye~");
               continue;
            case 'r':
               restart(board);
               printBoard(board, symbols);
               continue;
            default:
               if (!validate(s, board)) {
                  System.out.println("Wrong input!, please do again");
                  continue;
               }
               // convert the char 's' to the integer 'column', with the value 0 to 7
               // based on ascii values. '3' - '0' = 51 - 48 = 3
               int column = s - '0';

               fillBoard(board, column, player);
               printBoard(board, symbols);
               if (isGameOver(board)) {
                  System.out.println("Player " + player + ", you win!");
                  break;
               } else if (checkMate(player, board))
                     System.out.println("Check mate!");
               else if (check(player, board))
                  System.out.println("Check!");
                  // After each iteration, change the variable "player" alternatively between the integers 1 and 2.
                  player = otherPlayer(player);
         } // end switch
      } // end while
   }

   /**
    * Change the variable player alternatively between the integers 1 and 2
    *
    * @param player - current player
    *  return: integer corresponding to another player
    */
   int otherPlayer(int player) {
      if (player == 1) {
         return 2;
      } else {
         return 1;
      }
   }
   /**
    * Reset the board to the initial state
    *
    * @param board - the game board array
    */
   void restart(int[][] board) {
      // interate through rows
      for(int row = 0; row < HEIGHT; row++) {
         // through every column in a row
         for(int column= 0; column < WIDTH; column++) {
            // reset every cell to initial value - 0
            board[row][column] = 0;
         }
      }
   }

   /**
    * It allows a player to choose a new symbol to represents its chess.
    * This method should ask the player to enter a new symbol so that symbol is not
    * the same as its opponent.
    * Otherwise the player will need to enter it again until they are different.
    *
    * @param player  - the player who is about to change its symbol
    * @param symbols - the symbols array storing the players' symbols.
    */
   void changeSymbol(int player, char[] symbols) {
      Scanner in = new Scanner(System.in);
      char givenSymbol ; int otherSymbolIndex;

      // We need to make sure players have different symbols, so we check entered symbol
      // with the symbol that is assigned to the opponent

      // First player's symbol is the first item in the symbols array, so compare to the second item in the list.
      if (player == 1) {
         otherSymbolIndex = 1;
      // Second player's symbol is the second item in the symbols array, so compare to the first item in the list.
      } else {
         otherSymbolIndex = 0;
      }
      // Get other players symbol through index
      char otherSymbol = symbols[otherSymbolIndex];

      // Ask for the player for a new symbol
      // Compare it to another player's symbol
      // while two symbols are the same -> repeat
      do {
         System.out.println("Enter a new symbol, which needs to be different from your opponent's");
         givenSymbol = in.next().charAt(0);
      } while(givenSymbol == otherSymbol);

      // when the loop ends and two symbols are different,
      // assign validated symbol to the player
      symbols[Math.abs(otherSymbolIndex - 1)] = givenSymbol;
   }

   /**
    * This method returns true if the player "player" plays immediately, he/she may
    * end the game. This warns the other player to
    * place his/her next block in a correct position.
    *
    *
    * @param player - the player who is about to win if the other player does not
    *               stop him
    * @param board  - the 2D array of the game board.
    * @return true if the player is about to win, false if the player is not.
    */
   boolean check(int player, int[][] board) {
      // Check what will happen if a player drops a symbol at every column
      // Create a new board for checking situation for every row -> make all values as old board
      for(int column = 0; column < WIDTH; column++) {
         int[][] newBoard = new int[HEIGHT][WIDTH];

         for(int row = 0;  row < HEIGHT; row++) {
            for(int columnInner = 0; columnInner  < WIDTH; columnInner++) {
               newBoard[row][columnInner ] = board[row][columnInner];
            }
         }

         // if it is possible to drop symbol for that column, try dropping & fill the newboard
         if (newBoard[0][column] == 0) {
            fillBoard(newBoard, column, player);
         }

         // check if newBoard has a winner
         // if yes -> return true
         if (isGameOver(newBoard)) {
            return true;
         }
      }
      return false;
   }

   /**
    * This method is very similar to the method check. However, a check-mate move
    * means no matter how the other player place his/her next block, in the next
    * turn the player can win the game with certain move.
    *
    * A check-mate move must be a check move. Not all check moves are check-mate
    * move.
    *
    * @param player - the player who is about to win no matter what the other
    *               player does
    * @param board  - the 2D array of the game board/
    * @return true if the player is about to win
    */
   boolean checkMate(int player, int[][] board) {
      int otherPlayer =  otherPlayer(player);

      // checkmate move must be check move
      if (!check (player, board)) {
         return false;
      // if its is check, but another player has check too -> false
      } else if (check(otherPlayer, board)) {
         return false;
      } else {
         boolean checkMate = true;

         // Check what will happen if a player drops a symbol at every column
         // Create a new board for checking situation for every row -> make all values as old board
         for(int column= 0; column < WIDTH; column++) {
            int[][] newBoard = new int[HEIGHT][WIDTH];

            for(int row = 0; row  < HEIGHT; row++) {
               for(int columnInner  = 0; columnInner  < WIDTH; columnInner++ ) {
                  newBoard[row][columnInner] = board[row][columnInner];
               }
            }

            // if it is possible to drop symbol for that column, try dropping symbol of another player & fill the newboard
            if (newBoard[0][column] == 0) {
               fillBoard(newBoard, column, otherPlayer);
            }

            // if new symbols blocks check for the player, make this check canot be checkmate
            if (!check(player, newBoard)) {
               checkMate = false;
            }
         }
         return checkMate;
      }
   }

   /**
    * Validate if the input is valid. This input should be one of the character
    * '0', '1', '2', '3,' ..., '7'.
    * The column corresponding to that input should not be full.
    *
    * @param input - the character of the column that the block is intended to
    *              place
    * @param board - the game board
    * @return - true if it is valid, false if it is invalid (e.g., '8', 'c', '@',
    *         EOT (which has an unicode 4) )
    */
   boolean validate(char input, int[][] board) {
      int column = input - '0';
      // column value needs to be between 0-7 & column needs to have at least the highest spot free, eg equal to 0
      if (column < 8 && column >=0 && board[0][column] == 0) {
         return true;
      } else {
         return false;
      }
   }

   /**
    * Given the column (in integer) that a player wish to place his/her block,
    * update the gameboard. You may assume that the input has been validated before
    * calling this method, i.e., there always has room to place the block when
    * calling this method.
    *
    * @param board  - the game board
    * @param column - the column that the player want to places its block
    * @param player - 1 or 2, the player.
    */
   void fillBoard(int[][] board, int column, int player) {
      int rowToFill;
      // search for the empty spot in a column by iterating through rows from bottom to top
      for(rowToFill = HEIGHT - 1; rowToFill >= 0 && board[rowToFill][column] != 0; rowToFill -- ) {
      }
      // when an empty spot is found, assign the value of the player
      board[rowToFill][column] = player;
   }

   /**
    * Print the Help Menu. Please try to understand the switch case in runOnce and
    * Provide a one line comment about the purpose of each symbol.
    */
   void printHelpMenu() {
      System.out.println("Help Menu:");
      System.out.println("------------------");
      System.out.println("\uD83D\uDC7E Welcome to Command Line version of Connect Four Game! \uD83D\uDC7E ");
      System.out.println("------------------");
      System.out.println("Instructions:\n1. Choose two players\n2. Each player should choose a symbol or use a pre-assigned (1 and 2)\n3. Take turns dropping symbols from the top into a seven-column, six-row vertically suspended grid.\n4. The pieces fall straight down, occupying the lowest available space within the column.\n5. The goal of the Connect Four game is to be the first player to form a horizontal, vertical, or diagonal line of four of one’s own discs.\n6. Connect Four is a solved game. Thus, the first player can always win by playing the right moves.");
      System.out.println("------------------");
      System.out.println("Navigation:\n> Press the following letter + enter for:\n1. ‘h’ or ‘H’ for the Help Menu.\n2. ‘c’ or ‘C’ to change the symbol. Two players must have different symbols!\n3. ‘q’ or ‘Q’ to quit the game. But… come back, we will miss u!\n4. ‘r’ or ‘R’ to start the game from scratch. All the progress will be lost and a new empty board will be generated.\n- If you don’t need any functions, just play by choosing a column where you want to drop your symbols. Column must be between 0-7. Choose the column that is not full :)");
      System.out.println("------------------");
      System.out.println("Some warnings:\n1. Check!\nIf the player player plays immediately, he/she may end the game. This warns the other player toplace his/her next block in a correct position.\n2. Checkmate!\nA check-mate move means no matter how the other player place his/her next block,in the next turn the player can win the game with certain move. A check-mate move must be a check move. Not all check moves are check-mate move.\n3. If tie -> board fully filled but no connect 4, Player 2 wins.");
      System.out.println("------------------");
      System.out.println("Good luck!!!\uD83D\uDE43");
   }

   /**
    * Determine if the game is over. Game is over if and only if one of the player
    * has a connect-4 or the entire gameboard is fully filled.
    *
    * @param board - the game board
    * @return - true if the game is over, false other wise.
    */
   boolean isGameOver(int[][] board) {
      boolean isGameOver = true;
      // for every cell that is not zero, check if there is four same symbols in a row in four directions
      // iterate through every row
      for(int row = 0; row< HEIGHT; row++) {
         // iterate through every column
         for(int column = 0; column < WIDTH; column++) {
            // skip empty cells
            if (board[row][column] == 0) {
               isGameOver = false;
            } else {
               // look up & right - right diagonal
               // need to check 3 rows above and 3 columns right, so starting positions for row can be 0, 1, 2 for rows & 0, 1, 2, 3, 4 for columns
               if (row < 3 && column < 5 && board[row][column] == board[row + 1][column + 1] && board[row][column] == board[row + 2][column + 2] && board[row][column] == board[row + 3][column + 3]) {
                  return true;
               }

               // look up & left - left diagonal
               // need to check 3 rows above and 3 columns left, so starting positions for row can be 0, 1, 2 for rows & 3, 4, 5, 6, 7 for columns
               if (row < 3 && column >= 3 && board[row][column] == board[row + 1][column - 1] && board[row][column] == board[row + 2][column - 2] && board[row][column] == board[row + 3][column - 3]) {
                  return true;
               }

               // look right
               // need to check 3 columns right, so starting positions for columns can be 0, 1, 2, 3, 4
               if (column < 5 && board[row][column] == board[row][column + 1] && board[row][column] == board[row][column + 2] && board[row][column] == board[row][column + 3]) {
                  return true;
               }

               // look up
               // need to check 3 rows above, so starting positions for row can be 0, 1, 2 for rows
               if (row < 3 && board[row][column] == board[row + 1][column] && board[row][column] == board[row + 2][column] && board[row][column] == board[row + 3][column]) {
                  return true;
               }
            }
         }
      }
      return isGameOver;
   }
   /**
    * Print the game board in a particular format. The instruction can be referred
    * to the GitHub or the demo program. By default, Player 1 uses the character
    * '1' to represent its block. Player 2 uses the character '2'. They can be
    * overrided by the value of symbols array. This method does not change the
    * value of the gameboard nor the symbols array.
    *
    * @param board   - the game board to be printed.
    * @param symbols - the symbols that represents player 1 and player 2.
    */
   void printBoard(int[][] board, char[] symbols) {
      System.out.print(' ');
      // print column numbers 01234567
      for(int column = 0; column < WIDTH; column++) {
         System.out.print(column);
      }

      System.out.println("\n --------");

      // print rows
      for(int row = 0; row < HEIGHT; row++) {
         System.out.print('|');

         for(int column = 0; column < WIDTH; column++) {
            // if cell = 0, leave empty
            if (board[row][column] == 0) {
               System.out.print(' ');
               // if cell = 1 -> player1 -> print their symbol
            } else if (board[row][column] == 1) {
               System.out.print(symbols[0]);
               // if cell = 2 -> player2 -> print their symbol
            } else if (board[row][column] == 2) {
               System.out.print(symbols[1]);
            }
         }
         System.out.println('|');
      }
      System.out.println(" --------");
   }
}
