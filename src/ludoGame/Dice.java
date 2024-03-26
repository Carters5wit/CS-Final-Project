package ludoGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dice extends JPanel implements MouseListener {
    private int lastRoll;
    private boolean enabled;
    private Image[] diceImages;
    private boolean rolled;

    public Dice() {
        lastRoll = 1;
        enabled = true;
        rolled = false;

        // Load dice images
        diceImages = new Image[6];
        for (int i = 0; i < 6; i++) {
            String imageName = "All in C:\\Users\\ironn\\Desktop\\comp science 2\\Comp-Science\\Computer science-project"+ (i + 1) + ".png";
            diceImages[i] = new ImageIcon(imageName).getImage();
      
        }

        // Set panel properties
        setPreferredSize(new Dimension(100, 100));
        setBackground(Color.WHITE);
        addMouseListener(this);
    }

    public int getRoll() {
        return lastRoll;
    }

    public void toggleRoll() {
        enabled = !enabled;
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
                g.drawString("Click to Roll", 10, getHeight() / 2);
            }
        } else {
            g.drawString("Disabled", 10, getHeight() / 2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (enabled && !rolled) {
            lastRoll = (int) (Math.random() * 6) + 1;
            rolled = true;
            repaint();
        }
    }

    // Unused MouseEvent methods
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

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
