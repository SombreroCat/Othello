package othello;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
public class Othello extends JFrame implements Runnable {
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + 495;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + 525;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;

    final int numRows = 8;
    final int numColumns = 8;
    Piece board[][];
    boolean playerOnesTurn;
    boolean moveHappened;
    int currentRow;
    int currentColumn;
    int pastRow;
    int pastColumn;
    boolean purge;
    boolean gameover;
    Image background;
    Image gameboard;
    Image blackpiece;
    Image bluepiece;
    Image redpiece;
    boolean repaint;
    Image whitepiece;
    Image yellowsquare;
    enum WinState
    {
        None,PlayerOne,PlayerTwo,PlayerThree,PlayerFour,Tie
    }
    WinState winState;
    int winRow;
    int winColumn;
    enum WinDirection
    {
        Horizontal,Vertical,DiagonalUp,DiagonalDown
    }
    WinDirection winDirection;    
    int piecesOnBoard;
    int playerOneScore;
    int playerTwoScore;
    int playerThreeScore;
    int playerFourScore; 
    int ydelta;
    int xdelta;
    
    static Othello frame1;
    public static void main(String[] args) {
        frame1 = new Othello();
        frame1.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
    }

    public Othello() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    if (moveHappened || winState != WinState.None)
                        return;
                    int xpos = e.getX() - getX(0);
                    int ypos = e.getY() - getY(0);
                    if (xpos < 0 || ypos < 0 || xpos > getWidth2() || ypos > getHeight2())
                        return;
                    ydelta = getHeight2()/numRows;
                    xdelta = getWidth2()/numColumns;
                    currentColumn = xpos/xdelta;
                    currentRow = ypos/ydelta;
                    if(board[currentRow][currentColumn]!=null)
                    {
                        if(playerOnesTurn && (board[currentRow][currentColumn].getColor()== Color.black))
                        {
                            board[currentRow][currentColumn].setClickedOn(true);
                        }
                        else
                        {
                            board[currentRow][currentColumn].setClickedOn(false);
                        }
                        if(!playerOnesTurn && (board[currentRow][currentColumn].getColor()== Color.white))
                        {
                            board[currentRow][currentColumn].setClickedOn(true);
                        }
                        if(board[currentRow][currentColumn].getColor()== Color.yellow)
                        {
                            board[currentRow][currentColumn].setClickedOn(true);
                        }
                    }
//                    if(board[currentRow][currentColumn].getClickedOn())
//                    {
//                        
//                    }
                    
                }
                if (e.BUTTON3 == e.getButton()) {
                
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_ESCAPE == e.getKeyCode()) 
                {
                    
                }
                if (e.VK_R == e.getKeyCode())
                {
                    reset();
                }
                if (e.VK_E == e.getKeyCode())
                {
                    playerOnesTurn=!playerOnesTurn;
                }

                repaint();
            }
        });
        init();
        start();
    }




    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

//fill background
        g.drawImage(background,0,0,
                getWidth2()+50,getHeight2()+100,this);
//        if(playerOnesTurn)
//            g.setColor(Color.MAGENTA);
//        else
//            g.setColor(Color.BLACK);
//
//        g.fillRect(0, 0, xsize, ysize);
//
        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
//        g.setColor(Color.green);
//        g.fillPolygon(x, y, 4);
        g.drawImage(gameboard,getX(0),getY(0),getWidth2(),getHeight2(),this);
// draw border
        g.setColor(Color.BLACK);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        g.setColor(Color.BLACK);
//horizontal lines
        for (int zi=1;zi<numRows;zi++)
        {
            g.drawLine(getX(0) ,getY(0)+zi*getHeight2()/numRows ,
            getX(getWidth2()) ,getY(0)+zi*getHeight2()/numRows );
        }
//vertical lines
        for (int zi=1;zi<numColumns;zi++)
        {
            g.drawLine(getX(0)+zi*getWidth2()/numColumns ,getY(0) ,
            getX(0)+zi*getWidth2()/numColumns,getY(getHeight2())  );
        }
        for (int zrow=0;zrow<numRows;zrow++)
        {
            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
            {
                if (board[zrow][zcolumn] != null)
                {
                    g.drawImage(board[zrow][zcolumn].getPiece(),getX(0)+zcolumn*getWidth2()/numColumns,
                    getY(0)+zrow*getHeight2()/numRows,
                    getWidth2()/numColumns+1,
                    getHeight2()/numRows+1,this);
//                    g.fillOval(getX(0)+zcolumn*getWidth2()/numColumns,
//                    getY(0)+zrow*getHeight2()/numRows,
//                    getWidth2()/numColumns,
//                    getHeight2()/numRows);
                }
            }
        }
    
        if (winState == WinState.PlayerOne)
        {
            g.setColor(Color.gray);
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Player 1 has won.", 50, 200);            
        }
        else if (winState == WinState.PlayerTwo)
        {
            g.setColor(Color.gray);
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Player 2 has won.", 50, 200);            
        }
        if (winState == WinState.PlayerThree)
        {
            g.setColor(Color.gray);
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Player 3 has won.", 50, 200);            
        }
        else if (winState == WinState.PlayerFour)
        {
            g.setColor(Color.gray);
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Player 4 has won.", 50, 200);            
        }
        else if (winState == WinState.Tie)
        {
            g.setColor(Color.gray);
            g.setFont(new Font("Monospaced",Font.BOLD,40) );
            g.drawString("It is a tie.", 50, 200);            
        }
        
