import java.util.Random;

public class BackgroundIntelligence {

	static int max = Integer.MAX_VALUE, min = Integer.MIN_VALUE;

	Board board;
	int nextChoice, maxDepth;

	// user's turn, machine's turn
	int u, m;
	// draw
	int draw;

	// options for agents
	int random = 0, minimax = 1, hminimax = 3;

	// which agent we'll be
	int agent;

	// number of steps the ai will take to find
	int numOfSteps;

	// constructor
	public BackgroundIntelligence(Board board) {

		this.board = board;

		nextChoice 	= -1;
		maxDepth 	=  9;

		m = 0;
		u = 1;

		draw = 3;

		agent = -1;

	}

	public void setAgent(int agent) {

		this.agent = agent;

	}

	public void updateBoard(Board board) {

		this.board = board;

	}

	public int getChoice() {

		if (agent == -1) {

			System.out.println("agent not chosen!");
			return -1;

		}

		numOfSteps = 0;
		nextChoice = -1;
		long start = System.nanoTime();

		if (agent == random) {

			random();

		} else if (agent == minimax) {

			minimax(m);

		} else if (agent == hminimax) {

			minimax(0, m, min, max);

		}

		long end = System.nanoTime();
		long elapsed = (end - start);

		if (nextChoice == -1) { // choose random

			random();

		}

		System.out.println("I was thinking for " + elapsed + " units of time. I evaluated " + numOfSteps + " move(s).");



		return nextChoice;

	}

	public int gameResult() {

		if (board.culminate()) return board.currCell();

		// so that game continues
		if (board.haveFreeCells()) return -1;

		// draw
		return 0;

	}

	public int calcScore(int mScore, int numOfSteps) {

		int choiceScore = 4 - numOfSteps;

		if (mScore < 4) return (int) Math.pow(10, mScore) * choiceScore;

		return 1000;

	}

	public int getScore() {

        int score = 0;

        for (int i = board.row - 1; i >= 0; i = i - 1)

        	for (int j = 0; j < board.col; j = j + 1) {

        	int mScore = 1, emptCells  = 0;

            if (board.data[i][j] == 0 || board.data[i][j] == u) {

            	continue;

            }

            int coeff = (j < 4) ? 1 : -1;
            int remChoices = 0;

            if (j != 4) {

                for (int k = 1; k < 4; k = k + 1)  {

                	int focus = board.data[i][j + coeff * k];

                	if (focus == m) {

                			mScore = mScore + 1;

                	} else if (focus == u) {

               			mScore = 0;
               			emptCells = 0;
               			// break overall loop
               			break;

               		} else {

                		emptCells = emptCells + 1;

               		}

               	}

                if (emptCells > 0) {

                    for (int p = 1; p < 4; p = p + 1) {

                        for (int q = i; q < board.row; q = q + 1) {

                        	if (board.data[q][j + coeff * p] == 0) {

                        		remChoices = remChoices + 1;

                        	} else {

                        		break;

                        	}

                        }

                    }

                }

            }

            if (i > 4) {

            	int count = 0;

            	for (int k = 1; k < 4; k = k + 1) {

            		count = k;
            		int focus = board.data[i - k][j];

            		if (focus == m) {

            			mScore = mScore + 1;

            		} else if (focus == u) {

            			mScore = 0;
            			// break overall loop
            			break;

            		}


                }

                remChoices = 0;

                if (mScore > 0){

                    for (int p = i - count +1; p < i; p = p + 1) {

                    	if (board.data[p][j] == 0) remChoices = remChoices + 1;
                        else break;

                    }

                }

            }

            coeff = 1;
            if (i > 2 && j > 2) coeff = -1;

            if (j < 2 && i > 2) {

            	for (int k = 1; k < 4; k = k + 1) {

            		int focus = board.data[i - k][j + coeff * k];

            		if (focus == m) {

            			mScore = mScore + 1;

            		} else if (focus == u) {

            			mScore = 0;
                		emptCells = 0;
                		break;

            		} else {

            			emptCells = emptCells + 1;

            		}

                }

                if (emptCells > 0) {

                    for (int p = 1; p < 4; p = p + 1) {

                        for (int q = i - p; q < board.row; q = q + 1) {

                        	if (board.data[q][j + coeff * p] == 0) {

                        		remChoices = remChoices + 1;

                        	} else {

                        		break;

                        	}

                        }

                    }

                }

            }

            if (remChoices != 0) {

            	score = score + calcScore(mScore, remChoices);

            }

        }

        return score;

	}

	// simple minimax decision
	public int minimax(int turn) {

		numOfSteps = numOfSteps + 1;

		int g = gameResult();

		if (g == 0) return 0;

		else if (g == m) return max / 2;

		else if (g == u) return min / 2;

		int maxScore = min,
				minScore = max;

		for (int j = 0; j < board.row; j = j + 1) {

			int currScore = 0;

			if (!board.isLegal(j)) continue;

			// switch turn
			int temp = (turn == m) ? u : m;

			board.drop(j, turn);
			currScore = minimax(temp);

			if (turn == m) {

				if (currScore >  maxScore) nextChoice = j;

				if (currScore == max / 2) {

					board.unDrop(j);
					break;

				}

				maxScore = Math.max(currScore, maxScore);

			} else if (turn == u) {

				minScore = Math.min(currScore, minScore);

			}

			board.unDrop(j);

			if (currScore == max || currScore == min) {

				break;

			}

		}

		return (turn == m) ? maxScore : minScore;

	}

	// minimax algorithm with alpha-beta pruning and cut off at a chosen depth
	public int minimax(int depth, int turn, int alpha, int beta) {

		numOfSteps = numOfSteps + 1;

		if (beta <= alpha) {

			if (turn == m)  return max; // machine's move

			else return min;						// user's move

		}

		int g = gameResult();

		if (g == 0) return 0;

		else if (g == m) return max / 2;

		else if (g == u) return min / 2;

		if (depth == maxDepth) return getScore();

		int maxScore = min,
			minScore = max;

		for (int j = 0; j < board.row; j = j + 1) {

			int currScore = 0;

			if (!board.isLegal(j)) continue;

			// switch turn
			int temp = (turn == m) ? u : m;

			board.drop(j, turn);
			currScore = minimax(depth + 1, temp, alpha, beta);

			if (turn == m) {

				if (depth == 0) {

					if (currScore >  maxScore) nextChoice = j;

					if (currScore == max / 2) {

						board.unDrop(j);
						break;

					}

				}

				maxScore = Math.max(currScore, maxScore);
				alpha	 = Math.max(currScore, alpha);


			} else if (turn == u) {

				minScore = Math.min(currScore, minScore);
				beta 	 = Math.min(currScore, beta);

			}

			board.unDrop(j);

			if (currScore == max || currScore == min) {

				break;

			}

		}

		return (turn == m) ? maxScore : minScore;

	}

	// get a random num
	public void random() {

		numOfSteps = numOfSteps + 1;

		int[] freeCols = board.getFreeCols();
		int index = new Random().nextInt(freeCols.length - 1);

		nextChoice = freeCols[index];

	}

}
