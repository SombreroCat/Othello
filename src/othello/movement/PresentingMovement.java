/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package othello.movement;
import java.awt.Color;
import othello.Othello;
import othello.Piece;
public class PresentingMovement extends Othello{
    public void ShowCheck()
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
                        board[currentRow][currentColumn].setClickedOn(false);
                        purge=true;
                        repaint=true;
                 }
               }
    }
}