////        g.setColor(Color.WHITE);
////        g.setFont (new Font ("Monospaced", Font.PLAIN, 15));
////        g.drawString ("PlayerOne Score: " + playerOneScore, 30, 50);
////        g.setFont (new Font ("Monospaced", Font.PLAIN, 15));
////        g.drawString ("playerTwo Score: " + playerTwoScore, 350, 50);
////        
//
        g.setColor(Color.green);
        g.setFont (new Font ("Impact", Font.PLAIN, 15));
        g.drawString ("Black's score: " + Piece.blackScore, 30, 50);
        g.drawString ("White's score: " + Piece.whiteScore, 425, 50);
        
        g.drawString ("Red's score: " + Piece.redScore, 30, 590);
        g.drawString ("Blue's score: " + Piece.blueScore, 425, 590);
        g.drawString("Press Tab to Forfeit Turn", 200, 590);
        if(!playerOnesTurn)
        {
            g.drawString ("White's Turn", 240, 50);
        }
        else
            g.drawString ("Black's Turn", 240, 50);
        


        gOld.drawImage(image, 0, 0, null);
    }


////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.03;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset(){
        board = new Piece[numRows][numColumns];
        playerOnesTurn = true;
        moveHappened = false;
        winState = WinState.None;
        piecesOnBoard = 0;
        gameover=false;
        purge=false;
        repaint=false;
        board[4][4] = new Piece(Color.white);
        board[3][4] = new Piece(Color.black);
        board[3][3] = new Piece(Color.white);
        board[4][3] = new Piece(Color.black);

    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            background = Toolkit.getDefaultToolkit().getImage("./background.jpg");
            gameboard = Toolkit.getDefaultToolkit().getImage("./greenfelt.jpg");
            blackpiece = Toolkit.getDefaultToolkit().getImage("./blackpiece.GIF");
            yellowsquare = Toolkit.getDefaultToolkit().getImage("./Rectangle.PNG");
//            bluepiece;
//            redpiece;
//            whitepiece;
            reset();
        }     
        if(gameover)
            return;
        
        if (moveHappened)
        {
            moveHappened = false;
        }

