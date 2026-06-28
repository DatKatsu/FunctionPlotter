package com.datkatsu.functionplotter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class CoordinateSystem
{
    private CoordinateInfo coordinateInfo;
    
    private BufferedImage bufferedImage;
    private Graphics2D g2dBuffer;
    
    public CoordinateSystem (CoordinateInfo coordinateInfo)
    {
        this.coordinateInfo = coordinateInfo;
        //bufferedImage = new BufferedImage (coordinateInfo.getWidth (), coordinateInfo.getHeight (), BufferedImage.TYPE_INT_ARGB);
        
        drawCoordinates ();
    }
    
    private void drawCoordinates()
    {
        bufferedImage = new BufferedImage (coordinateInfo.getWidth (), coordinateInfo.getHeight (), BufferedImage.TYPE_INT_ARGB);
        g2dBuffer = bufferedImage.createGraphics ();
        g2dBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color color = new Color(0, 0,0, 0.5f);
        g2dBuffer.setStroke(new BasicStroke (2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2dBuffer.setPaint (color);
        g2dBuffer.translate (coordinateInfo.getOriginX (), Math.abs(coordinateInfo.yMax * coordinateInfo.getyStep ()));
        g2dBuffer.scale (1, -1);
        
        g2dBuffer.draw (new Line2D.Double (coordinateInfo.xMin * coordinateInfo.getxStep (), 0, coordinateInfo.xMax * coordinateInfo.getxStep (), 0));
        g2dBuffer.draw (new Line2D.Double (0, coordinateInfo.yMin * coordinateInfo.getyStep (), 0, coordinateInfo.yMax * coordinateInfo.getyStep ()));
    
        g2dBuffer.setStroke(new BasicStroke (1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        color = new Color (0, 0, 0, 0.2f);
        g2dBuffer.setPaint (color);
        BufferedImage textImage = new BufferedImage (coordinateInfo.getWidth (), coordinateInfo.getHeight (), BufferedImage.TYPE_INT_ARGB);
        Graphics2D textG = textImage.createGraphics ();
        textG.setPaint (Color.black);
        textG.translate (coordinateInfo.getOriginX (), Math.abs(coordinateInfo.yMax * coordinateInfo.getyStep ()));
        textG.scale (1 * coordinateInfo.zoom, -1 * coordinateInfo.zoom);
        
        for(double i = coordinateInfo.xMin ; i <= coordinateInfo.xMax; i += coordinateInfo.xLineDist)
        {
            g2dBuffer.draw (new Line2D.Double (i * coordinateInfo.getxStep (), coordinateInfo.yMin * coordinateInfo.getyStep (), i * coordinateInfo.getxStep (), coordinateInfo.yMax * coordinateInfo.getyStep ()));
            textG.drawString (Math.round (i * 100)/100.0+"", (int)(i * coordinateInfo.getxStep ()), 0);
        }
    
        for(double i = coordinateInfo.yMin; i <= coordinateInfo.yMax; i += coordinateInfo.yLineDist)
        {
            g2dBuffer.draw (new Line2D.Double (coordinateInfo.xMin * coordinateInfo.getxStep (), i * coordinateInfo.getyStep(), coordinateInfo.xMax * coordinateInfo.getxStep (), i* coordinateInfo.getyStep()));
            textG.drawString (Math.round(i * 100)/100.0*(-1)+"", 0, (int)(i * coordinateInfo.getyStep ()));
        }
        
        g2dBuffer.drawImage (textImage, (int)(-coordinateInfo.getOriginX () + 5), (int)(-coordinateInfo.yMax * coordinateInfo.getyStep () - 15), null);
        g2dBuffer.dispose ();
    }
    
    public void update()
    {
        drawCoordinates ();
    }
    
    public BufferedImage getBufferedImage ()
    {
        //drawCoordinates ();
        return bufferedImage;
    }
}
