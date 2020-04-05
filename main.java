import java.util.Scanner;

public class main {

	static ConnectFour connectFour;

	public static void setUp(Scanner scanner) {

		int row = 6, col = 7, limit = 4;

		boolean temp;
		System.out.println("would you like customise the number of rows and columns of the game? [ y / n] ");
		temp = scanner.next().equals("y");

		if (temp) {

			System.out.print("row: ");
			row = scanner.nextInt();
			System.out.print("column: ");
			col = scanner.nextInt();

		}

		System.out.println("would you like customise the number of disks required? [ y / n] ");
		temp = scanner.next().equals("y");

		if (temp) {

			System.out.print("limit: ");
			limit = scanner.nextInt();

		}

		// ConnectFour
		connectFour = new ConnectFour(row, col, limit);

		System.out.println("Welcome to a connect " + limit + " game! (again)");

		System.out.println("we offer agents that use: ");
		System.out.println("\t1. randomness;");
		System.out.println("\t2. mimimax;");
		System.out.println("\t3. h-minimax;");

		System.out.print("choose the agents [1 - 3]: ");
		int agentChoice = scanner.nextInt() - 1;
		System.out.println();

		int[] agents = { connectFour.ai.random, connectFour.ai.minimax, connectFour.ai.hminimax }
		connectFour.setAgent(agents[agentChoice]);

		connectFour.go(scanner);

	}

	public static void main(String[] args) {

		System.out.println("Welcome to the Connect Four game with an intelligenta agent!");

		Scanner scanner = new Scanner(System.in);
		boolean s = true;

		while (true) {

			if (s) setUp(scanner);

			System.out.print("would you like to quit? [ y / n] ");
			String quit = scanner.next();
			System.out.println();

			if (quit.equals("y")) break;

			System.out.print("would you like to play a diffent game / choose another agent? [ y / n] ");
			s = scanner.next().equals("y");
			System.out.println();

		}

		System.out.println("pleasure having you! bye :) ");

	}

}
