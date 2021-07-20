
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a state of the Colored Tile Puzzle game. 
 * PuzzleState implements State interface, and therefore, using Polymorphism it
 * can be entered as a parameter for the search algorithms.
 */
public class PuzzleState implements State {

	private static int discovery = 0;
	private Tile[][] board;
	private Tile[][] Goal;
	/** the (x,y) of the empty location on the board. */
	private Point empty1;
	private Point empty2;
	private State parent;
	private String Parent = "";
	private int cost, discoveryTime; 
	/** Remark if this state is out of the open list. */ 
	private boolean out;
	/** Are there 2 empty tiles on the board.  */
	private boolean twoEmpty;	

	List<Point> ListEmpty = new LinkedList<Point>();


	/** Empty constructor */
	public PuzzleState() { }

	/**
	 * Instantiates a new puzzle state.
	 * @param board the board
	 */


	/**
	 * Instantiates a new puzzle state.
	 * @param board the board
	 * @param empty the empty location
	 * @param lastOperator the last operator
	 * @param parent the parent of this state
	 * @param cost the cost from initial state to current state
	 */
	public PuzzleState(Tile[][] board) {
		this.board = board;
		this.discoveryTime = discovery++;
		this.out = false;
	}

	public PuzzleState(Tile[][] board, Point empty1, State parent, int cost) {
		this.board = board;
		this.empty1 = empty1;
		this.parent = parent;
		this.cost = cost;
		this.discoveryTime = discovery++;
		this.out = false;
		this.twoEmpty = false;
	}
	public PuzzleState(Tile[][] board,Tile[][] Goal ) {
		this.board = board;
		this.Goal = Goal;
		this.discoveryTime = discovery++;
		//Check some empty tiles

		int counter = 0;

		// search for the empty location
		for(int i=0; i<board.length; i++) //y
		{
			for(int j=0; j<board[i].length; j++)  //x 
			{
				if(board[i][j].isEmpty() && !blockEmpty(board) )	//There is only one empty tile
				{
					this.empty1 = new Point(j,i); 
					ListEmpty.add(empty1);
					break;
				}
				else if(board[i][j].isEmpty() && blockEmpty(board) && counter == 0)//There are two empty tiles and now we have found the first one
				{	
					this.empty1 = new Point(j,i);
					ListEmpty.add(empty1);
					counter++;
				}
				else if(board[i][j].isEmpty() && blockEmpty(board) && counter == 1)//There are two empty tiles and now we have found the second
				{				
					this.empty2 = new Point(j,i);
					ListEmpty.add(empty2);

				}


			}
		}
	}

