package othello;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import othello.movement.PresentingMovement;
public class Othello extends JFrame implements Runnable {
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 30;
    static final int WINDOW_BORDER = 8;
    static final int WINDOW_WIDTH = 2*(WINDOW_BORDER + XBORDER) + 495;
    static final int WINDOW_HEIGHT = YTITLE + WINDOW_BORDER + 2 * YBORDER + 525;
    boolean animateFirstTime = true;
    static int xsize = -1;
    static int ysize = -1;
    static Image image;
    Graphics2D g;

    final public int numRows = 8;
    final public int numColumns = 8;
    public Piece board[][];
    public boolean playerOnesTurn;
    public int playersturn;
    public boolean moveHappened;
    public int currentRow;
    public int currentColumn;
    public int pastRow;
    public int pastColumn;
    int slide;
    boolean blueturn;
    public boolean purge;
    public static boolean gameover;
    public static boolean menu;
    public static boolean howToPlay;
    Image background;
    Image gameboard;
    Image blackpiece;
    Image bluepiece;
    Image redpiece;
    public boolean repaint;
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
                    if (moveHappened )
                        return;
                    int xpos = e.getX() - getX(0);
                    int ypos = e.getY() - getY(0);
                    if (xpos < 0 || ypos < 0 || xpos > getWidth2() || ypos > getHeight2())
                        return;
                    ydelta = getHeight2()/numRows;
                    xdelta = getWidth2()/numColumns;
                    currentColumn = xpos/xdelta;
                    currentRow = ypos/ydelta;
                    purge=true;
                    if(!menu)
                    {
                        if(board[currentRow][currentColumn]!=null)
                        {
                            if(board[currentRow][currentColumn].getColor()== Color.yellow)
                            {
                                board[currentRow][currentColumn].setClickedOn(true);
                                moveHappened=true;
                            }
                            if(playersturn==1 && Piece.blackstatis &&board[currentRow][currentColumn].getColor()== Color.black)
                            {
                                board[currentRow][currentColumn].setClickedOn(true);
                            }
                            else if(playersturn==2 && Piece.whitestatis && board[currentRow][currentColumn].getColor()== Color.white)
                            {
                                board[currentRow][currentColumn].setClickedOn(true);
                            }
                            else if(playersturn==3 && Piece.redstatis && board[currentRow][currentColumn].getColor()== Color.red)
                            {
                                board[currentRow][currentColumn].setClickedOn(true);
                            }
                            else if(playersturn==4 && Piece.bluestatis && board[currentRow][currentColumn].getColor()== Color.blue)
                            {
                                board[currentRow][currentColumn].setClickedOn(true);
                            }
                           
                        }
                        
                    }
                    if(e.getY()>=200&&e.getY()<=350&&e.getX()>=200&&e.getX()<=350&&menu)
                        menu=false;
                    if(e.getY()>=400&&e.getY()<=550&&e.getX()>=200&&e.getX()<=350&&menu)
                        howToPlay=true;
                    
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
        if(e.getY()>=310&&e.getY()<=350&&e.getX()>=210&&e.getX()<=320)
                 GUI.Menu.playHover=true;
          else 
                 GUI.Menu.playHover=false;
          if(e.getY()>=450&&e.getY()<=500&&e.getX()>=150&&e.getX()<=400)
                 GUI.Menu.howToPlayHover=true;
          else
                 GUI.Menu.howToPlayHover=false;
          repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_ESCAPE == e.getKeyCode()) 
                {
                    reset();
                }
                if (e.VK_R == e.getKeyCode())
                {
                }
                if (e.VK_E == e.getKeyCode())
                {
                    if(playersturn<4)
                        playersturn++;
                    else
                        playersturn=1;
                    purge=true;
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


// draw border

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        g.setColor(Color.BLACK);
//horizontal lines
        if(!menu)
        {   
                g.drawImage(gameboard,slide-3,getY(0),getWidth2(),getHeight2(),this);
                g.setColor(Color.BLACK);
                g.drawPolyline(x, y, 5);
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
                        g.drawImage(board[zrow][zcolumn].getPiece(),slide+zcolumn*getWidth2()/numColumns-4,
                        getY(0)+zrow*getHeight2()/numRows,
                        getWidth2()/numColumns+1,
                        getHeight2()/numRows+1,this);
                    }
                }
            }
            g.setColor(Color.green);
            g.setFont (new Font ("Impact", Font.PLAIN, 15));
        }
        GUI.Menu.paint(g);
        if(!menu && !howToPlay)
        {
            g.drawString ("Black's score: " + Piece.blackScore, getX(2), getY(0));
            g.drawString ("White's score: " + Piece.whiteScore, getWidth()-115, 50);
            g.drawString ("Red's score: " + Piece.redScore, getX(2), getHeight()-13);
            g.drawString ("Blue's score: " + Piece.blueScore, getWidth()-110, getHeight()-13);
            g.drawString("Press E to Forfeit Turn",  getWidth()/2-65, getHeight()-13);
            if(playersturn==1)
                g.drawString ("Black's Turn", getWidth()/2-50, 50);
            if(playersturn==2)
                g.drawString ("White's Turn", getWidth()/2-50, 50);
            if(playersturn==3)
                g.drawString ("Red's Turn", getWidth()/2-50, 50);
            if(playersturn==4)
                g.drawString ("Blue's Turn", getWidth()/2-50, 50);
        
        }
        g.setColor(Color.green);
        if (winState == WinState.PlayerOne)
        {
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Black Wins", 170, 200);            
        }
        else if (winState == WinState.PlayerTwo)
        {
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("White Wins", 170, 200);            
        }
        if (winState == WinState.PlayerThree)
        {
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Blue Wins", 190, 200);            
        }
        else if (winState == WinState.PlayerFour)
        {
            g.setFont(new Font("Impact",Font.BOLD,40) );
            g.drawString("Red Wins", 190, 200);            
        }
        else if (winState == WinState.Tie)
        {
            g.setFont(new Font("Monospaced",Font.BOLD,40) );
            g.drawString("Tied Game", 170, 200);            
        }
    
       gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.01;    //time that 1 frame takes.
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
        menu=true;
        howToPlay=false;
        slide=800;
        board[5][1] = new Piece(Color.white);
        board[5][2] = new Piece(Color.blue);
        board[6][1] = new Piece(Color.BLACK);
        board[6][2] = new Piece(Color.red);
        Piece.blackstatis=true;
        Piece.whitestatis=true;
        Piece.redstatis=true;
        Piece.bluestatis=true;
        board[1][1] = new Piece(Color.white);
        board[1][2] = new Piece(Color.blue);
        board[2][1] = new Piece(Color.BLACK);
        board[2][2] = new Piece(Color.red);
        
        board[1][5] = new Piece(Color.white);
        board[1][6] = new Piece(Color.blue);
        board[2][5] = new Piece(Color.BLACK);
        board[2][6] = new Piece(Color.red);
        
        board[5][5] = new Piece(Color.white);
        board[5][6] = new Piece(Color.blue);
        board[6][5] = new Piece(Color.BLACK);
        board[6][6] = new Piece(Color.red);
        blueturn=false;
        playersturn=1;

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
            for (int zrow=0;zrow<numRows;zrow++)
                        {
                            for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
                            {
                                if(board[zrow][zcolumn]!=null)
                                board[zrow][zcolumn].setClickedOn(false);
                            }
                        }
        }
        
        if(gameover)
            return;
        if(slide>getX(7)&&!menu && !howToPlay)
            slide-=8;
        //REDO CODE
