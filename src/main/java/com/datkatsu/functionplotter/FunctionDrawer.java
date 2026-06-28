package com.datkatsu.functionplotter;

import com.datkatsu.expressionparser.ExpressionProcessor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class FunctionDrawer
{
    private FunctionGraph functionGraph;

    private BufferedImage bufferedImage;
    private Graphics2D g2dBuffer;
    
    private CoordinateInfo coordinateInfo;
    
    private Color color;
    private LineType lineType;
    
    private double xDistance = 1;
    
    public FunctionDrawer(FunctionGraph functionGraph, CoordinateInfo coordinateInfo, Color color, LineType lineType)
    {
        this.functionGraph = functionGraph;

        this.coordinateInfo = coordinateInfo;
        this.color = color;
        this.lineType = lineType;
        
        drawFunction ();
    }
    
    public void drawFunction()
    {
        this.bufferedImage = new BufferedImage (coordinateInfo.getWidth (), coordinateInfo.getHeight (), BufferedImage.TYPE_INT_ARGB);
        this.g2dBuffer = bufferedImage.createGraphics ();
        g2dBuffer.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dBuffer.setPaint (this.color);
        g2dBuffer.translate (coordinateInfo.getOriginX (), Math.abs(coordinateInfo.yMax * coordinateInfo.getyStep ()));
        g2dBuffer.scale (1, -1);
        
        double time = System.nanoTime ();
        
        if(lineType == LineType.STRAIGHT)
            drawStraight ();
        else if(lineType == LineType.DOTTED);
            drawDotted ();
    
        time = (System.nanoTime () - time);
        System.out.println ("FunctionDrawer: "+time/Math.pow(10, 9)+" Sekunden");
    }
    
    public void drawDotted()
    {
        //int step = (int) Math.max (1, xDistance * (Math.pow (10,coordinateInfo.digits)));
        int step = 1;
        Point2D.Double[] points = functionGraph.getPoints();

        for(int i = 0; i < functionGraph.size (); i+= step)
        {
            double x = points[i].getX ();
            double y = points[i].getY ();
            
            if(Double.isInfinite(y) || Double.isNaN(y))
            {
                //System.out.println ("Infinite or NaN. Not Drawn");
            }
            else
            {
                g2dBuffer.fill (new Ellipse2D.Double (x * coordinateInfo.getxStep () - 1, y * coordinateInfo.getyStep () - 1, 2, 2));
                //System.out.println (bigX.doubleValue ()+" " +y);
            }
        }
    }
    
    
    public void drawStraight()
    {
        GeneralPath generalPath = new GeneralPath ();
        
        int j = findNextPoint (generalPath, 0);
        Point2D.Double[] points = functionGraph.getPoints();

        for(int i = j; i < functionGraph.size (); i++)
        {
            double x = points[i].getX ();
            double y = points[i].getY ();
            
            if(Double.isInfinite(y) || Double.isNaN(y))
            {
                i = findNextPoint (generalPath, i);
            }
            else
            {
                generalPath.lineTo (x * coordinateInfo.getxStep (), y * coordinateInfo.getyStep ());
                generalPath.moveTo (x * coordinateInfo.getxStep (), y * coordinateInfo.getyStep ());
                //System.out.println ("GP: "+bigX.doubleValue ()+" " +y);
            }
            
        }
        g2dBuffer.draw(generalPath);
    }
    
    private int findNextPoint(GeneralPath generalPath, int begin)
    {
        Point2D.Double[] points = functionGraph.getPoints();

        for(int i = begin; i < functionGraph.size (); i++)
        {
            double x = points[i].getX ();
            double y = points[i].getY ();

            if(Double.isInfinite(y) || Double.isNaN(y))
            {
            
            }
            else
            {
                generalPath.moveTo (x * coordinateInfo.getxStep (), y * coordinateInfo.getyStep ());
                return i;
                //System.out.println ("GP: "+bigX.doubleValue ()+" " +y);
            }
        }
        
        return functionGraph.size ();
    }
    
    public BufferedImage getBufferedImage()
    {
        return bufferedImage;
    }
    
    public void update()
    {
        functionGraph.update ();
        drawFunction ();
    }
    
    public void setxDistance (double xDistance)
    {
        this.xDistance = xDistance;
    }
}