	// if have one empty block return false, otherwise return true
	public boolean blockEmpty(Tile[][] board)
	{
		this.board = board;
		int count = 0;
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				if(board[i][j].isEmpty() ) {
					count++;
				}
			}
		}
		if(count == 1) return false;
		else return true;

	}

	/*
	 * We will deal with the initial state, i.e., that the two matrices are identical
	 * @return true if the two matrices are identical, else return false.
	 */
	public boolean initial() {
		int numRows = board.length, numCols = board[0].length;
		for (int i=0;i<numRows;i++) {
			for (int j=0;j<numCols;j++) {
				if(board[i][j].getNumber() != Goal[i][j].getNumber())
					return false;
			}
		}
		return true;
	}

	public void print() {
		int numRows = board.length, numCols = board[0].length;
		System.out.println("the board is:");
		for (int i=0;i<numRows;i++) {
			for (int j=0;j<numCols;j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}

		System.out.println("the goal is:");
		for (int i=0;i<numRows;i++) {
			for (int j=0;j<numCols;j++) {
				System.out.print(Goal[i][j].getNumber());
			}
			System.out.println();
		}

	}
	/*	
	 * Checks whether the operator is valid, which means it does not exceed out of
	 * board limits
	 * @param row 	the row is given by the operator.
	 * @param column 	the column is given by the operator.
	 * @return true if the operator is valid, else return false.
	 */
	public boolean IsLegal(int row, int column) {
		int numRows = board.length, numCols = board[0].length;
		if (!(row >= 0 && row < numRows && column >= 0 && column < numCols))
			return false;
		return true;
	}



	public PuzzleState Clone()
	{
		int numRows = board.length, numCols = board[0].length;
		Tile[][] new_board= new Tile[numRows][numCols];
		for (int i=0;i<numRows;i++) {
			for (int j=0;j<numCols;j++) {
				new_board[i][j]= board[i][j];
			}
		}


		PuzzleState result = new PuzzleState(new_board, Goal);
		return result;
	}



	public Tile[][] Clone(Point from, Point to)
	{
		int numRows = board.length, numCols = board[0].length;
		Tile[][] new_board= new Tile[numRows][numCols];
		for (int i=0;i<numRows;i++) {
			for (int j=0;j<numCols;j++) {
				new_board[i][j]= board[i][j];
			}
		}

		Tile a = new_board[from.y][from.x];
		new_board[from.y][from.x] = new_board[to.y][to.x] ;


		//PuzzleState result = new PuzzleState(new_board, Goal);
		return new_board;
	}



	public Tile[][] CloneNew() // only copy
	{
		int numRows = board.length, numCols = board[0].length;
		Tile[][] new_board= new Tile[numRows][numCols];
		for (int i=0;i<numRows;i++) {
			for (int j=0;j<numCols;j++) {
				new_board[i][j]= board[i][j];
			}
		}
		return new_board;

	}




	public String path(PuzzleState s,List<Point>pointMoved, String direct,int cost1, int cost2)
	{
		if(s.Parent !="")
			s.Parent+="-";
		if(pointMoved.size() == 1)
		{
			s.Parent += cost1+direct;
		}
		else if(pointMoved.size() == 2)
		{
			switch (direct) {
			case "LL":
				s.Parent += cost1+"&"+cost2+"L";
				break;
			case "RR":
				s.Parent += cost1+"&"+cost2+"R";
				break;
			case "UU":
				s.Parent += cost1+"&"+cost2+"U";
				break;
			case "DD":
				s.Parent += cost1+"&"+cost2+"D";
				break;
			}
		}
		return Parent;
	}



	/**
	 * Gets all allowed operators from this state.
	 * Will never get back to parent state.
	 * @return a list of all allowed operators
	 */
	@Override
	public List<State> operators() {
		//if(blockEmpty(board) == false)	//1 tile empty
		List<State> statesList = new LinkedList<State>();	

		if(ListEmpty.size() == 2)
		{ 
			
			if( Math.abs(empty1.x - empty2.x) == 1 && empty1.y == empty2.y )
			{
				if(empty1.y>0)
				{
					List<Point>pointMoved = new LinkedList<Point>();
					Point above1 = new Point(empty1.x ,empty1.y-1);  
					Point above2 = new Point(empty2.x ,empty2.y-1 );  
					pointMoved.add(above1);
					pointMoved.add(above2);
					PuzzleState s =	moveTileNew(above1,above2, empty1, empty2);
					s.cost = cost+7;
					path(s,pointMoved,"DD",board[empty1.y-1][empty1.x].getNumber(),board[empty2.y-1][empty2.x].getNumber());
					statesList.add(s);
					board[empty1.y-1][empty1.x].setCostMove(7);
					board[empty2.y-1][empty2.x].setCostMove(7);
				}
				 if(empty1.y<board.length-1)
				{
					List<Point>pointMoved = new LinkedList<Point>();
					Point below1 = new Point( empty1.x,empty1.y+1);  
					Point below2 = new Point( empty2.x,empty2.y+1 );  
					pointMoved.add(below1);
					pointMoved.add(below2);
					PuzzleState s =	moveTileNew(below1,below2, empty1, empty2);
					s.cost = cost+7;
					path(s,pointMoved,"UU",board[empty1.y+1][empty1.x].getNumber(),board[empty2.y+1][empty2.x].getNumber());
					statesList.add(s);
					board[empty1.y+1][empty1.x].setCostMove(7);
					board[empty2.y+1][empty2.x].setCostMove(7);
				}
			}
			else if( Math.abs(empty1.y - empty2.y) == 1 && empty1.x == empty2.x)
			{
				if(empty1.x>0)
				{
					List<Point>pointMoved = new LinkedList<Point>();
					Point right1 = new Point(empty1.x-1 ,empty1.y);  
					Point right2 = new Point(empty2.x-1 ,empty2.y );  
					pointMoved.add(right1);
					pointMoved.add(right2);
					//PuzzleState s =	this.moveTile(right1, empty1).moveTile(right2, empty2);
					PuzzleState s =	moveTileNew(right1,right2, empty1, empty2);
					s.cost = cost+6;
					path(s,pointMoved,"RR",board[empty1.y][empty1.x-1].getNumber(),board[empty2.y ][empty2.x-1].getNumber());
					statesList.add(s);
					board[empty1.y][empty1.x-1].setCostMove(6);
					board[empty2.y ][empty2.x-1].setCostMove(6);
				}
				 if(empty1.x< board[0].length-1)
				{
					List<Point>pointMoved = new LinkedList<Point>();
					Point left1 = new Point(empty1.x+1,empty1.y  );  
					Point left2 = new Point(empty2.x+1, empty2.y); 
					pointMoved.add(left1);
					pointMoved.add(left2);
					//PuzzleState s =	this.moveTile(left1, empty1).moveTile(left2, empty2);
					PuzzleState s =	moveTileNew(left1,left2, empty1, empty2);
					s.cost = cost+6;
					path(s,pointMoved,"LL",board[empty1.y][empty1.x+1 ].getNumber(),board[empty2.y][empty2.x+1 ].getNumber());
					statesList.add(s);
					board[empty1.y][empty1.x+1].setCostMove(6); 
					board[empty2.y][empty2.x+1 ].setCostMove(6);
				}


			}

		}
		if(ListEmpty.size()>1)
			sortList();
		for (Point point : ListEmpty)			//only one point empty
		{ 
			if(point.x+1<board[0].length && board[point.y][point.x+1].getNumber() != 0)		//left
			{		
				List<Point>pointMoved = new LinkedList<Point>();
				Point from = new Point(point.x+1 ,point.y );
				pointMoved.add(from);
				PuzzleState s = moveTile(from,point);
				s.parent = this;
				s.cost = cost+5;
				path(s,pointMoved,"L",board[point.y][point.x+1].getNumber(),0);
				statesList.add(s);
				board[point.y][point.x+1].setCostMove(5);
			}
			if(point.y+1<board.length && board[point.y+1][point.x].getNumber() != 0)    //up
			{		
				List<Point>pointMoved = new LinkedList<Point>();
				Point from = new Point(point.x,point.y+1 );
				pointMoved.add(from);
				PuzzleState s = moveTile(from,point);
				s.parent = this;
				s.cost = cost+5;				
				path(s,pointMoved,"U",board[point.y+1][point.x].getNumber(),0);
				statesList.add(s);
				board[point.y+1][point.x].setCostMove(5);
			}	
			if(point.x>0 && board[point.y][point.x-1].getNumber() != 0)		//right
			{		
				List<Point>pointMoved = new LinkedList<Point>();
				Point from = new Point(point.x-1 ,point.y );
				pointMoved.add(from);
				PuzzleState s = moveTile(from,point);
				s.parent = this;
				s.cost = cost+5;
				path(s,pointMoved,"R",board[point.y][point.x-1].getNumber(),0);
				statesList.add(s);
				board[point.y][point.x-1].setCostMove(5);
			}
			if(point.y>0 && board[point.y-1][point.x].getNumber() != 0)		//down
			{		
				List<Point>pointMoved = new LinkedList<Point>();
				Point from = new Point(point.x  ,point.y-1);
				pointMoved.add(from);
				PuzzleState s = moveTile(from,point);
				s.parent = this;
				s.cost = cost+5;		
				path(s,pointMoved,"D",board[point.y-1][point.x].getNumber(),0);
				statesList.add(s);
				board[point.y-1][point.x].setCostMove(5);
			}

		}
		return statesList;
	}

	private void sortList ()
	{
		Point point1 = ListEmpty.get(0);
		Point point2 = ListEmpty.get(1);
		if(ListEmpty.get(0).getX()>ListEmpty.get(1).getX()) {
			//replace
			ListEmpty.set(0, point2);
			ListEmpty.set(1, point1);
		}
		else if(ListEmpty.get(0).getX()==ListEmpty.get(1).getX())
		{
			if(ListEmpty.get(0).getY()>ListEmpty.get(0).getY()) {
				//replace
				ListEmpty.set(0, point2);
				ListEmpty.set(1, point1);
			}
		}

	}

	private PuzzleState moveTileNew(Point from1, Point from2,Point empty1, Point empty2) {



		Tile[][]  result = this.CloneNew();
		result[empty1.y][empty1.x] = this.board[from1.y][from1.x];
		result[from1.y][from1.x] = this.board[empty1.y][empty1.x] ;


		result[empty2.y][empty2.x] = this.board[from2.y][from2.x];
		result[from2.y][from2.x] = this.board[empty2.y][empty2.x] ;
		PuzzleState a = new PuzzleState(result,Goal);

		a.Parent = this.Parent;


		return a;

	}



	// A private method designed to create a new board with sliding tile to the new empty location
	private PuzzleState moveTile(Point from, Point to) {

		Tile[][]  result = this.Clone( from,  to);
		result[to.y][to.x] = this.board[from.y][from.x];
		result[from.y][from.x] = this.board[to.y][to.x] ;

		PuzzleState a = new PuzzleState(result,Goal);

		a.Parent = this.Parent;

		return a;
	}






	/**
	 * Checks if this is goal state. That is, checks whether 
	 * all numbers are arranged and the empty location is at the end
	 * @return true if goal, false otherwise
	 */
	@Override				
	public boolean IsGoal() {
		int numRows = board.length, numCols = board[0].length;
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				// Checks that the tile is in its proper place and also that it is not the empty
				if(board[i][j].getNumber() != Goal[i][j].getNumber())
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Heuristic function that returns an estimate of the cost from this state to the goal state.
	 * I have defined this function much like the Manhattan distance method. That is, to sum 
	 * the optimal number of steps for each tile, Which is not empty, from its current location to the 
	 * place it needs to reach. 
	 * @return estimation of the cost from this state to the target state
	 */
	@Override
	public int h() {   
		int numRows = board.length, numCols = board[0].length;
		int totalDistance = 0, counter = 0,distance = 0;
		for(int i=0; i<numRows; i++) {
			for(int j=0; j<numCols; j++) {
				if(board[i][j].getNumber() != 0)
				{
					int number = board[i][j].getNumber() - 1;
					// Calculate distance
					distance = Math.abs(number/numCols - i) + Math.abs(number%numCols - j);
					//distance = Math.abs(board[i][j].)
					if(board[i][j] == Goal[i][j])
					{
						//distance = Manhattan(i,j);
						//counter++;
						totalDistance = 0; 
					}
					if(board[i][j].getCostMove() == 5)
						totalDistance += (distance*4);
					else
						totalDistance += (distance*3);	
						//totalDistance += (distance*(counter+1));
				}
			}
		}
		return totalDistance;




		//		int numRows = board.length, numCols = board[0].length, counter = 0;
		//		for(int i=0; i<numRows; i++) {
		//			for(int j=0; j<numCols; j++) {
		//				if(board[i][j] == Goal[i][j])
		//					counter++;
		//			}
		//		}
		//		return counter;
	}

	
	public int Manhattan(int x, int y)
	{
		int sumManhattan = 0;
	
		for(int i=0; i<Goal.length; i++) {
			for(int j=0; j<Goal[0].length; j++) {
				sumManhattan = (Math.abs(x-i) + Math.abs(y-j));
				
			}
		}
		return sumManhattan;
	}
	





	//	public int Manhattan_Distance_Heuristic(Board goal) {
	//        int totalDistance = 0;
	//        for (int i = 0; i < board.getNumber_rows(); i ++) {
	//            for (int j = 0; j < board.getNumber_columns(); j ++) {
	//                Square currentSquare = board.getBoard()[i][j];
	//                if (currentSquare.getIsEmpty())
	//                    continue;
	//                int squareNumber = currentSquare.getNumber();
	//                Location locationInInit = currentSquare.getLocation();
	//                Location locationInGoal = goal.getLocationOfNumber(squareNumber);
	//                int distance = Location.manhattan(locationInGoal, locationInInit);
	//                totalDistance += distance;
	//            }
	//        }
	//        return totalDistance;
	//    }





























	/** @return the parent state that leads to this state */
	@Override
	public State getParent() {
		return parent;
	}

	/** @return the total cost from the initial state to this state */
	@Override
	public int getCost() {
		return cost;
	}

	/** @return the discovery time of this state */
	@Override
	public int getDiscoveryTime() {
		return discoveryTime;
	}

	/**
	 * Checks if this state is out of the open list.
	 * The main use is in the DFBnB algorithm.
	 * @return true if this state is out from the open list, false otherwise
	 */
	@Override
	public boolean isOut() {
		return out;
	}

	/**
	 * Sets this state to be out or in the open list.
	 * @param isOut the value to set
	 */
	@Override
	public void setOut(boolean out) {
		this.out = out;
	}

	/** @return the lastOperator that leads to this state */
	public int getLastOperator() {
		if(parent == null) return 0;
		if(twoEmpty == false)
		{
			PuzzleState puzzleState = (PuzzleState)parent;
			if(empty1.y == puzzleState.empty1.y+1) return 1;
			else if(empty1.x == puzzleState.empty1.x+1) return 2;
			else if(empty1.y == puzzleState.empty1.y-1) return 3;
			else return 4;
		}
		else
		{
			PuzzleState puzzleState = (PuzzleState)parent;
			if(empty1.y == puzzleState.empty1.y+1 && empty2.y == puzzleState.empty2.y+1 ) return 1;
			else if(empty1.x == puzzleState.empty1.x+1 && empty2.x == puzzleState.empty2.x+1 ) return 2;
			else if(empty1.y == puzzleState.empty1.y-1 && empty2.y == puzzleState.empty2.y-1 ) return 3;
			else return 4;

		}

	}

	public String getParent1()
	{
		return Parent;
	}
	/** @return The last tile number moved to reach the current state */
	public int getTileMoved() {
		if(parent == null) return 0;
		PuzzleState puzzleState = (PuzzleState)parent;
		//System.out.println("empty1.x = "+empty1.x);
		//System.out.println("empty1.y= " +empty1.y);
		//System.out.println("getTileMoved = " + puzzleState.board[empty1.x][empty1.y].getNumber());
		return puzzleState.board[empty1.x][empty1.y].getNumber();
	}

	/**
	 * Gets the tile in the location (x,y)
	 * @return the tile
	 */
	public Tile getTile(int x, int y) {
		return board[x][y];
	}

	/**
	 * Compares two states and returns true if equal
	 * @param obj the object to compare
	 * @return true if equals, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		return this.toString() == obj.toString();
	}

	/**
	 * @return String representation of this state
	 */
	@Override
	public String toString() {
		String stateString = "";
		for (Tile[] row : board) 
			stateString += Arrays.toString(row) + "\n";

		return stateString;
	}

	/** @return the max threshold */
	@Override
	public int maxThreshold() {
		return Math.min(factorial(board.length*board[0].length-1), Integer.MAX_VALUE);
	}

	public int factorial(int n) {
		if(n>12) return Integer.MAX_VALUE;
		int fact = 1;
		for (int i = 2; i <= n; i++) {
			fact = fact * i;
		}
		return fact;
	}
}