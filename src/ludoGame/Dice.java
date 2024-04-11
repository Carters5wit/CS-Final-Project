package ludoGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/** 
 * This class is responsible to roll the dice and can roll it as many times as you want!
 * 
 * @author Ronny Ishimwe
 */

public class Dice extends JPanel implements MouseListener {
    private int lastRoll;
    private boolean enabled;
    private Image[] diceImages;
    private boolean rolled;
    private JButton rollButton; // button that helps roll multiple times

    public Dice() {
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
        }
    }
    
 
// function to enable the dice to roll
 private void enableRolling() {
    	enabled = true;
        rolled = true;
        repaint();
	}
// function to disable the dice to roll
private void disableRolling() {
    	enabled = false;
        rolled = false;
        repaint();
		
	}


	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (enabled) {
            if (rolled) {
                g.drawImage(diceImages[lastRoll - 1], 0, 0, getWidth(), getHeight(), this);
            } else {
                g.drawString("Click to Roll", 10000, getHeight() / 2);
            }
        } else {
            g.drawString("Disabled", 10, getHeight() / 2);
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
// This is the main method to run the dice panel
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dice");
        Dice dice = new Dice();
        frame.add(dice);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
