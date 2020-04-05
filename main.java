import java.util.Scanner;

public class main {

	static ConnectFour connectFour;

	public static void setUp(Scanner scanner) {

		System.out.println("we offer: ");
		System.out.println("\t1. 3x3x3 connect3;\tpart 1 of the project");
		System.out.println("\t2. 3x5x3 connect3;\tcourtesy of a well implemented code");
		System.out.println("\t3. 6x7x4 connect4;\tpart 2 of the project");

		System.out.print("choose the game [1 - 3]: ");
		int choice = scanner.nextInt();
		System.out.println();

		int row = -1, col = -1, limit = -1;

		if (choice == 1) {

			col = 3;
			row = 3;
			limit = 3;

		} else if (choice == 2) {

			col = 5;
			row = 3;
			limit = 3;

		} else {

			col = 7;
			row = 6;
			limit = 4;

		}

		// ConnectFour
		connectFour = new ConnectFour(row, col, limit);

		System.out.println("welcome to a connect " + limit + " game!");

		System.out.println("we offer agents that use: ");
		System.out.println("\t1. randomness;");
		System.out.println("\t2. mimimax;");
		System.out.println("\t3. h-minimax;");

		System.out.print("choose the agents [1 - 3]: ");
		int agentChoice = scanner.nextInt();
		System.out.println();

		if (agentChoice == 1) {

			connectFour.setAgent(connectFour.ai.random);

		} else if (agentChoice == 2) {

			connectFour.setAgent(connectFour.ai.minimax);

		} else if (agentChoice == 3) {

			connectFour.setAgent(connectFour.ai.hminimax);

		}

		connectFour.go(scanner);

	}

	public static void main(String[] args) {

		System.out.println("welcome to the Project 1!" + args.length);

		Scanner scanner = new Scanner(System.in);
		boolean s = true;

		if (args.length != 0) {

			if (args[0].equals("1")) {

				connectFour = new ConnectFour(3, 3, 3);
				connectFour.setAgent(connectFour.ai.minimax);
				connectFour.go(scanner);

			}

			if (args[0].equals("2")) {

				connectFour = new ConnectFour(6, 7, 4);
				connectFour.setAgent(connectFour.ai.hminimax);
				connectFour.go(scanner);

			}

			return;

		}

		while (true) {

			if (s) setUp(scanner);

			System.out.print("would you like to quit? [ y / n] ");
			String quit = scanner.next();
			System.out.println();

			if (quit.equals("y")) break;

			System.out.print("would you like to play a diffent game / choose another agent? [ y / n] ");
			s = (scanner.next().equals("y"));
			System.out.println();

		}

		System.out.println("pleasure having you! bye :) ");

	}

}
