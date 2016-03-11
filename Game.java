
/**
*							  		Game Class
*							=============================
*	The <code>Game</code> Class extends the <code>Frame</code> Class t
*	<p>
*	The <code>paint</code> method will paint game pieces on <code>Game</code>
*	after then have been initialized.
*	The <code>setupGamePiece</code> method creates a new game piece (<code>Domino</code>)
*	and places it on the <code>Game</code> based on the users input for its value and location.
*	The <code>placeGamePiece</code> method prompts a user for an X and Y coordinates for
*	(<code>Domino</code>) and places it on the <code>Game</code> based on that input.
*	The <code>getInteger</code> method prompts a user to enter an <code>int</code> 
*	and returns a result (int) if its exists inside the <code>Game</code> playable area.
*	
*	@Author Michael Twardowski
*/
import java.util.Scanner;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics;

public class Game extends Frame 
{
	/**
	 * Game piece object
	 */
	private Domino myDomino, theComputerDomino;
	
	/**
	 * Indicated if a Domino has been initialized
	 */
	private boolean isMyDominoPlaced = false,
					isComputerDominoPlaced = false;
	
	/**
	 * Handle for the <code>Game</code> window.
	 */
	private CloseWindow myWindow;

	/**
	 * Height and Width for the <code>Game</code> window.
	 */
	private final int WIDTH = 800, 
					  HEIGHT = 800,
					  MAX_VALUE_X,
					  MAX_VALUE_Y;
	
	/**
	 * Holds the insets for the created window
	 */
	
	private Insets insets;

	/**
	 * Instantiates a <code>Game</code> on which to place <code>Dominos</code>.
	 */
	public static void main(String[] args) 
	{
		Game myGameTable;
		myGameTable = new Game();
		
		myGameTable.playersTurn();
		myGameTable.computersTurn();
	}

	/**
	 * The default constructor for the <code>Game</code> Frame. 
	 */
	public Game() 
	{
		setTitle("Dominos");
		setLocation(50, 50);
		setSize(WIDTH, HEIGHT);
		setBackground(Color.lightGray);

		myWindow = new CloseWindow(); // allows for window closing
		addWindowListener(myWindow);
		
		System.out.println("Welcome to Dominos!");
		
		setVisible(true);
		
		insets = getInsets();
		
		int dominoWidth = 200,
			dominoHeight = 100,
			dominoDepth = 10;
		
		MAX_VALUE_X = WIDTH - insets.left - insets.right - dominoWidth - dominoDepth;
		MAX_VALUE_Y = HEIGHT - insets.top - insets.bottom - dominoHeight - dominoDepth;
		
	} // end default constructor

	/**
	 * The <code>paint</code> method will paint game pieces on <code>Game</code>
	 * after then have been initialized.
	 */
	public void paint(Graphics pane){
		if(isMyDominoPlaced){
			myDomino.paint(pane);
			if(isComputerDominoPlaced){
				theComputerDomino.paint(pane);
			}
			
		}
	}
	
	/**
	 * The <code>playersTurn</code> method creates a new game piece (<code>Domino</code>)
	 * and places it on the <code>Game</code> based on the users input for its value and location.
	 */
	private void playersTurn(){
		int leftFaceValue = setDominoValue(true);
		int rightFaceValue = setDominoValue(false);
		int x = setCoordinate(true);
		int y = setCoordinate(false);
		myDomino = new Domino(x, y, leftFaceValue, rightFaceValue);
		isMyDominoPlaced = true;
		
		repaint();
	}
	
	/**
	 * The <code>computersTurn</code> method creates a new game piece (<code>Domino</code>)
	 * and places it on the <code>Game</code> based on the users input for its value and location.
	 */
	private void computersTurn(){
		int leftFaceValue = (int)(Math.random()*5 +1);
		int rightFaceValue = (int)(Math.random()*5 +1);
		int x, y;
		
		do{
			x = (int)(Math.random()*MAX_VALUE_X +1);
			y = (int)(Math.random()*MAX_VALUE_Y +1);
		}while(myDomino.doDominosOverlap(x,y));
		
		theComputerDomino = new Domino(x, y, leftFaceValue, rightFaceValue);
		myDomino.compareFaceValues(theComputerDomino);
		isComputerDominoPlaced = true;
		
		repaint();
	}
	
	 /**   
	 * The <code>setDominoValue/code> method prompts a user to enter an <code>int</code> 
	 * for the left and right face value of the <code>Domino</code>.
	 */
	private int setDominoValue(boolean isLeft){
		String side;
		
		int minimumValue = 1,
		    maximumValue = 5;
		
		if(isLeft){
			side = "left";
		}
		else{
			side = "right";
		}
		
		System.out.println("Enter a value for the " + side + " face of a domino: ");
		int faceValue = getIntegerValue(minimumValue, maximumValue);
		
		return faceValue;
	}

	/**
	 * The <code>setCoordinate</code> method prompts a user for a coordinate for
	 * a (<code>Domino</code>) and places it on the <code>Game</code> based on that input.
	 * @return
	 */
	private int setCoordinate(boolean isXCoordinate){
		String dimension;
		
		int maxValue,
			offset,
			dominoDepth = 10;
		
		//prevents the user from entering a position that would clip of part of domino
		// the maximumWidth is the windowWidth - leftBorder  - rightBorder - dominoWidth - depthInX
		// the maximumHeight is the windowHeight - topBorder  - bottomBorder - dominoHeight - depthInY
		/** check this
		 * 
		 */
		// sets the the tableTop origin for new game pieces to be 
				// (leftInset + depthInX, topInset + depthInY)
				// any value entered by the user will be offset by this amount. An entered value of
				// (0,0) will have the top left corner of the game piece in the very top left corner of the tableTop
		if(isXCoordinate){
			dimension = "x";
			maxValue = MAX_VALUE_X;
			offset = insets.left + dominoDepth;
		}
		else{
			dimension = "y";
			maxValue = MAX_VALUE_Y;
			offset = insets.top + dominoDepth;
		}

		System.out.println("Enter a " + dimension + " coordinate for the domino.");
		
		int coordinate = getIntegerValue(0, maxValue);
		
		return coordinate + offset;
	}
	
	/**
	 * The <code>getInteger</code> method prompts a user to enter an <code>int</code> 
	 * and returns a result (int) if its exists inside the <code>Game</code> playable area.
	 * <p>
	 * It accepts a <code>length</code> (int) that is the length of <code>Game</code> in a X or Y dimension.
	 * 
	 * @param <code>length</code> (int) is the length of <code>Game</code> in a X or Y dimension.
	 * @return <code>result</code> (int) is the coordinate input from the user.
	 */
	private int getIntegerValue(int min, int max){
		String prompt = "Please enter an integer from " + min + " to " + max + ".";
	    System.out.println(prompt);
	    while(true){
	        try 
	        {
	            int result = Integer.parseInt(new Scanner(System.in).next()); // checks if result is on integer
	            if((result < min)||(result > max)) // true if the integer is outside the Game playable area
				{
	            	System.out.println("The integer you entered is outside the range.\n" + prompt);
	            	continue; // continues to the next loop iteration
				}
	            return result; // returns if input is an integer and in the Game playable area
	            
	        } 
	        catch(NumberFormatException ne) 
	        {
	            System.out.println("That's not a integer.\n" + prompt);
	        }
	    }
	}
}
