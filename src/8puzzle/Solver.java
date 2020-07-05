package 8puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.List;
import java.util.ArrayList;

public class Solver {
    private final Board start;
    private final int num_moves;
    private final boolean solvable;
    private final List<Board> moves;

    /********************************************************
     *Search Node Class
     ***********************************************************/
    private class Node implements Comparable<Node> {
        Board cur;
        Node prev;
        int priority;
        int moves;
        /********************************************************
         * Parameterized Constuctor
         * @param:
         * cur[Board]: Current board
         * prev[Node]: previous Node
         * manhattan[Boolean]: to indicate which type of heuristic to use.
         **********************************************************/
        Node(Board cur, Node prev,int moves, boolean manhattan){
            this.cur = cur;
            this.prev = prev;
            this.moves =  moves;
            if(manhattan){
                this.priority = this.moves + cur.manhattan();
            }
            else{
                this.priority = this.moves + cur.hamming();
            }
        }
        /********************************************************
         * Method to compare two nodes
         * @param:
         * that[Node]: Other Node object
         * @return: 0 if equal
         *          1 if greater
         *          -1 if less
         **********************************************************/
        public int compareTo(Node that){
            //Node that = (Node) o;
            if(this.priority < that.priority)
                return -1;
            if(this.priority > that.priority)
                return +1;
            return 0;
        }
    }


    /********************************************************
     * Parameterized Constructor
     * Computes the solution to the problem using A* search Algorithm
     *  @param:
     * initial[Board]: Board with initial configuration
     *
     **********************************************************/
    public Solver(Board initial){
        if(initial == null)
            throw new IllegalArgumentException();
        this.start = initial;
        MinPQ<Node> pq1 = new MinPQ<>(); // for the original puzzle

        MinPQ<Node> pq2 = new MinPQ<>(); // for the twin puzzle
        Board twin = this.start.twin();
        pq2.insert(new Node(twin, null,0,true));
        pq1.insert( new Node(this.start, null, 0, true));

        int min_moves = 0;
        //int min_moves2 = 0;
        boolean v1 = false;
        boolean v2 = false;
        int ctr = 0;
        while(true){

            if(ctr % 2 == 0 && pq1.size() > 0){
                if(pq1.min().cur.isGoal()){
                    min_moves = pq1.min().moves;
                    v1 = true;
                    //StdOut.println(this.pq.min().cur);
                    break;
                }

                Node top = pq1.delMin();
                Board cur = top.cur;
                Board prev = top.prev != null ? top.prev.cur: null;
                for(Board board: cur.neighbors()){
                    if ( !board.equals(prev)){
                        pq1.insert(new Node(board, top, top.moves+1, true));
                    }
                }
            }
            else if(ctr % 2 != 0 && pq2.size() > 0){
                if(pq2.min().cur.isGoal()){
                    min_moves = pq2.min().moves;
                    v2 = true;
                    break;
                }

                Node top = pq2.delMin();
                Board cur = top.cur;
                //StdOut.println(cur.isGoal());
                Board prev = top.prev != null ? top.prev.cur: null;
                for(Board board: cur.neighbors()){
                    if ( !board.equals(prev)){
                        pq2.insert(new Node(board, top, top.moves+1, true));
                    }
                }
            }
             ctr++;
            }

        this.num_moves = v1 ? min_moves: -1;
        this.solvable = v1 ? true:false ;

        if(this.solvable)
        {
            this.moves = new ArrayList<>();
            Node current = pq1.delMin();
            while(current != null)
            {
                this.moves.add(0,current.cur);
                current = current.prev;
            }

        }
        else
            this.moves = null;

    }

    /********************************************************
     * Method to check if Board is solvable
     * @param: Node
     * @return: True if solvable
     **********************************************************/
    public boolean isSolvable(){
        return this.solvable;
    }
    /********************************************************
     * Method to get min number of moves to solve initial board
     * @param:
     * board[]: None
     * @return: Integer
     * **********************************************************/
    public int moves(){
        return this.num_moves;
    }

    /********************************************************
     * Method to get sequence of boards in a shortest solution
     * @param:
     * board[]: None
     * @return: List of Steps
     * **********************************************************/
    public Iterable<Board> solution(){
        return this.moves;
    }

    // test client (see below)
    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Board twin = initial.twin();
        Solver solver = new Solver(initial);
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }


}
