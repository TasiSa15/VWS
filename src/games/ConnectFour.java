package games;

import java.util.Random;
import java.util.Scanner;
import constants.Constants;

public class ConnectFour {
	
	private final String[][] m_PlayingField;
	private boolean m_Finish = false;

	public ConnectFour() {
		m_PlayingField = new String [Constants.NUMBER_ROWS][Constants.NUMBER_COLUMNS];
		createPlayingField();
	}
	
	/**
	 * Starts the game Connect Four.
	 * @param pScanner - Scanner for the input
	 */
	public void startConnectFour(Scanner pScanner) {
		System.out.println("You started the game \"Connect Four\"!");

		int tFreeFields = Constants.NUMBER_ROWS * Constants.NUMBER_COLUMNS;

		while(!m_Finish) {
			
			drawPlayingField();
			
			String tChange = getInput(pScanner);
			
			if(isValidInput(tChange)) {
				if(!m_Finish) {
					int tRowIndex = Integer.valueOf(tChange)-1;
					boolean tTileSetSucessfully = setTile(tRowIndex, 
							Constants.PLAYER_SYMBOL, tFreeFields);
					System.out.println(getSucessfulOutput(tTileSetSucessfully));
				}
				
				if(!m_Finish)
					setTileForBot(tFreeFields);
			}
			else {
				System.out.println(Constants.OUTPUT_InputNotValid);
			}
		}
	}
	
	/**
	 * Creates the playing field by setting all values to the free symbol.
	 */
	private void createPlayingField() {
		for(int tRow = 0; tRow < Constants.NUMBER_ROWS; tRow++) {
			for(int tColumn = 0; tColumn < Constants.NUMBER_COLUMNS; tColumn++) {
				m_PlayingField[tRow][tColumn] = Constants.FREE_SYMBOL;
			}
		}
	}

	/**
	 * Draws the playing field in the console.
	 */
	private void drawPlayingField() {
		System.out.println("\n1\t2\t3\t4\t5\t6\t7\n"
				+ "--------------------------------------------------");
		for(int tRow = 0; tRow < Constants.NUMBER_ROWS; tRow++) {
			for(int tColumn = 0; tColumn < Constants.NUMBER_COLUMNS; tColumn++) {
				System.out.print(m_PlayingField[tRow][tColumn] + "\t");
			}
			System.out.println();
		}
		
		System.out.println();
	}
	
