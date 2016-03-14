/**	
*					         Domino Class
*					=============================
* The <code>Domino</code> class creates a graphical object to be used in a
* <code>Game</game>
* The upper left posterior corner of the <code>Domino</code>  is drawn at point.
* <p>
* The <code>paint</code> method receives a <code>Graphics</code> object and 
* draws a <code>Domino</code>.
* The <code>drawBlank</code> method receives a <code>Graphics</code> object 
* and draws a blank <code>Domino</code>.
* The <code>drawDot</code> method receives a <code>Graphics</code> object, 
* an <code>int</code> for the X coordinate,and an <code>int</code> for the Y 
* coordinate.
* The <code>drawOne, drawTwo, drawThree, drawFour, drawFive</code> methods 
* receive a <code>Graphics</code> object, and a <code>boolean</code>
* that determines the side of the face of the <code>Domino</code> to be drawn on. 
* The <code>doDominosOverlap</code> method returns true if coordinates passed
* within the area of this <code>Domino</code>.
* The <code>compareFaceValues</code> method checks to see the input parameter
* <code>Domino</code> matches this <code>Domino</code>. 
* The <code>flipDominoHorizontal</code> method will rotate the
<code>Domino</code> 180 degrees.
* 
* 
* @Author Michael Twardowski
*/
import java.awt.Color;						
import java.awt.Graphics;
import java.util.Scanner;

public class Domino
{
	/**
	 * Dimensions for the <code>Domino</code> graphic
	 */
    private static final int DOT_DIAMETER = 20,
    				  		 HEIGHT = 100,
    				  		 WIDTH = 200,
    				  		 DEPTH = 10;
    
    /**
     *  Minimum space between Dominos
     */
    private static final int SPACING = 2;
	
	/**
	 * Holds values for the location of the <code>Domino</code> and the
	 * value for of each side of its front face.
	 */
	private int x,
		        y,
		        leftFaceValue,
		        rightFaceValue;

	/**
	 *	The constructor for a <code>Domino</code> receives a <code>int</code> 
	 * for the X coordinate , Y coordinate, left face value, and right face value
	 *  The upper left posterior corner of the <code>Domino</code> 
	 * is drawn at point (x,y).
	 * 
	 * @param someX is a <code>int</code> for the X coordinate of the Domino
	 * @param someY is a <code>int</code> for the Y coordinate of the Domino
	 * @param leftValue is a <code>int</code> for the left face value.
	 * @param rightValue is a <code>int</code> for the right face value.
	 */
	public Domino(int someX, int someY, int leftValue, int rightValue)
	{
		x = someX;
		y = someY;
		leftFaceValue = leftValue;
		rightFaceValue = rightValue;
	} // end constructor
	

	/**
	 * The <code>paint</code> method receives a <code>Graphics</code> object
	 * and draws a <code>Domino</code>.
	 *
	 * @param pane is a <code>Graphics</code> object
	 */
	public void paint(Graphics pane)
	{
		drawBlank(pane); // draws a blank domino
		
		pane.setColor(Color.blue); // set dot color
		
		// paints dots on left and right face of the Domino based on
		// their value. Passes the boolean for the methods to know which
		// side to draw on.
		boolean side[] = {true, false};
		int value[] = {leftFaceValue, rightFaceValue};
		for(int i = 0; i<2; i++)
		{
			switch(value[i])
			{
				case 1:
					drawOne(pane, side[i]);
					break;
				case 2:
					drawTwo(pane, side[i]);
					break;
				case 3:
					drawThree(pane, side[i]);
					break;
				case 4:
					drawFour(pane, side[i]);
					break;
				case 5:
					drawFive(pane, side[i]);
					break;
			}
		}
	}
	
