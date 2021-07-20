//import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.LinkedList;
//import java.util.List;


public class Ex1 {

	static String searchAlgorithm;
	static boolean withTime, withOpen;
	static PuzzleState initialState;

	/**
	 * The main method.
	 */
	public static void main(String[] args) {

		readInput();
		// initialize the game and start game
		PuzzleGameManager manager = new PuzzleGameManager(searchAlgorithm, initialState, withOpen);
		manager.startGame();
		// Write result
		writeOutput(manager);
	}

		/**  Read input from "input.txt" */
		public static void readInput() {
			BufferedReader reader;	
			try {
				reader = new BufferedReader(new FileReader("input.txt"));
				// Read the search algorithm to run
				searchAlgorithm = reader.readLine();
				// Decide whether to write the run time and print the open list
				if(reader.readLine().matches("with time")) withTime = true;
				if(reader.readLine().matches("with open")) withOpen = true;
				// Obtain the number of rows and columns of the board
				String boardSize = reader.readLine();
				int indexOfX = boardSize.indexOf('x');
				int numRows = Integer.parseInt(boardSize.substring(0, indexOfX));
				int numCols = Integer.parseInt(boardSize.substring(indexOfX+1, boardSize.length()));
			
				
				
				// Create the tile board
				Tile[][] board = new Tile[numRows][numCols];
				for(int i=0; i<numRows; i++) {
					String[] stateRow = reader.readLine().split(",");
					for(int j=0; j<numCols; j++) {
						if(stateRow[j].charAt(0) != '_') {
							int number = Integer.parseInt(stateRow[j]);
							board[i][j] = new Tile(number);
						}
						else board[i][j] = new Tile();
					}
				}

				// Create the Goal board
				reader.readLine();
				Tile[][] Goal = new Tile[numRows][numCols];
				for(int i=0; i<numRows; i++) {
					String s = reader.readLine();
					String[] stateRow = s.split(",");

					for(int j=0; j<numCols; j++) {

						if(stateRow[j].charAt(0) != '_') {
								int number = Integer.parseInt(stateRow[j]);
								Goal[i][j] = new Tile(number);
						
						}
						else Goal[i][j] = new Tile();
					}
				}
				
				
				// Create the initial state of the game
				initialState = new PuzzleState(board,Goal);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		/**
		 * Write output to "output.txt"
		 * @param manager The game manager who ran the game
		 */
		public static void writeOutput(PuzzleGameManager manager) {
			PrintWriter writer;
			try {
				writer = new PrintWriter(new FileWriter("output.txt"));
				// Write the moves of each tile to solution and the total number of nodes created
				writer.println(manager.getMoves());
				writer.println("Num: " + manager.getNumNodeCreated());
				// Write the cost of the solution if one exists
				if(manager.getsolutionPath() != null) {
					writer.println("Cost: " + manager.getCost());
					// Write the time if chooses to write it
					if(withTime)
						writer.println(manager.getTime() + " seconds");	
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