//        if(repaint)
//        {
//            if(currentColumn==pastColumn)
//            {
//                int bop = currentRow-pastRow;
//                if(bop>0)
//                {
//                    for (int zrow=1;zrow<bop;zrow++)
//                    {
//                        board[currentRow+zrow][currentColumn].setColor(board[pastRow][pastColumn].getColor());
//                    }
//                }
//            }
//        }
        for (int zrow=0;zrow<numRows;zrow++)
                {
                    for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
                    {
                        if(board[zrow][zcolumn]!=null)
                        {
                            if(board[zrow][zcolumn].getColor()== Color.black&&!board[zrow][zcolumn].getClickedOn())
                            {
                                Piece.blackScore++;
                                board[zrow][zcolumn].setClickedOn(true);
                            }
                            if(board[zrow][zcolumn].getColor()== Color.white&&!board[zrow][zcolumn].getClickedOn())
                            {
                                Piece.whiteScore++;
                                board[zrow][zcolumn].setClickedOn(true);
                            } 
                            if(board[zrow][zcolumn].getColor()== Color.red&&!board[zrow][zcolumn].getClickedOn())
                            {
                                Piece.redScore++;
                                board[zrow][zcolumn].setClickedOn(true&&!board[zrow][zcolumn].getClickedOn());
                            }
                            if(board[zrow][zcolumn].getColor()== Color.blue)
                            {
                                Piece.blueScore++;
                                board[zrow][zcolumn].setClickedOn(true);
                            }
                        }
                    }
                }
        {
            int blackscore=0;
            int whitescore=0;
            int redscore=0;
            int bluescore=0;
            for (int zrow=0;zrow<numRows;zrow++)
                {
                    for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
                    {
                        if (board[zrow][zcolumn] != null)
                        {
                            if(board[zrow][zcolumn].getColor()== Color.BLACK)
                            {
                                blackscore++;
                            }
                            if(board[zrow][zcolumn].getColor()== Color.WHITE)
                            {
                                whitescore++;
                            }
                            if(board[zrow][zcolumn].getColor()== Color.RED)
                            {
                                redscore++;
                            }
                            if(board[zrow][zcolumn].getColor()== Color.BLUE)
                            {
                                bluescore++;

                            }
                        }
                    }
                }

            Piece.whiteScore=whitescore;
            Piece.blackScore=blackscore;
            Piece.redScore=redscore;
            Piece.blueScore=bluescore;
        }

        if(repaint)
        {
            if(currentColumn==pastColumn)
            {
                int bop = currentRow-pastRow;
                if(bop>0)
                {
                    for (int zrow=1;zrow<bop;zrow++)
                    {
                        board[pastRow+zrow][pastColumn].setColor(board[pastRow][pastColumn].getColor());
                    }
                }
                if(bop<0)
                    for (int zrow=-1;zrow>bop;zrow--)
                    {
                        board[pastRow+zrow][pastColumn].setColor(board[pastRow][pastColumn].getColor());
                    }
            }
            if(currentRow==pastRow)
            {
                int bop = currentColumn-pastColumn;
                if(bop>0)
                {
                    for (int zcol=1;zcol<bop;zcol++)
                    {
                        board[pastRow][pastColumn+zcol].setColor(board[pastRow][pastColumn].getColor());
                    }
                }
                if(bop<0)
                    for (int zcol=-1;zcol>bop;zcol--)
                    {
                        board[pastRow][pastColumn+zcol].setColor(board[pastRow][pastColumn].getColor());
                    }
            }
            repaint=false;
        }

        if(purge)
        {
            for (int zrow=0;zrow<numRows;zrow++)
                {
                    for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
                    {
                        if (board[zrow][zcolumn] != null)
                        {
                            if(board[zrow][zcolumn].getColor()==Color.yellow)
                                board[zrow][zcolumn]=null;
                        }
                    }
                }
            purge=false;
        }
        //REDO CODE
        if(board[currentRow][currentColumn]!=null)
        {
            if(board[currentRow][currentColumn].getClickedOn())
            {
                    if(board[currentRow][currentColumn].getColor()!=Color.YELLOW)
                    {
                        if(currentColumn+1<numColumns && currentColumn+2<numColumns 
                           && board[currentRow][currentColumn+1]!=null
                           && board[currentRow][currentColumn+1].getColor()!=board[currentRow][currentColumn].getColor() 
                           && board[currentRow][currentColumn+1].getColor()!=Color.YELLOW
                           && board[currentRow][currentColumn+2]==null)
                        {
                            board[currentRow][currentColumn+2]= new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                        if(currentRow-1>=0 && currentRow-2>=0 
                           && board[currentRow-1][currentColumn]!=null 
                           && board[currentRow-1][currentColumn].getColor()!=board[currentRow][currentColumn].getColor()
                           && board[currentRow-1][currentColumn].getColor()!=Color.YELLOW
                           && board[currentRow-2][currentColumn]==null)
                        {
                            board[currentRow-2][currentColumn]= new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                        if(currentRow+1<numRows && currentRow+2<numRows 
                           &&board[currentRow+1][currentColumn]!=null 
                           && board[currentRow+1][currentColumn].getColor()!=board[currentRow][currentColumn].getColor()
                           && board[currentRow+1][currentColumn].getColor()!=Color.YELLOW     
                           && board[currentRow+2][currentColumn]==null)
                        {
                            board[currentRow+2][currentColumn]= new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                        if(currentColumn-1>=0 && currentColumn-2>=0 
                           && board[currentRow][currentColumn-1]!=null 
                           && board[currentRow][currentColumn-1].getColor()!=board[currentRow][currentColumn].getColor() 
                           && board[currentRow][currentColumn-1].getColor()!=Color.YELLOW     
                           && board[currentRow][currentColumn-2]==null)
                        {
                            board[currentRow][currentColumn-2]= new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                    }
                    else
                    {
                        board[currentRow][currentColumn].setColor(board[pastRow][pastColumn].getColor());
                        playerOnesTurn= !playerOnesTurn;
                        board[currentRow][currentColumn].setClickedOn(false);
                        purge=true;
                        repaint=true;
<<<<<<< HEAD



                 }
=======
                    }
>>>>>>> origin/master
            }
        }
    }

 
    
////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER + WINDOW_BORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE );
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}