//                if(board[currentRow][currentColumn]!=null)
//        {
//            othello.movement.PresentingMovement.ShowCheck();
//        }
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
                        if(currentColumn+1<numColumns && currentColumn+2<numColumns && currentRow-1>=0 && currentRow-2>=0
                           && board[currentRow-1][currentColumn+1]!=null
                           && board[currentRow-1][currentColumn+1].getColor()!=board[currentRow][currentColumn].getColor()
                           && board[currentRow-1][currentColumn+1].getColor()!=Color.YELLOW 
                           && board[currentRow-2][currentColumn+2]==null
                                )
                        {
                            board[currentRow-2][currentColumn+2] = new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                        if(currentRow+1<numRows && currentRow+2<numRows && currentColumn-1>=0 && currentColumn-2>=0
                           && board[currentRow+1][currentColumn-1]!=null
                           && board[currentRow+1][currentColumn-1].getColor()!=board[currentRow][currentColumn].getColor()
                           && board[currentRow+1][currentColumn-1].getColor()!=Color.YELLOW 
                           && board[currentRow+2][currentColumn-2]==null
                                )
                        {
                            board[currentRow+2][currentColumn-2] = new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                        if(currentColumn-1>0-1 && currentColumn-2>0-1 && currentRow-1>0-1 && currentRow-2>0-1
                           && board[currentRow-1][currentColumn-1]!=null
                           && board[currentRow-1][currentColumn-1].getColor()!=board[currentRow][currentColumn].getColor()
                           && board[currentRow-1][currentColumn-1].getColor()!=Color.YELLOW 
                           && board[currentRow-2][currentColumn-2]==null     
                                )
                        {
                            board[currentRow-2][currentColumn-2] = new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                        if(currentColumn+1<numColumns && currentColumn+2<numColumns && currentRow+1<numRows && currentRow+2<numRows
                           && board[currentRow+1][currentColumn+1]!=null
                           && board[currentRow+1][currentColumn+1].getColor()!=board[currentRow][currentColumn].getColor()
                           && board[currentRow+1][currentColumn+1].getColor()!=Color.YELLOW 
                           && board[currentRow+2][currentColumn+2]==null     
                                )
                        {
                            board[currentRow+2][currentColumn+2] = new Piece(Color.yellow);
                            pastRow=currentRow;
                            pastColumn=currentColumn;
                        }
                    }
                    else
                    {
                        board[currentRow][currentColumn].setColor(board[pastRow][pastColumn].getColor());
                        if(playersturn<4)
                            playersturn++;
                        else
                            playersturn=1;
                        purge=true;
                        repaint=true;
                 }
               }
            }
            
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
        if(blackscore==0)
        {
            Piece.blackstatis=false;
        }
        if(whitescore==0)
        {
            Piece.whitestatis=false;
        }
        if(bluescore==0)
        {
            Piece.bluestatis=false;
        }
        if(redscore==0)
        {
            Piece.redstatis=false;
        }
        if(playersturn==1 && !Piece.blackstatis)
            playersturn++;
        if(playersturn==2 && !Piece.whitestatis)
            playersturn++;
        if(playersturn==3 && !Piece.redstatis)
            playersturn++;
        if(playersturn==4 && !Piece.bluestatis)
            playersturn=1;
        if(repaint)
        {
            if(currentColumn==pastColumn)
            {
                int bop = currentRow-pastRow;
                if(bop>0)
                {
                    for (int zrow=1;zrow<bop;zrow++)
                        board[pastRow+zrow][pastColumn].setColor(board[pastRow][pastColumn].getColor());
                }
                if(bop<0)
                    for (int zrow=-1;zrow>bop;zrow--)
                        board[pastRow+zrow][pastColumn].setColor(board[pastRow][pastColumn].getColor());
            }
            if(currentRow==pastRow)
            {
                int bop = currentColumn-pastColumn;
                if(bop>0)
                {
                    for (int zcol=1;zcol<bop;zcol++)
                        board[pastRow][pastColumn+zcol].setColor(board[pastRow][pastColumn].getColor());
                }
                if(bop<0)
                    for (int zcol=-1;zcol>bop;zcol--)
                        board[pastRow][pastColumn+zcol].setColor(board[pastRow][pastColumn].getColor());
            }
            if(currentRow==pastRow-2 && currentColumn==pastColumn+2)
            {
               for(int diff=1; diff<2;diff++)
                   board[pastRow-diff][pastColumn+diff].setColor(board[pastRow][pastColumn].getColor());
            }
            if(currentRow==pastRow+2 && currentColumn==pastColumn-2)
            {
               for(int diff=1; diff<2;diff++)
                   board[pastRow+diff][pastColumn-diff].setColor(board[pastRow][pastColumn].getColor());
            }
            if(currentRow==pastRow-2 && currentColumn==pastColumn-2)
            {
               for(int diff=1; diff<2;diff++)
                   board[pastRow-diff][pastColumn-diff].setColor(board[pastRow][pastColumn].getColor());
            }
            if(currentRow==pastRow+2 && currentColumn==pastColumn+2)
            {
               for(int diff=1; diff<2;diff++)
                   board[pastRow+diff][pastColumn+diff].setColor(board[pastRow][pastColumn].getColor());
            }
            repaint=false;
        }

        
        if (moveHappened)
        {
            CheckWin();
            moveHappened = false;
        }
        }

