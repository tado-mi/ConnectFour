import java.util.Scanner;

public class ConnectFour {

  Board board;
  BackgroundIntelligence ai;

  // constructor
  public ConnectFour(int row, int col, int limit) {

    board = new Board(row, col, limit);
    ai = new BackgroundIntelligence(board);

  }

  public void setAgent(int agent) {

    ai.setAgent(agent);

  }

  public boolean gameOver() {

    return board.draw() || board.culminate();

  }

  public void printBoard() {
    
    System.out.println(board.toString());

  }

  public void handleGameOver() {

    System.out.println("So, it came to my attention that the game is over.");
    System.out.println("\tI can tell that, because I am intelligent, you see.");

    System.out.println("this is where we are at: ");
    printBoard();
    System.out.println();

    int w = board.getWinner();

    if (w == ai.draw) System.out.println("I see that I have met my equal here!");
    if (w == ai.u) System.out.println("You won! I stand humbled, master!");
    if (w == ai.m) System.out.println("I won! Which is not surprising at all, since "
    + "I have a metallic brain and had great minds work on me!");

    if (ai.agent == ai.random) {

      System.out.println("\twasn't too demanding to play against randomness, was it?");

    }

  }

  // driver method
  public void go(Scanner scanner) {

    while (true) {

      if (gameOver()) {

        handleGameOver();
        return;

      }

      System.out.print("available choices: ");
      board.printFreeCols();

      boolean isLegal = false;
      int userChoice  = -1;

      while (!isLegal) { // force the user to make a valid choice

        System.out.print("choose column: ");
        userChoice = scanner.nextInt();

        if (!board.isLegal(userChoice)) {

          System.out.println("column " + userChoice + " is full / off bounds.");
          continue;

        } // else find the row

        isLegal = true;

      }

      // get the board.data to reflect the user choice
      board.drop(userChoice, ai.u);

      System.out.println("result: ");
      printBoard();

      // update ai's board
      ai.updateBoard(board);

      // ai
      int aiChoice = ai.getChoice();
      if (aiChoice == -1) {

        // should never happen
        return;

      }

      board.drop(aiChoice, ai.m);

      // System.out.println("ai chose column " + aiChoice);
      System.out.println("result: ");
      printBoard();

    }

  }

}
