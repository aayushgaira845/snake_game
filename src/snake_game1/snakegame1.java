package snake_game1;

import javax.swing.*;




    public class snakegame1 extends JFrame {
        snakegame1(){
            super("snake game");
            board bj=new board();
           add(bj);
            setSize(400,400);
            setLocationRelativeTo(null);
            setVisible(true);


        }
        public static void main(String[] args) {
            snakegame1 obj=new snakegame1();

        }

    }

