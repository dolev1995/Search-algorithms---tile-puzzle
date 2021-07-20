//import java.util.List;

/**
 * The Class PuzzleGameManager represents a puzzle tile game manager. 
 * The game manager receives the following information: Which search algorithm to run, 
 * initial state, and boolean value decides whether print the open list in each iteration of the algorithm. 
 * All game data can be retrieved using the game manager's defined methods. The data is: solution path, string 
 * containing all the steps of the tiles, the cost to the solution, the total number of nodes created, and the run time.
 */
public class PuzzleGameManager {
	private String searchAlgorithm;
	private State initialState;
	private boolean withOpen;
	private SearchInfo searchInfo;
	private String moves;

	/** Empty constructor*/
	public PuzzleGameManager() { }

	/**
	 * Instantiates a new puzzle game manager.
	 * @param searchAlgorithm the search algorithm
	 * @param initialState the initial state
	 * @param withOpen boolean value decides whether to print the open list
	 */
	public PuzzleGameManager(String searchAlgorithm, State initialState, boolean withOpen) {
		this.searchAlgorithm = searchAlgorithm;
		this.initialState = initialState;
		this.withOpen = withOpen;
	}

	/** Start game */
	public void startGame() {
		switch(searchAlgorithm) {
		case("BFS")   : searchInfo = SearchAlgorithms.BFS(initialState, withOpen); break;
		case("DFID")  : searchInfo = SearchAlgorithms.DFID(initialState, withOpen); break;
		case("A*")    : searchInfo = SearchAlgorithms.A_star(initialState, withOpen); break;
		case("IDA*")  : searchInfo = SearchAlgorithms.IDA_star(initialState, withOpen); break;
		case("DFBnB") : searchInfo = SearchAlgorithms.DFBnB(initialState, withOpen); break;
		}
		// find moves 
		moves = findMoves();
		
	}

	/** @return string containing all the steps of the tiles */
	private String findMoves() {
		if(initialState.initial())
			return "The matrices are the same";
		
		//initialState.print();
		
		
		String moves = "";
		
		PuzzleState puzzleState;
		puzzleState = SearchAlgorithms.path;

	//	List<State> solutionPath = searchInfo.getsolutionPath();
		// if there is no solution to the initial state return "no path"
		if (puzzleState == null)
			return "no path";
		
		//moves =  solutionPath.get(0).getParent1();//.Parent;
		
		
		
		moves = puzzleState.getParent1();
		return moves;
	}

	/** @return string containing all the steps of the tiles */
	public String getMoves() {
		return moves;
	}

	/** @return the cost to the solution */
	public int getCost() {
		return searchInfo.getCost();
	}

	/** @return the total number of nodes created */
	public int getNumNodeCreated() {
		return searchInfo.getNumNodesCreated();
	}

	/** @return the algorithm run time */
	public double getTime() {
		return searchInfo.getTime();
	}

	/** @return the solution path */
	public PuzzleState getsolutionPath() {
		return SearchAlgorithms.path;
	}
}