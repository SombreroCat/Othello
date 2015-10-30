
package othello;

import java.awt.*;
public class Piece {
    private Color color;
    private boolean ClickedOn;
    Piece(Color _color)
    {
        color = _color;
        ClickedOn=false;
    }
    Color getColor()
    {
        return (color);
    }
    void setColor(Color _color)
    {
        color = _color;
    }
    boolean getClickedOn()
    {
        return(ClickedOn);
    }
    void setClickedOn(boolean _clickedon)
    {
        ClickedOn=_clickedon;
    }
    
    Image getPiece()
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