	/**   
	 * The <code>drawBlank</code> method receives a <code>Graphics</code> object
	 * and draws a blank <code>Domino</code>.
	 *
	 * @param pane is a <code>Graphics</code> object
	 */
    private void drawBlank(Graphics pane)
    {
    	// Arrays hold the X and Y coordinates for the polygons that make up
    	// the Domino
    	int topX[] = {x , x - DEPTH, x + WIDTH - DEPTH, x + WIDTH},
        	topY[] = {y , y - DEPTH, y - DEPTH, y},
			sideX[] = {x , x - DEPTH, x - DEPTH, x},
        	sideY[] = {y , y - DEPTH, y + HEIGHT - DEPTH, y + HEIGHT},
        	faceTotalPoints = 4; // number of total points in the polygon
    	
    	// Draws front face of Domino
    	pane.setColor(Color.white);
    	pane.fillRect(x, y, WIDTH, HEIGHT);
    	
    	// Draws top and left side of Domino
    	pane.setColor(Color.gray);
    	pane.fillPolygon(topX, topY, faceTotalPoints);
    	pane.fillPolygon(sideX, sideY, faceTotalPoints);
    	
    	// Draws outlines for all edges
    	pane.setColor(Color.black);
    	pane.drawRect(x, y, WIDTH, HEIGHT);
    	pane.drawPolygon(topX, topY, faceTotalPoints);
    	pane.drawPolygon(sideX, sideY, faceTotalPoints);
    	
    	// Draws midline of Domino
    	pane.drawLine(x + WIDTH/2, y, x + WIDTH/2, y + HEIGHT);
    	pane.drawLine(x + WIDTH/2, y, x + WIDTH/2 - DEPTH, y - DEPTH);
    }
    
	/**   
	 * The <code>drawDot</code> method receives a <code>Graphics</code> object, 
	 * an <code>int</code> for the X coordinate, and an <code>int</code> for the
	 * Y coordinate.
	 * <p>
	 * It draws a dot, centered at the X, Y coordinates.
	 * <p>
	 * @param pane is a <code>Graphics</code> object
	 * @param centerX is an <code>int</code> corresponding to X coordinate of the dot.
	 * @param centerY is an <code>int</code> corresponding to Y coordinate of the dot.
     */ 
    private void drawDot(Graphics pane, int centerX, int centerY)
	{
    	pane.fillOval(centerX - DOT_DIAMETER/2, centerY - DOT_DIAMETER/2, DOT_DIAMETER, DOT_DIAMETER);
	}
	
    /**   
	 * The <code>drawOne</code> method receives a <code>Graphics</code> object,
	 * and a <code>boolean</code> that determines the side of the face of the
	 * <code>Domino</code> to be drawn on. 
	 * <p>
	 * If the <code>boolean</code> is <code>true</code> the one dot will be 
	 * drawn centered on the left face of the <code>Domino</code>. 
	 * If false, it the dot will be drawn on the right.
	 * <p>
	 * @param pane is a <code>Graphics</code> object
	 * @param isLeft is a <code>boolean</code> 
     */ 
    private void drawOne(Graphics pane, boolean isLeft)
    {
    	int centerX,
    	    centerY;
 
    	if(isLeft)
    	{
    		 centerX = x + WIDTH/4;
    		 centerY = y + HEIGHT/2;
    	}
    	else
    	{
    		centerX = x + WIDTH*3/4;
   		 	centerY = y + HEIGHT/2;
    	}
    	
    	drawDot(pane, centerX, centerY);
    }
    
    /**   
	 * The <code>drawTwo</code> method receives a <code>Graphics</code> object,
	 * and a <code>boolean</code> that determines the side of the face of the 
	 * <code>Domino</code> to be drawn on. 
	 * <p>
	 * If the <code>boolean</code> is <code>true</code> the two dots will be 
	 * drawn on the left face of the <code>Domino</code>. 
	 * If false, it the dots will be drawn on the right.
	 * <p>
	 * @param pane is a <code>Graphics</code> object
	 * @param isLeft is a <code>boolean</code> 
     */ 
    private void drawTwo(Graphics pane, boolean isLeft) 
    {
    	int centerX,
        centerY,
        centerX2,
        centerY2;

		if(isLeft)
		{
			 centerX = x + WIDTH/8;
			 centerY = y + HEIGHT/4;
			 centerX2 = x + WIDTH*3/8;
			 centerY2 = y + HEIGHT*3/4;
		}
		else
		{
			centerX = x + WIDTH*5/8;
			centerY = y + HEIGHT/4;
			centerX2 = x + WIDTH*7/8;
			centerY2 = y + HEIGHT*3/4;
		}
		
		drawDot(pane, centerX, centerY);
		drawDot(pane, centerX2, centerY2);
    }
    
    /**   
	 * The <code>drawThree</code> method receives a <code>Graphics</code> object,
	 * and a <code>boolean</code> that determines the side of the face of the 
	 * <code>Domino</code> to be drawn on. 
	 * <p>
	 * If the <code>boolean</code> is <code>true</code> the three dots will be 
	 * drawn on the left face of the <code>Domino</code>. 
	 * If false, it the dots will be drawn on the right.
	 * <p>
	 * @param pane is a <code>Graphics</code> object
	 * @param isLeft is a <code>boolean</code> 
     */ 
    private void drawThree(Graphics pane, boolean isLeft)
    {
		drawOne(pane, isLeft);
		drawTwo(pane, isLeft);
    }
    
