public class Board {

    int row, col,
        currRow, currCol,
        limit;

    int[][] data;
    int[] freeCols;

    int emptCell;

    char charSet[];

    int winner;

    public Board(int r, int c, int l){

        row = r;
        col = c;

        currRow = -1;
        currCol = -1;

        // -1: since the methods checking omit the current cell
        // -1: since the methods checking do > not >=
        limit = l - 2;

        emptCell = -1;

        data = new int[row][col];

        for (int i = 0; i < row; i = i + 1) {

            for (int j = 0; j < col; j = j + 1) {

                data[i][j] = emptCell;

            }

        }

        freeCols = new int[col + 1];

        charSet = new char[2];
        charSet[0] = 'R';
        charSet[1] = 'Y';

        winner = -1;

    }

    public String toString() {

        String ans = "\n" + " ";

        for (int j = 0; j < col; j = j + 1) {

            ans = " " + ans + " " + j;

        }

        ans = ans + "\n";

        for (int i = 0; i < row; i = i + 1) {

            ans = ans + i + " ";

            for (int j = 0; j < col; j = j + 1) {

                char c = ' ';

                if (data[i][j] != emptCell) c = charSet[data[i][j]];

                ans = ans + c + " ";

            }

            ans = ans + i;

            ans = ans + "\n";

        }

        ans = ans + " ";


        for (int j = 0; j < col; j = j + 1) {

            ans = " " + ans + " " + j;

        }

        ans = ans + "\n";

        return ans;

    }

    public boolean isLegal(int c){

      if (c < 0 || c > (col - 1)) return false;

        return (data[0][c] == emptCell);

    }

    public boolean drop(int c, int turn){

        if (!isLegal(c)) {

            System.out.println("illegal drop made it all the way to drop(c, turn)");
            return false;

        }

        for (int i = row - 1; i >= 0; i = i - 1) {

            if (data[i][c] == emptCell) {

                currRow = i;
                currCol = c;

                data[i][c] = turn;
                return true;

            }

        }

        return false;

    }

    public void unDrop(int c){

        for (int i = 0; i < row; i = i + 1) {

            if (data[i][c] != emptCell) {

                data[i][c] = emptCell;
                return;

            }

        }

    }

    // methods for free columns

    public void setFreeCols() {

        int ans[] = new int[col + 1];
        int count = 0;

        for (int j = 0; j < col; j = j + 1) {

            if (data[0][j] == emptCell) {

                ans[count] = j;
                count = count + 1;

            }

        }

        ans[count] = -1;

        freeCols = ans;


    }

    public int[] getFreeCols() {

        setFreeCols();
        return freeCols;

    }

    public boolean haveFreeCells() {

      setFreeCols();
      return freeCols[0] != -1;

    }

    public void printFreeCols() {

        setFreeCols();

        System.out.print("{ ");

        for (int i = 0; freeCols[i] != -1; i = i + 1) {

            System.out.print(freeCols[i] + " ");

        }

        System.out.println("}");

    }

    public boolean vertical () {

        int c = data[currRow][currCol];

        int st = currRow;
        if (st > 0) {

            while (data[st - 1][currCol] == c) {

                st = st - 1;
                if (st == 0) break;

            }

        }

        int end = currRow;
        if (end < row - 1) {

            while (data[end + 1][currCol] == c) {

                end = end + 1;
                if (end == row - 1) break;

            }

        }

        return (end - st > limit);

    }

    public boolean horizontal () {

        int c = data[currRow][currCol];

        int st = currCol;
        if (st > 0) {

            while (data[currRow][st - 1] == c) {

                st  = st - 1;
                if (st == 0) break;

            }

        }

        int end = currCol;
        if (end < col - 1) {

            while (data[currRow][end + 1] == c) {

                end = end + 1;
                if (end == col - 1) break;

            }

        }

        return (end - st > limit);

    }

    public boolean diagonal() {

        boolean D = diagonalDown();
        boolean U = diagonalUp();

        return D || U;

    }

    // \
    public boolean diagonalDown () {

        int c = data[currRow][currCol];

        int xSt = currRow;
        int ySt = currCol;

        if (xSt > 0  && ySt > 0) {

            while (data[xSt - 1][ySt - 1] == c) {

                xSt = xSt - 1;
                ySt = ySt - 1;

                if (xSt == 0 || ySt == 0) break;

            }

        }

        int xEnd = currRow;
        int yEnd = currCol;

        if (xEnd < row - 1 && yEnd < col - 1) {

            while (data[xEnd + 1][yEnd + 1] == c) {

                xEnd = xEnd + 1;
                yEnd = yEnd + 1;

                if (xEnd == row - 1 || yEnd == col - 1) break;

            }

        }

        return (xEnd - xSt > limit);

    }

    //  /
    public boolean diagonalUp() {

        int c = data[currRow][currCol];

        int xSt = currRow;
        int ySt = currCol;

        if (xSt < row - 1  && ySt > 0) {

            while (data[xSt + 1][ySt - 1] == c) {

                xSt = xSt + 1;
                ySt = ySt - 1;

                if (xSt == row - 1 || ySt == 0) break;

            }

        }

        int xEnd = currRow;
        int yEnd = currCol;

        if (xEnd > 0 && yEnd < col - 1) {

            while (data[xEnd - 1][yEnd + 1] == c) {

                xEnd = xEnd - 1;
                yEnd = yEnd + 1;

                if (xEnd == 0 || yEnd == col - 1) break;

            }

        }

        return (yEnd - ySt > limit);

    }

    public boolean culminate() {

        if (currRow == -1 || currCol == -1) return false;

        if (horizontal() || vertical() || diagonal()) {

            winner = data[currRow][currCol];
            return true;

        }

        return false;

    }

    public boolean draw() {

        setFreeCols();

        if (freeCols[0] == -1) {

            winner = 3; // value of ai.draw
            return true;

        }

        return false;

    }

    public int currCell() {

        if (currRow == -1 || currCol == -1) return -1;

        return data[currRow][currCol];

    }

    public int getWinner() {

      return winner;

    }

}
