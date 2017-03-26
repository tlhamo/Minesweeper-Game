import objectdraw.*;
import java.awt.*;
/**
 * -Creates an individual cell
 * -Methods that modify an individual cell
 * 
 * @author Tseki Lhamo
 * @version 7th December 2016
 */ 
public class GridCell
{
    //a framed rect over a filled recct will give a cell
    private FramedRect border;
    private FilledRect singleCell;
    //filledOval for mines
    private FilledOval mine;
    private DrawingCanvas canvas;
    //size of the cell which is a square and the size of mine
    private static final int CELL_SIZE = 35,
                            MINE_SIZE = 25;
    private int left, //left and top of cell
                top;  
    private Text countText;  //for displaying the neighboring count
    //inital state of boolean variables which will later be changed depending on the methods called from Grid class
    private boolean hasMine = false;     
    private boolean isFlagged = false;   
    private boolean isLeftClicked = false;  
    private boolean mineCheck = false;    

    public GridCell(int left, int top, DrawingCanvas canvas){
        this.left = left;
        this.top = top;
        this.canvas = canvas; 
        singleCell = new FilledRect(left, top, CELL_SIZE, CELL_SIZE, canvas); 
        singleCell.setColor(Color.GRAY);
        border = new FramedRect(left, top, CELL_SIZE, CELL_SIZE, canvas); 
    }

    public boolean isMine(){ //return if there is a mine there or not 
        return mineCheck;   
    }

    public void setMine(boolean mineExists){ //grid cell passes a boolean that is either true or false
        mineCheck = mineExists;             //the value of the argument passed is assigned to mineCheck
        if (mineCheck == true){                   //if mineCheck is true
            mine = new FilledOval(left + CELL_SIZE/2 - MINE_SIZE/2, top+ CELL_SIZE/2 - MINE_SIZE/2, MINE_SIZE, MINE_SIZE, canvas);
            mine.hide(); //hide the mines initially and if it isn't clicked
            hasMine = true;   //remembers that there is a mine in that location
        }            
    }

    public boolean checkMine () { //returns either true or false depending on the saved outcome in that cell location
        return hasMine;
    }

    public void setFlag(){ //sets flag to blue when right clicked
        singleCell.setColor(Color.BLUE);
        isFlagged = true;    //remembers that the cell is flagged
    }

    public boolean checkFlag(){   //returns whether that cell is flagged or not
        return isFlagged;           
    }

    public void removeFlag(){   //sets the color of cell to gray 
        singleCell.setColor(Color.GRAY);
        isFlagged = false;          //remembers that that cell is now not flagged
    }

    public void setRed(){          //sets cell to red 
        singleCell.setColor(Color.RED);
    }

    public void displayNeighbor(int count){ //displays the number of mines around the cell (passed through parameter)

        if (count != 0) {  //when count is 0, simply set cell to white (don't display count)
            countText = new Text (count, singleCell.getX() + CELL_SIZE/2, singleCell.getY() + CELL_SIZE/2, canvas);
            countText.hide();
            countText.move(-countText.getWidth()/2, -countText.getHeight()/2); 

        }
    }

    public void setWhite(){
        singleCell.setColor (Color.WHITE); //sets cell to white
        if(countText != null){              //avoids null pointer exception when the count on the square is zero, which means there is no countText that can be shown
            countText.show();             
        }
        isLeftClicked = true;//remembers that cell has been left clicked on
    } 

    public boolean checkLeftClick(){    //returns whether cell is left clicked on or not
        return isLeftClicked;
     }

    public void gameOver(){ //shows the mine 
        mine.show();     
    }

}

