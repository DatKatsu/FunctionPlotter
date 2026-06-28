package com.datkatsu.functionplotter;

public class CoordinateInfo
{
    public double xMin = - 40;
    public double xMax = 40;
    public double yMin = -40;
    public double yMax = 40 ;
    
    public double digits = 1;
    
    private int width = 3200;
    private int height = 3200;
    private double xStep; //1 Unit = xStep pixels
    private double yStep; //1 Unit = yStep pixels
    
    public double xLineDist;
    public double yLineDist;
    
    private double originX;
    private double originY;
    
    public double cameraXPos = 0;
    public double cameraYPos = 0;
    
    public double zoom = 1;
    
    public CoordinateInfo()
    {
        updateInfo ();
        cameraXPos = originX/2;
        cameraYPos = originY/2;
    }
    
    public void updateInfo()
    {
        xStep = getWidth ()/Math.abs (xMax - xMin);
        yStep = getHeight ()/Math.abs (yMax - yMin);
        originX = Math.abs (xMin) * xStep;
        originY = Math.abs (yMin) * yStep;
        xLineDist = Math.abs (xMax - xMin)/40;
        yLineDist = Math.abs (yMax - yMin)/40;
        digits = -Math.log10(xLineDist/100) ;
    
        System.out.println ("x from "+xMin+" "+xMax);
        System.out.println ("y from "+yMin+" "+yMax);
        System.out.println ("Origin at Pos("+originX+", "+originY+")");
        System.out.println ("Distance between Lines x: "+xLineDist);
        System.out.println ("Distance between Lines y: "+yLineDist);
        System.out.println (+digits+" digits that are calculated");
    }
    
    public int getWidth ()
    {
        return width;
    }
    
    public void setWidth (int width)
    {
        this.width = width;
    }
    
    public int getHeight ()
    {
        return height;
    }
    
    public void setHeight (int height)
    {
        this.height = height;
    }
    
    public double getxStep ()
    {
        return xStep;
    }
    
    public double getyStep ()
    {
        return yStep;
    }
    
    public double getOriginX ()
    {
        return originX;
    }
    
    
    public double getOriginY ()
    {
        return originY;
    }
    
}
