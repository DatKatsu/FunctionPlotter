package com.datkatsu.functionplotter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class FunctionDrawer
{
    private FunctionGraph functionGraph;
    private BufferedImage bufferedImage;
    private double threshold = 8;

    private Color color;

    public FunctionDrawer(FunctionGraph functionGraph, Color color)
    {
        this.functionGraph = functionGraph;
        this.color = color;
        drawFunction ();
    }
    
    public void drawFunction()
    {
        this.bufferedImage = new BufferedImage (3200, 3200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dBuffer = bufferedImage.createGraphics ();
        g2dBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dBuffer.setPaint (this.color);
        g2dBuffer.translate (1600, 1600);
        g2dBuffer.scale (1, -1);
        double time = System.nanoTime ();

        //g2dBuffer.draw(new Line2D.Double(0, 0, 1000, 1000));
        drawStraight (g2dBuffer);

        time = (System.nanoTime () - time);
        System.out.println ("FunctionDrawer: "+time/Math.pow(10, 9)+" Sekunden");
    }
    
    public void drawStraight(Graphics2D g2d)
    {
        GeneralPath generalPath = new GeneralPath ();
        
        int j = findNextPoint (generalPath, 0);
        Point2D.Double[] points = functionGraph.getPoints();

        double previousY = Double.NaN;

        for(int i = j; i < functionGraph.size (); i++)
        {
            double x = points[i].getX ();
            double y = points[i].getY ();
            
            if(Double.isInfinite(y) || Double.isNaN(y))
            {
                i = findNextPoint (generalPath, i);
                previousY = Double.NaN;
            }
            else
            {
                if(!Double.isNaN(previousY) && Math.abs(y - previousY) > threshold) {
                    generalPath.moveTo(x * 40, y * 40);
                }
                else
                {
                    generalPath.lineTo (x * 40, y * 40);
                }

                previousY = y;
            }
            
        }
        g2d.draw(generalPath);
    }
    
    private int findNextPoint(GeneralPath generalPath, int begin)
    {
        Point2D.Double[] points = functionGraph.getPoints();

        for(int i = begin; i < functionGraph.size (); i++)
        {
            double x = points[i].getX ();
            double y = points[i].getY ();

            if(!(Double.isInfinite(y) || Double.isNaN(y)))
            {
                generalPath.moveTo (x * 40, y * 40);
                return i;
            }
        }
        
        return functionGraph.size ();
    }
    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }

}
