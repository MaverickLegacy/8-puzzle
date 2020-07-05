package LinkedListAPI;

import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.List;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] tiles;
    private final int n;
    private int zx, zy;
    private final boolean isgoal;
    private final int hamming_dist;
    private final int manhatten_dist;

    /********************************************************
     * Method to swap tiles in the board
     * @param:
     * p1x[int]: x index of the tile 1
     * p1y[int]: y index of the tile 1
     * p2x[int]: x index of the tile 2
     * p2y[int]: y index of the tile 2
     * @return: copy of the tiles with the tiles swapped
    **********************************************************/
    private int[][] swap(int p1x,int p1y, int p2x, int p2y){
        int[][] new_tiles = copy(this.tiles);
        int temp = new_tiles[p1x][p1y];
        new_tiles[p1x][p1y] = new_tiles[p2x][p2y];
        new_tiles[p2x][p2y] = temp;
        return new_tiles;
    }
    /********************************************************
     * Method to check if tiles are in Goal configuration
     * @param:
     * board[int[][]]: 2D int array
     * @return: True if goal configuration
     **********************************************************/
    private boolean Isgoal(int[][] board){
        boolean flag = true;
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.n; j++){
                if (i == this.n - 1 && j == this.n - 1) {
                    if (board[i][j] != 0) {
                        flag = false;
                        break;
                    }

                }
                else if (board[i][j] != (i * this.n + j + 1)) {
                    flag = false;
                    break;
                }
            }

            return flag;
    }
    /********************************************************
     * Method to calculate hamming distance of the current configuration
     * @param:
     * board[int[][]]: 2D array of the tiles
     * @return: hamming dist of current configuration
     **********************************************************/
    private int calcHammingDist(int[][] board){
        int dist = 0;
        for(int i=0; i < this.n; i++)
            for(int j=0; j < this.n; j++)
                if((i != this.n - 1) || (j != this.n - 1)){
                    if (board[i][j] != (i * this.n + j + 1)) {
                        dist++;
                    }

                }
            return dist;
    }
    /********************************************************
     * Method to calculate Manhattan distance of the current configuration
     * @param:
     * board[int[][]]: 2D array of the tiles
     * @return: Manhattan distance of current configuration
     **********************************************************/
    private int calcManhattanDist(int[][] board, int[][] goal_board){
        int dist = 0;
        for(int i = 0; i < this.n; i++)
            for(int j = 0; j < this.n; j++)
                //if(i != this.n - 1 || j != this.n - 1 ){
                    if( board[i][j] != 0){
                        if(board[i][j] != ( i * this.n + j + 1 ) )
                        {
                            int index = board[i][j];
                            dist += Math.abs(goal_board[index][0] - i) + Math.abs(goal_board[index][1] - j);
                        }

                    }
                //}
        return dist;
    }
    /********************************************************
     * Method to create copy of the current tiles configuration
     * @param:
     * board: 2D array of the tiles
     * @return: copy of current tiles configuration
     **********************************************************/
    private int[][] copy(int[][] tiles){
        int[][] cp = new int[tiles.length][tiles.length];
        for(int i = 0; i < this.n; i++)
            for(int j = 0; j < this.n; j++){
                cp[i][j] = tiles[i][j];
            }
        return cp;
    }

    /********************************************************
     * Parameterized constructor
     **********************************************************/
    public Board(int[][] tiles){

        this.n = tiles.length;
        this.tiles = new int[this.n][this.n];
        int[][] goal_board = new int[this.n * this.n][2];

        /********** Initializing the goal board************************/
        int cnt = 1;
        goal_board[0][0] = this.n - 1;
        goal_board[0][1] = this.n - 1;
        for(int i = 0; i < this.n; i++)
             for(int j = 0; j < this.n; j++)
             {
                 this.tiles[i][j] = tiles[i][j];
                 if(tiles[i][j] == 0)
                 {
                     this.zx = i;
                     this.zy = j;
                 }
                if(cnt < this.n * this.n)
                {
                     goal_board[cnt][0] = i;
                     goal_board[cnt][1] = j;
                }
                cnt++;
        }

        this.isgoal = this.Isgoal(this.tiles);
        this.hamming_dist = this.calcHammingDist(this.tiles);
        this.manhatten_dist = this.calcManhattanDist(this.tiles, goal_board);

    }
    /**********************************
    string representation of the board
     @param :None
     @return : String
    ***********************************/
    public String toString(){
        StringBuilder in_str = new StringBuilder();
        in_str.append(this.n + "\n");
        for(int i=0; i < this.n ; i++){
            for(int j=0; j < this.n; j++){
                in_str.append(this.tiles[i][j]);
                if(j != this.n-1)
                    in_str.append("\t");
            }

            if(i != this.n-1)
                in_str.append("\n");
        }

        return in_str.toString();
    }

    /**********************************
     Method to return the board dimensions
     @param :None
     @return : Integer
     ***********************************/
    public int dimension(){
        return this.n;
    }

    /**********************************
     Method to return the hamming distance
     @param :None
     @return : Integer
     ***********************************/
    public int hamming(){
     return this.hamming_dist;
    }

    /**********************************
     Method to return the Manhattan distance
     @param :None
     @return : Integer
     ***********************************/
    public int manhattan(){
        return this.manhatten_dist;
    }

    /**********************************
     Method to check if in Goal Configuration
     @param :None
     @return : Integer
     ***********************************/
    public boolean isGoal() {
        return this.isgoal;
    }

    /**********************************
     Method to check equality of 2 Boards
     @param : Object y
     @return : Boolean
     ***********************************/

    public boolean equals(Object y){
        if(y == null  || !y.getClass().equals(Board.class)){
            return false;
        }

        Board other = (Board)y;
        if(this.n != other.n)
            return false;
        for(int i = 0; i < this.n; i++)
            for(int j=0; j < this.n; j++){
                if(this.tiles[i][j] != other.tiles[i][j])
                    return false;
            }
        return true;
    }

    /**********************************
     Method to return the all neigbouring board dimensions
     @param :None
     @return : Iterable of Board type
     ***********************************/

    public Iterable<Board> neighbors(){
        List<Board> neigbhbours = new ArrayList<Board>();
        if(zx > 0){
            Board neighbour = new Board(swap(zx,zy,zx-1,zy));
            neigbhbours.add(neighbour);
        }
        if(zx < this.n-1){

            Board neighbour = new Board(swap(zx,zy,zx+1,zy));
            neigbhbours.add(neighbour);
        }

        if(zy > 0){
            Board neighbour = new Board(swap(zx,zy,zx,zy-1));
            neigbhbours.add(neighbour);
        }
        if(zy < this.n-1){
            Board neighbour = new Board(swap(zx,zy,zx,zy+1));
            neigbhbours.add(neighbour);
        }

        return neigbhbours;
    }
    /**********************************
     Method to return coordinates of pair of tiles
     @param :None
     @return : Integer[]
     ***********************************/
    private int[] exchangeTiles(){
        for (int row = 0; row < this.n; row++)
            for (int col = 0; col < this.n - 1; col++)
                if(this.tiles[row][col] != 0 && this.tiles[row][col+1] != 0)
                    return new int[] {row, col, row, col+1};

        return new int[] {0, 0, 0, 1};
    }

    /**********************************
     Method to get twin of the existing board
     @param :None
     @return : Board
     ***********************************/
    public Board twin(){
        int[] rand_pts = exchangeTiles();
        Board itstwin = new Board(swap(rand_pts[0],rand_pts[1], rand_pts[2], rand_pts[3]));
        return itstwin;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        /*
        int[][] board1 = { {5, 8, 7}, {1, 4, 6}, {3, 0, 2} };
        int[][] board1 = { {0, 1, 3}, {4, 2, 5}, {7, 8, 6} };
        int[][] board1 = {{2,  3, 19 ,15, 20}, {4, 22, 16,  6,  5}, { 11, 17, 21, 13, 10}, { 1, 7, 14, 18, 0}, {23, 12, 8, 24, 9}};
        */

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board initial = new Board(tiles);
    }

}
