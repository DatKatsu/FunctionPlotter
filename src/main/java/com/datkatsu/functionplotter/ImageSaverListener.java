package com.datkatsu.functionplotter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ImageSaverListener implements ActionListener
{
    private final String imagetype;
    private final Saveable saveable;
    
    public ImageSaverListener (String imagetype, Saveable saveable)
    {
        this.imagetype = imagetype;
        this.saveable = saveable;
    }
    
    @Override
    public void actionPerformed (ActionEvent e)
    {
        try
        {
            JFileChooser chooser = new JFileChooser ();
            int retVal = chooser.showSaveDialog (null);
            if (retVal == JFileChooser.APPROVE_OPTION)
            {
                File file = chooser.getSelectedFile ();
                ImageIO.write (saveable.getImage(), imagetype, new File (file.getAbsolutePath () + "."+imagetype));
            }
        }
        catch (
                IOException ioe)
        {
            System.out.println ("Could not save file");
        }
    }
}
