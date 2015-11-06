
package othello;

import java.awt.*;
public class Piece {
    private Color color;
    private boolean ClickedOn;
    private boolean counted;
    static int blackScore;
    static int whiteScore;
    static int redScore;
    static int blueScore;
    private int colorscore;
    
    public Piece(Color _color)
    {
        color = _color;
        ClickedOn=false;
        if(color==Color.BLUE)
            colorscore=4;
        if(color==Color.WHITE)
            colorscore=2;
        if(color==Color.RED)
            colorscore=3;
        if(color==Color.BLACK)
            colorscore=1;
        if(color==Color.YELLOW)
            colorscore=5;
    }
    public Color getColor()
    {
        return (color);
    }
    public int getColorScore()
    {
        return (colorscore);
    }
    public void setColor(Color _color)
    {
        color = _color;
    }
    public boolean getClickedOn()
    {
        return(ClickedOn);
    }
    public void setClickedOn(boolean _clickedon)
    {
        ClickedOn=_clickedon;
    }
    public void setCounted(boolean _counted)
    {
        counted=_counted;
    }
    public boolean getCounted()
    {
        return(counted);
    }
    
    public Image getPiece()
    {
        if(color == Color.BLUE)
            return(Toolkit.getDefaultToolkit().getImage("./bluepiece.GIF"));
        if(color == Color.RED)
            return(Toolkit.getDefaultToolkit().getImage("./redpiece.GIF"));
        if(color == Color.BLACK)
            return(Toolkit.getDefaultToolkit().getImage("./blackpiece.GIF"));
        if(color == Color.WHITE)
            return(Toolkit.getDefaultToolkit().getImage("./whitepiece.GIF"));
        if(color == Color.yellow)
            return(Toolkit.getDefaultToolkit().getImage("./Rectangle.PNG"));
        return(null);
    }
    
}