	/**
	 * Sets the given symbol in the next free field in the given column.
	 * 
	 * @param pColumnIndex - given column to set the tile in
	 * @param pSymbol - symbol to set in die column
	 * @param pFreeFields - counter of the free fields left in the playing field
	 * @return true if tile is set, else false
	 */
	private boolean setTile(int pColumnIndex, String pSymbol, int pFreeFields) {
		for(int tRow = Constants.NUMBER_ROWS-1; tRow >= 0; tRow--) {
			if(m_PlayingField[tRow][pColumnIndex].equals(Constants.FREE_SYMBOL)) {
				m_PlayingField[tRow][pColumnIndex] = pSymbol;
				pFreeFields--;
				checkIfWon(pSymbol);
				checkForFreeFields(pFreeFields);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Simulates a bot. Sets a tile in a random column on the playing field.
	 * 
	 * @param pFreeFields - counter of the free fields left in the playing field
	 */
	private void setTileForBot(int pFreeFields) {
		Random tRndm = new Random();
		int tColumnIndex = tRndm.nextInt(Constants.NUMBER_COLUMNS);
		setTile(tColumnIndex, Constants.BOT_SYMBOL, pFreeFields);
		System.out.println("Your opponent has set his stone in column " 
				+ (tColumnIndex+1) + "!");
	}
	
	/**
	 * Checks if the player with the given symbol wins.
	 * 
	 * @param pSymbol - symbol of the current player
	 */
	private void checkIfWon(String pSymbol) {

		checkIfWonHorizontal(pSymbol);
		checkIfWonVertical(pSymbol);
		checkIfWonDiagonalDescending(pSymbol);
		checkIfWonDiagonalAscending(pSymbol);
		
		if(m_Finish) {
			System.out.println("Player " + pSymbol + " has won!");
			drawPlayingField();
		}
	}
	
	/**
	 * Checks if the given symbol wins the game with horizontal tiles.
	 * Ends the game if player won.
	 * 
	 * @param pSymbol - symbol of the current player
	 */
	private void checkIfWonHorizontal(String pSymbol) {
		int tCountSameSymbols = 0;
		for(int tRow = 0; tRow < Constants.NUMBER_ROWS; tRow++) {
			tCountSameSymbols = 0;
			for(int tColumn = 0; tColumn < Constants.NUMBER_COLUMNS; tColumn++) {
				if(m_PlayingField[tRow][tColumn].equals(pSymbol)) {
					tCountSameSymbols++;
					if(tCountSameSymbols == Constants.NUMBER_TILESTOWIN)
						m_Finish = true;
				}
				else tCountSameSymbols = 0;
			}
		}
	}
	
	/**
	 * Checks if the given symbol wins the game with vertical tiles.
	 * Ends the game if player won.
	 * 
	 * @param pSymbol - symbol of the current player
	 */
	private void checkIfWonVertical(String pSymbol) {
		int tCountSameSymbols = 0;
		for(int tColumn = 0; tColumn < Constants.NUMBER_COLUMNS; tColumn++) {
			for(int tRow = 0; tRow < Constants.NUMBER_ROWS; tRow++) {
				if(m_PlayingField[tRow][tColumn].equals(pSymbol)) {
					tCountSameSymbols++;
					if(tCountSameSymbols == Constants.NUMBER_TILESTOWIN)
						m_Finish = true;
				}
				else tCountSameSymbols = 0;
			}
		}
	}
	
	/**
	 * Checks if the given symbol wins the game with diagonal ascending tiles.
	 * Ends the game if player won.
	 * 
	 * @param pSymbol - symbol of the current player
	 */
	private void checkIfWonDiagonalAscending(String pSymbol) {
		for(int tRow = Constants.NUMBER_ROWS-1; tRow >= 0; tRow--) {
			for(int tColumn = 0; tColumn < Constants.NUMBER_COLUMNS; tColumn++) {
				if(hasEnoughSymbolsDiagonalAscending(tRow, tColumn, pSymbol))
					m_Finish = true;
			}
		}
	}

	/**
	 * Checks if the given symbol wins the game with diagonal descending tiles.
	 * Ends the game if player won.
	 * 
	 * @param pSymbol - symbol of the current player
	 */
	private void checkIfWonDiagonalDescending(String pSymbol) {
		for(int tRow = 0; tRow < Constants.NUMBER_ROWS; tRow++) {
			for(int tColumn = 0; tColumn < Constants.NUMBER_COLUMNS; tColumn++) {
				if(hasEnoughSymbolsDiagonalDescending(tRow, tColumn, pSymbol))
					m_Finish = true;
			}
		}
	}
	
	/**
	 * Counts same symbols in a diagonal row ascending, using the given symbol.
	 * Starts in given row and column.
	 * 
	 * @param pRow - start row
	 * @param pColumn - start column
	 * @param pSymbol - symbol of the current player
	 * @return true - if there are enough tiles in a row to win, else false
	 */
	private boolean hasEnoughSymbolsDiagonalAscending(int pRow, int pColumn, String pSymbol) {
		int tCurrentRow = pRow;
		int tCurrentColumn = pColumn;
		int tCountSameSymbols = 0;
		
		while(checkIfInsidePlayingField(tCurrentRow, tCurrentColumn)) {
			if(m_PlayingField[tCurrentRow][tCurrentColumn].equals(pSymbol)) {
				tCountSameSymbols++;
				if(tCountSameSymbols == Constants.NUMBER_TILESTOWIN)
					return true;
			}
			else tCountSameSymbols = 0;
			tCurrentRow--;
			tCurrentColumn++;
		}
		return false;
	}

	/**
	 * Counts same symbols in a diagonal row descending, using the given symbol.
	 * Starts in given row and column.
	 * 
	 * @param pRow - start row
	 * @param pColumn - start column
	 * @param pSymbol - symbol of the current player
	 * @return true - if there are enough tiles in a row to win, else false
	 */
	private boolean hasEnoughSymbolsDiagonalDescending(int pRow, int pColumn, String pSymbol) {
		int tCurrentRow = pRow;
		int tCurrentColumn = pColumn;
		int tCountSameSymbols = 0;
		
		while(checkIfInsidePlayingField(tCurrentRow, tCurrentColumn)) {
			if(m_PlayingField[tCurrentRow][tCurrentColumn].equals(pSymbol)) {
				tCountSameSymbols++;
				if(tCountSameSymbols == Constants.NUMBER_TILESTOWIN)
					return true;
			}
			else tCountSameSymbols = 0;
			tCurrentRow++;
			tCurrentColumn++;
		}
		return false;
	}
	
	/**
	 * Checks if the given coordinates are inside the playing field.
	 * 
	 * @param pRow - row
	 * @param pColumn - column
	 * @return true if inside, else false
	 */
	private boolean checkIfInsidePlayingField(int pRow, int pColumn) {
		return pRow >= 0 && pRow < Constants.NUMBER_ROWS 
				&& pColumn >= 0 && pColumn < Constants.NUMBER_COLUMNS;
	}
	
	/**
	 * Checks if there are free fields left. If not finish the game.
	 * 
	 * @param pFreeFields - counter of the free fields left in the playing field
	 */
	private void checkForFreeFields(int pFreeFields) {
		if(pFreeFields == 0) {
			System.out.println("There are no more free fields! Nobody won!");
			m_Finish = true;
		}
	}
	
	/**
	 * Gets the player console input.
	 * 
	 * @param pScanner - Scanner for the input
	 * @return player console input
	 */
	private String getInput(Scanner pScanner) {
		System.out.println("Choose a Column.");
		System.out.print("> ");
		return pScanner.nextLine();
	}
	
	/**
	 * Checks if the given input is valid.
	 * 
	 * @param pInput - player console input
	 * @return true if valid, else false
	 */
	private boolean isValidInput(String pInput) {
		return pInput.matches("[1-7]{1}");
	}
	
	/**
	 * Gets the console output depending on the given boolean.
	 * 
	 * @param pTileSetSucessfully - sucess of the tile setting
	 * @return - console output
	 */
	private String getSucessfulOutput(boolean pTileSetSucessfully) {
		if(pTileSetSucessfully) {
			return Constants.OUTPUT_SucessfulSetting;
		}
		else return Constants.OUTPUT_SomethingWentWrong;
	}
}
