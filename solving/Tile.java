
public class Tile {
	
	/**
	 * This class represents tiles, each tile has its own number,
	 *  the empty tile will get the number 0.
	 * 
	 */
	
		private int number;
		private int costMove;
		// private char costMove;
		// 'r' - Sliding of one tile, 'h' - Sliding two tiles horizontally, 'v' - Sliding two tiles vertically

		/**
		 * Instantiates empty location in the board
		 */
		public Tile() {
			number = 0;
			costMove = 5;
		}

		/**
		 * Instantiates a new tile.
		 * @param number the number of the tile
		 * @param color the color of the tile
		 */
		public Tile(int number) {
			this.number = number;

		}

		/**
		 * @return the number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Sets the number.
		 * @param number the number to set
		 */
		public void setNumber(int number) {
			this.number = number;
		}
		public int getCostMove() {
			return costMove;
		}
//		
		public void setCostMove(int costMove) {
			this.costMove = costMove;
		}


		/**
		 * Checks if the tile is empty.
		 * @return true if the tile empty, false otherwise
		 */
		public boolean isEmpty() {
			return number == 0;
		}

		/**
		 * @return string representation of the tile
		 */
		public String toString() {
			return ""+number;
		}
	}


