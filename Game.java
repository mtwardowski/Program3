
/**
*							  	Game Class
*						=============================
*	The <code>Game</code> Class extends the <code>Frame</code> Class.
*	<p>
*	The <code>paint</code> method will paint game pieces on <code>Game</code>
*	after then have been initialized.
*	The <code>playersTurn</code> method creates a new <code>Domino</code>
* 	and places it on the <code>Game</code> based on the users input 
*	for its value and location.
*	The <code>computersTurn</code> method creates a new <code>Domino</code>
* 	with randomly generated value and location and places it on the
* 	<code>Game</code> on the game board.
* 	The <code>setDominoValue/code> method prompts a user to enter an 
* 	<code>int</code> between 1 and 5 for the face value of the <code>Domino</code>.
* 	The <code>setCoordinate</code> method prompts a user for a coordinate for
* 	a (<code>Domino</code>) and places it on the <code>Game</code> based on 
* 	that input.
* 	The <code>getIntegerValue</code> method prompts a user to enter an
*	<code>int</code> and returns a result (<code>int</code>).
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
	 * Game piece objects for the player and computer
	 */
	private Domino myDomino, theComputerDomino;
	
	/**
	 * Indicated if a <code>Domino</code> has been initialized
	 */
	private boolean isMyDominoPlaced = false,
					isComputerDominoPlaced = false;
	
	/**
	 * Handle for the <code>Game</code> window.
	 */
	private CloseWindow myWindow;

	/**
	 * Height, and Width for the <code>Game</code> window.
	 * The MAX_VALUE_X and MAX_VALUE_Y is playable area after the 
	 * <code>Game</code> window border and <code>Domino</code> width is 
	 * accounted for.
	 */
	private final int WIDTH = 800, 
					  HEIGHT = 800,
					  MAX_VALUE_X,
					  MAX_VALUE_Y;
	
	/**
	 * Holds the insets for the <code>Game</code> window
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
		
		/* useful for testing
		*for(int i=0; i<50; i++){
		*	myGameTable.computersTurn();
		*}
		*/
	}

	/**
	 * The default constructor for the <code>Game</code>. 
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
		
		// determines the max possible location that a user can select for a
		// domino's position
		MAX_VALUE_X = WIDTH - insets.left - insets.right - dominoWidth - dominoDepth;
		MAX_VALUE_Y = HEIGHT - insets.top - insets.bottom - dominoHeight - dominoDepth;
		
	} // end default constructor

	/**
	 * The <code>paint</code> method will paint each <code>Domino</code> on <code>Game</code>
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
	 * The <code>playersTurn</code> method creates a new <code>Domino</code>
	 * and places it on the <code>Game</code> based on the users input 
	 * for its value and location.
	 */
	private void playersTurn(){

		// sets up values for the domino
		int leftFaceValue = setDominoValue(true);
		int rightFaceValue = setDominoValue(false);
		
		// sets x and y coordinates
		int x = setCoordinate(true);
		int y = setCoordinate(false);
		
		myDomino = new Domino(x, y, leftFaceValue, rightFaceValue);
		isMyDominoPlaced = true;
		
		repaint();
	}
	
	/**
	 * The <code>computersTurn</code> method creates a new <code>Domino</code>
	 * with randomly generated value and location and places it on the
	 * <code>Game</code> on the game board.
	 */
	private void computersTurn(){
		
		// generates random values from 1 to 5 for each face of the domino
		int leftFaceValue = (int)(Math.random()*5 +1);
		int rightFaceValue = (int)(Math.random()*5 +1);
		
		//generates a location for the domino that does not overlap myDomino
		// or lie within a border
		int x, y,
			xOffset = insets.left + 10,
			yOffset = insets.top + 10;
		
		do{
			x = (int)(Math.random()*(MAX_VALUE_X - xOffset) + xOffset);
			y = (int)(Math.random()*(MAX_VALUE_Y - yOffset) + yOffset);
		}while(myDomino.doDominosOverlap(x,y)); // checks for domino overlap
		
		theComputerDomino = new Domino(x, y, leftFaceValue, rightFaceValue);
		
		// checks to see if the new domino matches myDomino
		myDomino.compareFaceValues(theComputerDomino);
		
		// delays display of domino
		try {
		    Thread.sleep(1500);  //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    //Thread.currentThread().interrupt();
		}
		
		isComputerDominoPlaced = true;
		
		repaint();
	}
	
	 /**   
	 * The <code>setDominoValue</code> method prompts a user to enter an 
	 * <code>int</code> between 1 and 5 for the face value of the <code>Domino</code>.
	 * <p>
	 * @param isLeft is a <code>boolean</code> to determine what
	 * side of the domino is being set up.
	 * @return int of the selected value
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
	 * a (<code>Domino</code>) and places it on the <code>Game</code> based on 
	 * that input.
	 * <p>
	 * @param isXCoordinate is a <code>boolean</code> to 
	 * what determine what dimension the coordinate belongs to.
	 * @return int of the selected value for the coordinate
	 */
	private int setCoordinate(boolean isXCoordinate){
		// holds the name of the dimension being setup
		String dimension;
		
		int maxValue,
			offset,
			dominoDepth = 10;
		
		/* prevents the user from entering a position that would clip of part
		* of domino the maximumWidth is:
		* windowWidth - leftBorder  - rightBorder - dominoWidth - depthInX
		* the maximumHeight is:
		* windowHeight - topBorder  - bottomBorder - dominoHeight - depthInY
		* sets the the tableTop origin for new game pieces to be 
		* (leftInset + depthInX, topInset + depthInY)
		* any value entered by the user will be offset by this amount. An entered value of
		* (0,0) will have the top left corner of the game piece in the very top left corner of the tableTop
		*/
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
	 * The <code>getIntegerValue</code> method prompts a user to enter an
	 * <code>int</code> and returns a result (<code>int</code>).
	 * <p>
	 * It accepts two <code>int</code>, <code>min</code>  and <code>max</code>
	 * correspond to the range that this method will accept for user input.
	 * 
	 * @param min is the minimum value of that can be entered.
	 * @param max is the maximum value of that can be entered.
	 * @return result (int) is the coordinate input from the user.
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