////////////////////////////////////////////////////////////////////////////
    public boolean CheckWin()    {
    
        int totalspaces=0;
        boolean gamewon=false;
        boolean checkmoves=true;
        for (int zrow=0;zrow<numRows;zrow++)
                {
                    for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
                    {
                        if(board[zrow][zcolumn]!=null)
                        {
                            totalspaces++;
                        }
                    }
                }
        //work
        for (int zrow=0;zrow<numRows;zrow++)
                {
                    for (int zcolumn=0;zcolumn<numColumns;zcolumn++)
                    {
                        if(board[zrow][zcolumn]!=null)
                        {
                            if(board[zrow][zcolumn].getColor()!=Color.YELLOW)
                            {
                                boolean exists = true;
                                if(board[zrow][zcolumn].getColor()==Color.BLACK && !Piece.blackstatis)
                                {
                                    exists=false;
                                }
                                if(board[zrow][zcolumn].getColor()==Color.WHITE && !Piece.whitestatis)
                                {
                                    exists=false;
                                }
                                if(board[zrow][zcolumn].getColor()==Color.RED && !Piece.redstatis)
                                {
                                    exists=false;
                                }
                                if(board[zrow][zcolumn].getColor()==Color.BLUE && !Piece.bluestatis)
                                {
                                    exists=false;
                                }
                                    if(exists)
                                    {
                                        if(zcolumn+1<numColumns && zcolumn+2<numColumns 
                                           && board[zrow][zcolumn+1]!=null
                                           && board[zrow][zcolumn+1].getColor()!=board[zrow][zcolumn].getColor() 
                                           && board[zrow][zcolumn+1].getColor()!=Color.YELLOW
                                           && board[zrow][zcolumn+2]==null)
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zrow-1>=0 && zrow-2>=0 
                                           && board[zrow-1][zcolumn]!=null 
                                           && board[zrow-1][zcolumn].getColor()!=board[zrow][zcolumn].getColor()
                                           && board[zrow-1][zcolumn].getColor()!=Color.YELLOW
                                           && board[zrow-2][zcolumn]==null)
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zrow+1<numRows && zrow+2<numRows 
                                           &&board[zrow+1][zcolumn]!=null 
                                           && board[zrow+1][zcolumn].getColor()!=board[zrow][zcolumn].getColor()
                                           && board[zrow+1][zcolumn].getColor()!=Color.YELLOW     
                                           && board[zrow+2][zcolumn]==null)
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zcolumn-1>=0 && zcolumn-2>=0 
                                           && board[zrow][zcolumn-1]!=null 
                                           && board[zrow][zcolumn-1].getColor()!=board[zrow][zcolumn].getColor() 
                                           && board[zrow][zcolumn-1].getColor()!=Color.YELLOW     
                                           && board[zrow][zcolumn-2]==null)
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zcolumn+1<numColumns && zcolumn+2<numColumns && zrow-1>=0 && zrow-2>=0
                                           && board[zrow-1][zcolumn+1]!=null
                                           && board[zrow-1][zcolumn+1].getColor()!=board[zrow][zcolumn].getColor()
                                           && board[zrow-1][zcolumn+1].getColor()!=Color.YELLOW 
                                           && board[zrow-2][zcolumn+2]==null
                                                )
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zrow+1<numRows && zrow+2<numRows && zcolumn-1>=0 && zcolumn-2>=0
                                           && board[zrow+1][zcolumn-1]!=null
                                           && board[zrow+1][zcolumn-1].getColor()!=board[zrow][zcolumn].getColor()
                                           && board[zrow+1][zcolumn-1].getColor()!=Color.YELLOW 
                                           && board[zrow+2][zcolumn-2]==null
                                                )
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zcolumn-1>0-1 && zcolumn-2>0-1 && zrow-1>0-1 && zrow-2>0-1
                                           && board[zrow-1][zcolumn-1]!=null
                                           && board[zrow-1][zcolumn-1].getColor()!=board[zrow][zcolumn].getColor()
                                           && board[zrow-1][zcolumn-1].getColor()!=Color.YELLOW 
                                           && board[zrow-2][zcolumn-2]==null     
                                                )
                                        {
                                            checkmoves=false;
                                        }
                                        else if(zcolumn+1<numColumns && zcolumn+2<numColumns && zrow+1<numRows && zrow+2<numRows
                                           && board[zrow+1][zcolumn+1]!=null
                                           && board[zrow+1][zcolumn+1].getColor()!=board[zrow][zcolumn].getColor()
                                           && board[zrow+1][zcolumn+1].getColor()!=Color.YELLOW 
                                           && board[zrow+2][zcolumn+2]==null     
                                                )
                                        {
                                            checkmoves=false;
                                        }
                                        if(checkmoves)
                                            gamewon=true;
                                        else if (!checkmoves)
                                            gamewon=false;
                                    }
                            }
                        }
                    }
                }
        if(totalspaces==64)
                {
                    gamewon=true;
                }
        if(gamewon)
        {
            if(Piece.blackScore>Piece.whiteScore && Piece.blackScore>Piece.blueScore && Piece.blackScore>Piece.redScore)
            {
                winState=WinState.PlayerOne;
            }
            else if(Piece.whiteScore>Piece.blackScore && Piece.whiteScore>Piece.blueScore && Piece.whiteScore>Piece.redScore)
            {
                winState=WinState.PlayerTwo;
            }
            else if(Piece.blueScore>Piece.blackScore && Piece.blueScore>Piece.whiteScore && Piece.blueScore>Piece.redScore)
            {
                winState=WinState.PlayerThree;
            }
            else if(Piece.redScore>Piece.blackScore && Piece.redScore>Piece.whiteScore && Piece.redScore>Piece.blueScore)
            {
                winState=WinState.PlayerFour;
            }
            else
            {
                winState=WinState.Tie;
            }
            return(true); 
        }
        return(false);
    
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
    
    public static int getWidth2() {
        return (xsize - 2 * (XBORDER + WINDOW_BORDER));
    }

    public static int getHeight2() {
        return (ysize - 2 * YBORDER - WINDOW_BORDER - YTITLE);
    }
}