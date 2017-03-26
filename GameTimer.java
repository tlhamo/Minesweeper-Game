import objectdraw.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Creates timer using activeobject
 * 
 * @author Tseki Lhamo
 * @version 7th December 2016
 */

   public class GameTimer extends ActiveObject{
      private JLabel timer;  //creating a timer with a jlabel to put into the south panel
      private int time = 0;  //time is zero at first   
      private boolean timerRun = true;   //remembers if the timer started
   
      
    public GameTimer(JPanel panel, DrawingCanvas canvas){
           
           timer = new JLabel("Time:" + time + "");  //the initial loop of the timer
           panel.add(timer);  //adding timer to panel that was indicated through parameter
           
     }
        
        public void run(){            
          while(timerRun){    //the time will only run if isn't stopped
           pause(1000);         //pause is 1 second
           time++;              //increment time after the pause
           timer.setText("Time taken:" + time);   //change the time to the new time
        }
    }
        
    public void stopTimer(){  //when timer is stoped like on game over,, the timer will stop so loop in run won't run
        timerRun = false;                   
    }
    
    //resetting the timer 
    public void resetTimer(){
       time = 0;   
       timerRun = true;   //timer is true so loop in the run will work 
    }
    
    
    }


    



