import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Has the GUIs and calls methods in mousePressed
 * has reset method that will be called on newGameButton.
 * Also contains the difficulty levels
 * 
 * @author Tseki Lhamo
 * @version 7th December 2016
 */
public class Minesweeper extends WindowController implements MouseListener, ActionListener
{
    private Grid displayGrid;  //grid        
    private int count = 0;  //the initial count of the number of flags 
    private int lastDimension; //remembers the last dimensions of the grid
    private JLabel minesFound;   // for mines flagged tally
    private JPanel southPanel; //panel for timer, new game button and minesflagged tally
    private JComboBox difficulty; //levels
    private JButton newGameButton;  //reset
    private GameTimer timer;    //creating new timer 
    private boolean levelsClickedCheck; //remembers if the levels were clicked 
    private  boolean timerStarted = false; //remembers if timer has started counting 
   
    
    public void begin(){
        resize (370, 550);  //initial window size
        
        JPanel southPanel = new JPanel();
        add(southPanel, BorderLayout.SOUTH);   //adding south panel to canvas and in the south side
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS)); //orientation of the southpanel

        minesFound = new JLabel ("Mines found: " + count + "/10");
        southPanel.add(minesFound);                         
        minesFound.setAlignmentX(Component.CENTER_ALIGNMENT);

        newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        southPanel.add(newGameButton);
        newGameButton.addActionListener(this);

        displayGrid = new Grid(10, 10, canvas); //initial grid 
        canvas.addMouseListener(this);    //for mouse listener jobs to happen

        difficulty = new JComboBox();  
        difficulty.addItem("Easy"); //adding items or levels in the combobox
        difficulty.addItem("Medium");
        difficulty.addItem("Hard");
        southPanel.add(difficulty);
        difficulty.addActionListener(this);  //calling action listener on the combobox for it to actually do something when level is changed

        timer = new GameTimer(southPanel, canvas); //creating new timer in southPanel
         

    } 

    public void mousePressed(MouseEvent event){
        Location point = new Location (event.getX(), event.getY()); //convereting event to point value
        if(timerStarted == false){  //prevents timer from starting again (illegalthreadexception) after it has already started
               timer.start();  //starts timer when grid is clicked
               timerStarted = true; //remembers that timer has started
           }
        
        if (event.getButton() == MouseEvent.BUTTON3) { //   Do the following on Right Click 
            //gets the count(numbers of flagged cells) after right clicking             
            count = displayGrid.rightClick(point);
            //updating the mines found to the new count 
            minesFound.setText("Mines found: " + count + "/10");
            //minesFound = new JLabel ("Mines found: " + count + "/10"); 
            if(displayGrid.gameOverCheck()){  //stops timer when you lose or you win is true
                timer.stopTimer();
                
            }
        }   
        else {//Do the following on left click
            //calling leftClick method that will either turn cell white or do nothing depending on condition
            displayGrid.leftClick(point);
            if(displayGrid.gameOverCheck()){  //stops timer when user wins or loses 
                timer.stopTimer();
                
            }
           
        }

    }

    public void actionPerformed(ActionEvent event){    //for the newgamebutton
        //call reset method when negame button is pressed
        if(event.getSource() == newGameButton){
            reset();
        } 
        else if(event.getSource() == difficulty){ //if the combobox is clicked
            
           levelsClickedCheck = true;                       //indicates that and item in the difficulty box has been clicked
            if(difficulty.getSelectedItem() == "Easy"){ //do all the folowing if "Easy" is selected on the combobox
                timer.resetTimer();
                resize(370,550);                            //resizing canvas to fit the displayGrid
                canvas.clear();
                displayGrid = new Grid(10,10,canvas);
                lastDimension = 10;                     //remembering this as the last dimension so that reset button can change grid 
                                                        //accordingly

            }else if(difficulty.getSelectedItem() == "Medium"){
                timer.resetTimer();
                resize(450,600);                    
                canvas.clear(); 
                displayGrid = new Grid(12,12,canvas); 
                lastDimension = 12;

            }else if(difficulty.getSelectedItem() == "Hard"){
                timer.resetTimer();
                resize(550,700);
                canvas.clear();
                displayGrid = new Grid(15,15,canvas);
                lastDimension = 15;

            }
        }
    }

    public void reset(){ //resetting grid
        //reseting timer to zero
        timer.resetTimer();
        canvas.clear(); //cleare the canvas, set count to original count value and update the displayed mine count 
        count = 0;    //displaying count as zero when newgamebutton is clicked
        minesFound.setText("Mines found: "+ count + "/10");
        if(levelsClickedCheck){ //is the difficulty box is clicked and then the reset button is clicked, use the same dimensions as the last difficulty
                            //level clicked
            displayGrid = new Grid(lastDimension,lastDimension,canvas);
        } else{        //otherwise create the original grid dimension which is easy
            displayGrid = new Grid(10, 10, canvas);
        }
    }
   
    
    //methods not used, but stated for the mouseListener
    public void mouseClicked(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {
    }

}

