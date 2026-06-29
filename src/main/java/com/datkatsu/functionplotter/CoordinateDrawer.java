package com.datkatsu.functionplotter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class CoordinateDrawer
{
    private CoordinateInfo coordinateInfo;
    
    private BufferedImage bufferedImage;
    private Graphics2D g2dBuffer;

    private int width = 3200;
    private int height = 3200;

    private double pixelPerX = 40;
    private double pixelPerY = 40;

    private int xOrigin;
    private int yOrigin;

    public CoordinateDrawer(CoordinateInfo coordinateInfo, int width, int height)
    {
        this.coordinateInfo = coordinateInfo;
        xOrigin = width / 2;
        yOrigin = height / 2;
        drawCoordinates ();
    }
    
    private void drawCoordinates()
    {
        bufferedImage = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);
        g2dBuffer = bufferedImage.createGraphics ();
        g2dBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = new Color(0, 0,0, 0.5f);
        g2dBuffer.setStroke(new BasicStroke (2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2dBuffer.setPaint (color);

        //draw main coordinate lines
        g2dBuffer.draw (new Line2D.Double (xOrigin, 0, xOrigin, height));
        g2dBuffer.draw (new Line2D.Double (0, yOrigin, width, yOrigin));

        // sets origin of the drawing coordinate system to the center of the image;
        g2dBuffer.translate (xOrigin, yOrigin);
        // inverts the scale in the y-axis so the y-axis goes from bottom to top as is conventional instead of top to bottom
        g2dBuffer.scale (1, -1);


        g2dBuffer.setStroke(new BasicStroke (1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        color = new Color (0, 0, 0, 0.2f);
        g2dBuffer.setPaint (color);

        BufferedImage textImage = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D textG2d = textImage.createGraphics ();
        textG2d.setPaint (Color.black);
        textG2d.translate (xOrigin, yOrigin);
        textG2d.scale (1, -1);

        int count = ( int ) (( coordinateInfo.xMax() - coordinateInfo.xMin() ) / coordinateInfo.xStep());
        for(int i = 0; i < count; i++)
        {
            Line2D.Double line = new Line2D.Double(
                    (coordinateInfo.xMin() + i) * pixelPerX,
                    -yOrigin,
                    (coordinateInfo.xMin() + i) * pixelPerX,
                    height
            );
            g2dBuffer.draw(line);
            String numbering = coordinateInfo.xMin() + i * coordinateInfo.xStep() + " ";
            textG2d.drawString (numbering, (float)((coordinateInfo.xMin() + i) * pixelPerX), 0);
        }

        for(int i = 0; i < count; i++)
        {
            Line2D.Double line = new Line2D.Double(
                    -xOrigin,
                    (coordinateInfo.yMin() + i) * pixelPerY,
                    width,
                    (coordinateInfo.yMin() + i) * pixelPerY
            );
            g2dBuffer.draw(line);
            String numbering = coordinateInfo.yMax() - i * coordinateInfo.yStep() + " ";
            textG2d.drawString (numbering, 0, (float)((coordinateInfo.yMin() + i) * pixelPerY));
        }
        g2dBuffer.drawImage (textImage, -xOrigin + 5, -yOrigin + 5, null);
        g2dBuffer.dispose ();
    }

    public BufferedImage getBufferedImage ()
    {
        return bufferedImage;
    }
}
