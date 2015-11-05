
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
    
    public Piece(Color _color)
    {
        color = _color;
        ClickedOn=false;
    }
    public Color getColor()
    {
        return (color);
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
