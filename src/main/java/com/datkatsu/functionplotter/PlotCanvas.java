package com.datkatsu.functionplotter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlotCanvas extends JPanel implements Saveable
{
    private BufferedImage bufferedImage;
    private Graphics2D g2d;
    private ArrayList<FunctionDrawer> functionList = new ArrayList<> ();
    private CoordinateInfo coordinateInfo = new CoordinateInfo ();
    private CoordinateSystem coordinateSystem;

    public PlotCanvas()
    {
        bufferedImage = new BufferedImage (coordinateInfo.getWidth (), coordinateInfo.getHeight (), BufferedImage.TYPE_INT_ARGB);
        g2d = bufferedImage.createGraphics ();
        g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint (Color.white);
        g2d.fillRect (0, 0, coordinateInfo.getWidth (), coordinateInfo.getHeight ());
        
        this.coordinateSystem = new CoordinateSystem(coordinateInfo);
        g2d.drawImage(coordinateSystem.getBufferedImage(), 0, 0, null);
        coordinateInfo.zoom = 1;
        CameraMouseListener cameraMouseListener = new CameraMouseListener ();
        addMouseListener (cameraMouseListener);
        addMouseMotionListener (cameraMouseListener);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent (g);
        Graphics2D g2 = (Graphics2D) g;
        draw (g2);
        //g2.drawImage (camera.getCameraImage (), 0, 0, 800, 800, null);
        
    }
    
    private void draw(Graphics2D g2)
    {
        g2.scale (coordinateInfo.zoom, coordinateInfo.zoom);
        g2.translate (-coordinateInfo.cameraXPos, -coordinateInfo.cameraYPos);
        g2d.setPaint (Color.white);
        g2d.fill (new Rectangle2D.Double (0, 0, coordinateInfo.getWidth (), coordinateInfo.getHeight ()));
        g2d.drawImage (coordinateSystem.getBufferedImage (), 0, 0, null);
        //g2d.scale (zoom, zoom);
        for(FunctionDrawer functionDrawer : functionList)
            g2d.drawImage (functionDrawer.getBufferedImage (), 0, 0, null);
        
        //g2.clip (new Rectangle2D.Double ());
        g2.drawImage (bufferedImage,0, 0, coordinateInfo.getWidth (), coordinateInfo.getHeight (), null);
    }
    
    public void addFunction (String s, Color color, LineType lineType)
    {
        functionList.add (new FunctionDrawer (new FunctionGraph (s, -100, 100, 1000), coordinateInfo, color, lineType));
    }
    
    private void mirrorY()
    {
        AffineTransform at = new AffineTransform ();
        at.concatenate (AffineTransform.getScaleInstance (1, -1));
        at.concatenate (AffineTransform.getTranslateInstance (0, -800));
        BufferedImage newImage = new BufferedImage (800, 800, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics ();
        g2.transform (at);
        g2.drawImage (bufferedImage, 0, 0, null);
        g2.dispose ();
        bufferedImage = newImage;
    }
    
    @Override
    public BufferedImage getImage ()
    {
        return bufferedImage;
    }
    
    
    public void clear()
    {
        functionList.clear ();
        repaint ();
    }
    
    class CameraMouseListener implements MouseMotionListener, MouseListener
    {
        int oldX, oldY;
        @Override
        public void mouseClicked (MouseEvent e)
        {
        
        }
    
        @Override
        public void mousePressed (MouseEvent e)
        {
            oldX = e.getX ();
            oldY = e.getY ();
        }
    
        @Override
        public void mouseReleased (MouseEvent e)
        {
        
        }
    
        @Override
        public void mouseEntered (MouseEvent e)
        {
        
        }
    
        @Override
        public void mouseExited (MouseEvent e)
        {
        
        }
    
        @Override
        public void mouseDragged (MouseEvent e)
        {
            int newX = e.getX ();
            int newY = e.getY ();
            int diffX = oldX - newX;
            int diffY = oldY - newY;
            coordinateInfo.cameraXPos += diffX;
            coordinateInfo.cameraYPos += diffY;
            oldX = newX;
            oldY = newY;
            repaint ();
        }
    
        @Override
        public void mouseMoved (MouseEvent e)
        {
        
        }
    }
    
}
