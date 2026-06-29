package com.datkatsu.functionplotter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainWindow
{
    private JFrame frame;
    private JPanel framePanel;
    private PlotCanvas plotCanvas;
    private JPanel leftToolbar;
    private JPanel topToolbar;
    private JPanel bottomToolBar;
    private ArrayList<JLabel> functionLabels = new ArrayList<> ();
    
    public MainWindow()
    {
        frame = new JFrame ();
        JFrame.setDefaultLookAndFeelDecorated (true);
        
        plotCanvas = new PlotCanvas();
        setBottomToolbar ();
        setLeftToolbar ();
        setTopToolbar ();
        
        framePanel = new JPanel ();
        framePanel.setPreferredSize (new Dimension (1200, 900));
        framePanel.setLayout (new BorderLayout ());
        framePanel.add (plotCanvas, BorderLayout.CENTER);
        framePanel.add (bottomToolBar, BorderLayout.SOUTH);
        framePanel.add (leftToolbar, BorderLayout.WEST);
        framePanel.add(topToolbar, BorderLayout.NORTH);
        
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout (new FlowLayout ());
        frame.add(framePanel);
        //frame.setResizable (false);
        frame.pack ();
        frame.setVisible (true);
       
    }
    
    private void setTopToolbar()
    {
        ImageSaverListener imageSaverListener = new ImageSaverListener("png", plotCanvas);
        JButton save = new JButton ("Save as PNG");
        save.setPreferredSize(new Dimension(600, 30));
        save.addActionListener (imageSaverListener);
        
        topToolbar = new JPanel ();
        topToolbar.setLayout (new FlowLayout (FlowLayout.CENTER));
        topToolbar.add (save);
    }
    
    private void setBottomToolbar()
    {

        JTextField textField = new JTextField ("2 * x");
        textField.setPreferredSize(new Dimension(600, 30));
        JButton draw = new JButton ("Draw");
        draw.setPreferredSize(new Dimension(150, 30));
        draw.addActionListener (e ->
        {
            plotCanvas.addFunction (textField.getText (), Color.blue, LineType.STRAIGHT);
            JLabel functionText = new JLabel(textField.getText ());
            functionLabels.add(functionText);
            leftToolbar.add(functionText);
            leftToolbar.revalidate();
            leftToolbar.repaint();
            framePanel.repaint ();
        });

        JButton clear = new JButton ("Clear");
        clear.setPreferredSize(new Dimension(150, 30));
        clear.addActionListener (e ->
        {
            plotCanvas.clear ();
            functionLabels.clear();
            leftToolbar.removeAll();
            leftToolbar.add(new JLabel ("Functions:"));
            leftToolbar.revalidate();
            leftToolbar.repaint();
            framePanel.repaint ();
        });
    
        bottomToolBar = new JPanel ();
        bottomToolBar.setLayout (new FlowLayout(FlowLayout.CENTER));
        bottomToolBar.add(textField);
        bottomToolBar.add(draw);
        bottomToolBar.add(clear);
    }

    private void setLeftToolbar()
    {
        leftToolbar = new JPanel ();
        leftToolbar.setLayout (new BoxLayout(leftToolbar, BoxLayout.Y_AXIS));
        leftToolbar.setPreferredSize(new Dimension(100, 900));
        leftToolbar.add(new JLabel ("Functions:"));

    }
}

