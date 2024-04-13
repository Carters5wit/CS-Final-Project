package ludoGame;

import javax.swing.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;

import java.awt.*;
import java.awt.event.*;
/** 
 * This class is responsible to roll the dice and can roll it as many times as you want!
 * 
 * @author Ronny Ishimwe
 * @author Reggie Andrade
 */
import java.util.ArrayList;

/**
 * Responsible for rolling the dice
 * and getting the last roll and sends 
 * it to the main for pawn to move according to 
 * the roll.
 * 
 * @author Ronny Ishimwe
 */
public class Dice extends JPanel implements MouseListener {
    private int lastRoll;
    private boolean enabled;
    private Image[] diceImages;
    private boolean rolled;
    private JButton rollButton; // button that helps roll multiple times
    private BooleanProperty signal = new SimpleBooleanProperty(false);

    private ArrayList<Player> plrs;
    private Scene sc;
    private Pawn[][] pawns;
    private Board board;
    private int players;
    
    public Dice(ArrayList<Player> plrs, int players, Scene sc, Pawn[][] pawns, Board board) {
        this.plrs = plrs;
        this.sc = sc;
        this.pawns = pawns;
        this.board = board;
        this.players = players;
    	lastRoll = 1;
        enabled = true;
        rolled = false;

        // import all the dice images
        diceImages = new Image[6];
        for (int i = 0; i < 6; i++) {
            String imageName = "src/images/Dice" + (i + 1) + ".jpg";
            diceImages[i] = new ImageIcon(imageName).getImage();
        }

        // Set panel properties
        setPreferredSize(new Dimension(200, 150));
        setBackground(Color.WHITE);
        addMouseListener(this);

        // Create and configure the "click roll " button
        
        rollButton = new JButton("Click to roll");
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollMultipleTimes(5); 
            }
        });
        add(rollButton);
    }
    // This method is used to get the last roll of the dice 
    public int getRoll() {
        return lastRoll;
    }
    
    // Method to add a listener to the signal property
    public void addSignalListener(ChangeListener<Boolean> listener) {
        signal.addListener(listener);
    }

    // Method to remove a listener from the signal property
    public void removeSignalListener(ChangeListener<Boolean> listener) {
        signal.removeListener(listener);
    }
    
// This method is used to toggle with the rolling state of the dice.
    public void toggleRoll() {
        enabled = !enabled;
        rolled = true;
        repaint();
    }
 // This method is used to roll the dice multiple times with the delay between each roll.
    public void rollMultipleTimes(int numRolls) {
        if (enabled) {
        	disableRolling();// Disables rolling for the other play while the second player is still rolling
            for (int i = 0; i < numRolls; i++) {
                lastRoll = (int) (Math.random() * 6) + 1;
                rolled = true;
                repaint();
                try {
                    Thread.sleep(10); // delay between each roll
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            enableRolling();//re-enables the dice to roll again after all the player's rolls are completed
            Main.turn(plrs, players, this, sc, pawns, board, true);
        }
    }
    
 
// function to enable the dice to roll
 public void enableRolling() {
    	enabled = true;
        rolled = true;
        repaint();
	}
// function to disable the dice to roll
public void disableRolling() {
    	enabled = false;
        rolled = false;
        repaint();
		
	}


@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (rolled) {
        g.drawImage(diceImages[lastRoll - 1], 0, 0, getWidth(), getHeight(), this);
    } else {
        g.drawString("Click to Roll", 10, getHeight() / 2);
    }
}
	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on this component.
    * If rolling is enabled and the dice has not already been rolled, this method generates
    * a random roll for the dice, marks it as rolled, and repaints the panel to display the result.
	 */
	
    @Override
    public void mouseClicked(MouseEvent e) {
        if (enabled && !rolled) {
            lastRoll = (int) (Math.random() * 6) + 1;
            rolled = true;
            repaint();
            signal.set(true); // Fire the signal property when the dice is rolled
        }
    }
//
    //  MouseEvent methods
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
