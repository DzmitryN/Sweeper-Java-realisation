import sweeper.*;

import javax.swing.*;
import sweeper.Box;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JavaSweeper extends JFrame{

    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROW = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) {

        new JavaSweeper();
    }

    private JavaSweeper() throws HeadlessException {
        game = new Game(COLS, ROW, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    //create label
    private void initLabel(){
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
    }

        //create panel
    private void initPanel(){
        panel = new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


                for(Coord coord : Ranges.getAllCoords()){

                    g.drawImage((Image)game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
                }

            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                if(e.getButton() ==MouseEvent.BUTTON1) {
                    game.pressLeftButton(new Coord(x, y));
                }
                  else  if(e.getButton() == MouseEvent.BUTTON3){
                        game.pressRightButton(new Coord(x, y));
                }
                else  if(e.getButton() == MouseEvent.BUTTON2){
                    game.start();
                }
                label.setText(getMessage());
                panel.repaint();
            }
        });



        panel.setPreferredSize(new Dimension(COLS * IMAGE_SIZE, ROW * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {

        switch (game.getState()){
            case PLAYED: return  "Be Smart!";
            case BOMBED: return "Sorry, you lose.";
            case WINNER: return "Congratulations, you won!";
            default: return "Welcome!";
        }
    }

    //create frame
    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Sweeper");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();
        setLocationRelativeTo(null);

    }

    //call images
    private void setImages(){
        for( Box box : Box.values()){
            box.image = getImage(box.name().toLowerCase());
        }
    }

        //create image
    private Image getImage(String name){
        String fileName = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }
}
