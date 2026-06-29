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
    private ArrayList<JLabel> functionList = new ArrayList<> ();
    
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
        save.addActionListener (imageSaverListener);
        
        topToolbar = new JPanel ();
        topToolbar.setLayout (new GridLayout (1, 8));
        topToolbar.add (save);
    }
    
    private void setBottomToolbar()
    {
        JTextField textField = new JTextField ("2 * x");
        JButton calculate = new JButton ("Draw");
        calculate.addActionListener (e ->
        {
            plotCanvas.addFunction (textField.getText (), Color.blue, LineType.STRAIGHT);
            framePanel.repaint ();
        });
        
        JButton clear = new JButton ("Clear");
        clear.addActionListener (e -> plotCanvas.clear ());
    
        bottomToolBar = new JPanel ();
        bottomToolBar.setLayout (new GridLayout (1, 3));
        bottomToolBar.add(textField);
        bottomToolBar.add(calculate);
        bottomToolBar.add(clear);
    }
    
    private void setLeftToolbar()
    {
        leftToolbar = new JPanel ();
        leftToolbar.setLayout (new GridLayout (20, 1));
        leftToolbar.add(new JLabel ("Functions:"));
        for(int i = 1; i < 20; i++)
        {
            functionList.add(new JLabel (""));
            leftToolbar.add (functionList.get (i-1));
        }
    }
}

