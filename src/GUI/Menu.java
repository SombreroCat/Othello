package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Menu {
    public static boolean playHover;
    public static boolean howToPlayHover;
    public static int playXPos;
    public static int playYPos;
    public static int howToPlayXpos;
    public static int howToPlayYpos;
    
     public static void paint(Graphics2D g)
     {
         if(othello.Othello.menu&&!othello.Othello.howToPlay)
        {
             g.setColor(Color.green);
             g.setFont(new Font("Impact",Font.BOLD,50));
             g.drawString("Othello", 190, 120);
             if(playHover)
             {
                 g.setColor(Color.yellow);
                 g.setFont(new Font("Impact",Font.BOLD,60));
                 playXPos=-9;
                 playYPos=4;
             }
             g.drawString("Play", 220+playXPos, 350+playYPos);
             g.setColor(Color.green);
             g.setFont(new Font("Impact",Font.BOLD,50));
             playXPos=0;
             playYPos=0;
             
             if(howToPlayHover)
             {
                 g.setFont(new Font("Impact",Font.BOLD,60));
                 g.setColor(Color.yellow);
                 howToPlayXpos=-25;
                 howToPlayYpos=4;
             }
             g.drawString("How to Play", 145+howToPlayXpos, 500+howToPlayYpos);
             howToPlayXpos=0;
             howToPlayYpos=0;
             g.setFont(new Font("Impact",Font.BOLD,50));
             
        }
        if(othello.Othello.howToPlay&&othello.Othello.menu)
        {
             g.setColor(Color.green);
             g.setFont(new Font("Impact",Font.BOLD,18));
             g.drawString("Players can only move if they jump over an enemy player's piece.", 10, 50);
             g.drawString("Clicking on a piece will show all possible moves for the piece.", 10, 100);
             g.drawString("The game ends when there are no more possible moves.", 10, 150);
             g.drawString("The player with the most pieces by the end of the game wins.", 10, 200);
             g.drawString("If a player cannot make a move, but other players can,",10,250) ;
             g.drawString("that player must forfeit thier move by pressing E .", 10, 270);
             g.drawString("Think wisely before moving, because you cannot undo your move. ", 10, 320);
        }
     }
}
