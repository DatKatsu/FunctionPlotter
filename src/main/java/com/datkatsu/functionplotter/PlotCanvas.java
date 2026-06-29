package com.datkatsu.functionplotter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlotCanvas extends JPanel implements Saveable
{
    private BufferedImage bufferedImage;
    private ArrayList<FunctionDrawer> functionDrawers = new ArrayList<> ();
    private CoordinateInfo coordinateInfo = new CoordinateInfo(-40, 40, -40, 40, 1, 1);
    private CoordinateDrawer coordinateDrawer;

    public PlotCanvas()
    {
        bufferedImage = new BufferedImage (3200, 3200, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferG2d = bufferedImage.createGraphics ();
        bufferG2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        bufferG2d.setPaint (Color.white);
        bufferG2d.fillRect (0, 0, 3200, 3200);
        
        this.coordinateDrawer = new CoordinateDrawer(coordinateInfo, 3200, 3200);
        bufferG2d.drawImage(coordinateDrawer.getBufferedImage(), 0, 0, null);
        //coordinateInfo.zoom = 1;
        //CameraMouseListener cameraMouseListener = new CameraMouseListener ();
        //addMouseListener (cameraMouseListener);
        //addMouseMotionListener (cameraMouseListener);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent (g);
        Graphics2D g2d = (Graphics2D) g;
        Graphics2D g2dBuffer = bufferedImage.createGraphics ();

        g2dBuffer.setPaint (Color.white);
        g2dBuffer.fill (new Rectangle2D.Double (0, 0, 3200, 3200));

        g2dBuffer.drawImage (coordinateDrawer.getBufferedImage (), 0, 0, null);

        for(FunctionDrawer drawer : functionDrawers)
            g2dBuffer.drawImage (drawer.getBufferedImage (), 0, 0, null);

        int x = (getWidth() - bufferedImage.getWidth()) / 2;
        int y = (getHeight() - bufferedImage.getHeight()) / 2;
        g2d.drawImage (bufferedImage, x, y, null);
        
    }

    public void addFunction (String s, Color color, LineType lineType)
    {
        FunctionGraph graph = new FunctionGraph(s, -100, 100, 10000);
        FunctionDrawer drawer = new FunctionDrawer(graph, color);
        functionDrawers.add (drawer);
        System.out.println("Add");
    }
    
    @Override
    public BufferedImage getImage ()
    {
        return bufferedImage;
    }

    public void clear()
    {
        functionDrawers.clear ();
        repaint ();
    }
    
//    class CameraMouseListener implements MouseMotionListener, MouseListener
//    {
//        int oldX, oldY;
//        @Override
//        public void mouseClicked (MouseEvent e)
//        {
//
//        }
//
//        @Override
//        public void mousePressed (MouseEvent e)
//        {
//            oldX = e.getX ();
//            oldY = e.getY ();
//        }
//
//        @Override
//        public void mouseReleased (MouseEvent e)
//        {
//
//        }
//
//        @Override
//        public void mouseEntered (MouseEvent e)
//        {
//
//        }
//
//        @Override
//        public void mouseExited (MouseEvent e)
//        {
//
//        }
//
//        @Override
//        public void mouseDragged (MouseEvent e)
//        {
//            int newX = e.getX ();
//            int newY = e.getY ();
//            int diffX = oldX - newX;
//            int diffY = oldY - newY;
//            coordinateInfo.cameraXPos += diffX;
//            coordinateInfo.cameraYPos += diffY;
//            oldX = newX;
//            oldY = newY;
//            repaint ();
//        }
//
//        @Override
//        public void mouseMoved (MouseEvent e)
//        {
//
//        }
//    }
    
}