    /**   
	 * The <code>drawFour</code> method receives a <code>Graphics</code> object,
	 * and a <code>boolean</code> that determines the side of the face of the
	 * <code>Domino</code> to be drawn on. 
	 * <p>
	 * If the <code>boolean</code> is <code>true</code> the four dots will be
	 * drawn on the left face of the <code>Domino</code>. 
	 * If false, it the dots will be drawn on the right.
	 * 
	 * @param pane is a <code>Graphics</code> object
	 * @param isLeft is a <code>boolean</code> 
     */ 
    private void drawFour(Graphics pane, boolean isLeft)
    {
    	int centerX,
        centerY,
        centerX2,
        centerY2;

		if(isLeft)
		{
			 centerX = x + WIDTH/8;
			 centerY = y + HEIGHT*3/4;
			 centerX2 = x + WIDTH*3/8;
			 centerY2 = y + HEIGHT/4;
		}
		else
		{
			centerX = x + WIDTH*5/8;
			centerY = y + HEIGHT*3/4;
			centerX2 = x + WIDTH*7/8;
			centerY2 = y + HEIGHT/4;
		}
    	
		drawDot(pane, centerX, centerY);
		drawDot(pane, centerX2, centerY2);
		
    	drawTwo(pane, isLeft);
    }
    
    /**   
	 * The <code>drawFive</code> method receives a <code>Graphics</code> object,
	 * and a <code>boolean</code> that determines the side of the face of the
	 * <code>Domino</code> to be drawn on. 
	 * <p>
	 * If the <code>boolean</code> is <code>true</code> the five dots will be
	 * drawn on the left face of the <code>Domino</code>. 
	 * If false, it the dots will be drawn on the right.
	 * <p>
	 * @param pane is a <code>Graphics</code> object
	 * @param isLeft is a <code>boolean</code> 
     */ 
    private void drawFive(Graphics pane, boolean isLeft)
    {
    	drawFour(pane, isLeft);
    	drawOne(pane, isLeft);
    }
    
    /**
     * The <code>doDominosOverlap</code> method returns true if coordinates passed
     *  lie within the area of this <code>Domino</code>.
     * <p>
     * @param x <code>int</code> for the x coordinate to be checked.
     * @param y <code>int</code> for the y coordinate to be checked.
     * @return true if coordinate the coordinate is the <code>Domino</code> area.
     */
    public boolean doDominosOverlap(int x,int y){
    	if((x >= this.x - DEPTH - WIDTH)&&(x <= this.x + DEPTH + WIDTH)&&
    	  (y >= this.y - DEPTH - HEIGHT)&&(y <= this.y + DEPTH + HEIGHT)){
    		return true;
    	}
    	else{
        	return false;
    	}
    }
    
    /**
     * The <code>compareFaceValues</code> method checks to see the input parameter
     * <code>Domino</code> matches this <code>Domino</code>. 
     * <p>
     * @param domino to be compared to
     */
    public void compareFaceValues(Domino domino){
    	String prompt = "There's no match!";
    	
    	if(leftFaceValue == domino.leftFaceValue){
			domino.flipDominoHorizontal();
			domino.x = x - WIDTH - DEPTH - SPACING;
			domino.y = y;
			prompt = "It's a Match!";
    	}
    	else if(rightFaceValue == domino.leftFaceValue){
    		domino.x = x + WIDTH + DEPTH + SPACING;
			domino.y = y;
			prompt = "It's a Match!";
    	}
    	else if(leftFaceValue == domino.rightFaceValue){
    		domino.x = x - WIDTH - DEPTH - SPACING;
			domino.y = y;
			prompt = "It's a Match!";
    	}
    	else if(rightFaceValue == domino.rightFaceValue){
			domino.flipDominoHorizontal();
			domino.x = x + WIDTH + DEPTH + SPACING;
			domino.y = y;
			prompt = "It's a Match!";
    	}
    	
    	System.out.println(prompt);
    }
    
    /**
     * The <code>flipDominoHorizontal</code> method will rotate the
     *  <code>Domino</code> 180 degrees.
     */
    private void flipDominoHorizontal(){
    	int tempFaceValue = leftFaceValue;
    	leftFaceValue = rightFaceValue;
    	rightFaceValue = tempFaceValue;
    }
}
