package artcreator;

import artcreator.gui.CreatorFrame;
import artcreator.creator.impl.CreatorImpl;

public class Main {

	public static void main(String[] args) {
		CreatorFrame frame = new CreatorFrame();
	      frame.setVisible(true);
	      System.out.println("Projekt erfolgreich eingerichtet");
	      CreatorImpl impl = new CreatorImpl(null, null);
	      impl.importImage("C:/Users/ninoo/Downloads/WhatsApp Image 2024-06-11 at 15.56.43.jpeg", null);
		
	}

}
