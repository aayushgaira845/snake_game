package snake_game1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class board extends JPanel implements ActionListener {

    Image apple;
    Image dot;
    Image head;
    int dots;
    final int dotsize = 10;
    final int[] x = new int[16000];
    final int[] y = new int[16000];
    private int apple_x;
    private int apple_y;
    boolean left = false;
    boolean right = true;
    boolean up = false;
    boolean down = false;
    boolean inGame = true;
    private Timer timer;

    board() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        intgame();
        loadimage();
    }

    public void loadimage() {
        ImageIcon a = new ImageIcon(ClassLoader.getSystemResource("snake_game1/icons/head.png"));
        ImageIcon b = new ImageIcon(ClassLoader.getSystemResource("snake_game1/icons/dot.png"));
        ImageIcon c = new ImageIcon(ClassLoader.getSystemResource("snake_game1/icons/apple.png"));
        head = a.getImage();
        dot = b.getImage();
        apple = c.getImage();
    }

    public void intgame() {
        dots = 3;
        for (int i = 0; i < dots; i++) 
        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        // Use panel size to calculate random positions
        int maxX = getWidth() / dotsize;
        int maxY = getHeight() / dotsize;

        if (maxX <= 0) maxX = 30; // fallback if panel not yet visible
        if (maxY <= 0) maxY = 30;

        int r1 = (int) (Math.random() * maxX);
        int r2 = (int) (Math.random() * maxY);
        apple_x = r1 * dotsize;
        apple_y = r2 * dotsize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            // Draw snake first
            for (int i = 0; i < dots; i++) {
                if (i == 0) g.drawImage(head, x[i], y[i], this);
                else g.drawImage(dot, x[i], y[i], this);
            }
            // Draw apple on top
            if (apple != null) g.drawImage(apple, apple_x, apple_y, this);
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameover(g);
        }
    }

    public void gameover(Graphics g) {
        String msg = "Game Over";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 24));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(msg, (getWidth() - metrics.stringWidth(msg)) / 2, getHeight() / 2);
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) x[0] -= dotsize;
        if (right) x[0] += dotsize;
        if (up) y[0] -= dotsize;
        if (down) y[0] += dotsize;
    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) inGame = false;
        }
        if (y[0] >= getHeight()) inGame = false;
        if (x[0] < 0) inGame = false;

        if (!inGame) timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true; up = false; down = false; right = false;
            }
            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true; up = false; down = false; left = false;
            }
            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true; left = false; right = false; down = false;
            }
            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true; left = false; right = false; up = false;
            }
        }
    }
}
