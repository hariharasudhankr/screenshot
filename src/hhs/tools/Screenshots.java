package hhs.tools;
/**********************************************************************************************************************
 * File name   : Screenshots.java                                                                                     *
 *                                                                                                                    *
 * Author      : Hari Hara Sudhan                                                                                     *
 * Date	       : 8-Jan-2015                                                                                           *
 * Version     : Initial prototype                                                                                    *
 * How to use  : 1. On execution a file browser will be displayed to select a folder to place the screenshot          *
 *               2. Once the folder is selected a small window will be placed above all the window                    *
 *               3. Click the small window, for each click screenshot will be taken and placed in the selected folder *
 *               4. On closing the small window the output folder will be open                                        *
 *                                                                                                                    *
 * Note for developers: This can be integrated with Selenium/Any java based testing automation to take                *
 *                      screenshots automatically                                                                     *
 **********************************************************************************************************************/

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Screenshots extends JFrame implements MouseListener, WindowListener
{

	private static final long serialVersionUID = 1L;

	private String filePath = "";
	private String dot = ".";
	private String extension = "jpg";
	private String fileName = ".";
	private String thisFileName = ".";
	private int keyPressCount = 0;

	Rectangle screenAsRectangle;
	Dimension screenSize;
	BufferedImage imageFile;
	Robot robot;
	Random randomNumberGenerator;
	JFrame smallbox;
	File outFile;

	/**********************************************************
	 * * Create a small window with which will listen to mouse * clicks * *
	 **********************************************************/
	/* @SupressWarnings("deprication") */
	public Screenshots(String folderSelected)
	{

		filePath = folderSelected;
		try
		{
			robot = new Robot();
			randomNumberGenerator = new Random();
			smallbox = new JFrame();
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}

		smallbox.setLocation(HEIGHT, WIDTH);
		smallbox.setAlwaysOnTop(true);
		smallbox.setLocationByPlatform(true);
		smallbox.setSize(90, 60);
		smallbox.add(new JLabel("Click!"));
		smallbox.setVisible(true);
		smallbox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		smallbox.setAlwaysOnTop(true);
		smallbox.setCursor(Cursor.CROSSHAIR_CURSOR);
		smallbox.requestFocus();
		smallbox.toFront();
		smallbox.addMouseListener(this);
		smallbox.addWindowListener(this);
	}

	/**********************************************************
	 * * Take screenshot of window and save into a file * *
	 **********************************************************/
	public void createPictures()
	{

		/* Create a jpg file */
		try
		{
			keyPressCount = keyPressCount + 1;
			thisFileName = "/Img-" + keyPressCount + "_" + String.valueOf(randomNumberGenerator.nextInt(999999));
			fileName = filePath + thisFileName + dot + extension;
			System.out.println("filename=" + fileName);
		} catch (Exception fileNameCreationException)
		{
			fileNameCreationException.printStackTrace();
		}

		/* Get the resulution of the screen and use it to create screenshot */
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenAsRectangle = new Rectangle(screenSize);

		/*
		 * With the screen resolution create a snap of current window and write into a
		 * file
		 */
		imageFile = robot.createScreenCapture(screenAsRectangle);
		outFile = new File(fileName);
		try
		{
			ImageIO.write(imageFile, extension, outFile);
		} catch (IOException ioException)
		{
			ioException.printStackTrace();
		}
	}

	/*
	 * For each mouse click in the window take a snap of window and save into a file
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		createPictures();
	}

	/* When the screenshot taking window is closed open the output folder */
	@Override
	public void windowClosing(WindowEvent e)
	{

		if (keyPressCount > 0)
		{
			Desktop desktop = Desktop.getDesktop();
			File directoryToOpen = new File(filePath);
			try
			{
				desktop.open(directoryToOpen);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}

	/**/
	public static void main(String argument[])
	{
		String chosenFolder = "";
		JFileChooser chooser = new JFileChooser();

		chooser.setCurrentDirectory(new File("."));
		chooser.setDialogTitle("Select Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setApproveButtonText("Select");
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			chosenFolder = chooser.getSelectedFile().toString();
			new Screenshots(chosenFolder);
		}
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

}