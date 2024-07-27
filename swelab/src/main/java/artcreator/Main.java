package artcreator;

import artcreator.gui.CreatorFrame;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.impl.Profile;
import artcreator.creator.impl.Settings;
import artcreator.creator.impl.Template;
import artcreator.creator.port.ProfileService;
import artcreator.creator.impl.ProfileServiceImpl;

public class Main {

	public static void main(String[] args) {
		CreatorFrame frame = new CreatorFrame();
		//frame.createColorPaletteView();

		frame.setVisible(true);
		
		System.out.println("Projekt erfolgreich eingerichtet");
		CreatorImpl impl = new CreatorImpl(null, null);
		
		
		Settings settings_0 = new Settings(2.0f, 400);
		Profile profile_0 = new Profile(2, "Profile 2", settings_0);
		Profile profile_3 = new Profile(3, "Profile 3", settings_0);
		
		BufferedImage[] images = impl.importImage(System.getProperty("user.dir") + "/istockphoto-1271087162-612x612.jpg", System.getProperty("user.dir") + "/istockphoto-1271087162-612x612.jpg");
	    
		Template template = impl.generateTemplate(images[0], images[1], settings_0);

		
		settings_0.insertColor("#FF0000");
		settings_0.insertColor("#00FF00");
		settings_0.insertColor("#0000FF");
		settings_0.insertColor("#FFFF00");
		settings_0.insertColor("#00FFFF");
		settings_0.insertColor("#FFFFFF");
		
		System.out.println(System.getProperty("user.dir"));
		
		
	}

}
