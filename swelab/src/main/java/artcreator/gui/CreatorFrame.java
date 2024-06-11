package artcreator.gui;

import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import artcreator.creator.CreatorFactory;
import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class CreatorFrame extends JFrame implements Observer {

	private Creator creator = CreatorFactory.FACTORY.creator();
	private Subject subject = StateMachineFactory.FACTORY.subject();
	private Controller controller;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 500;

	private JButton btn = new JButton("Hello SWE");
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();

	public CreatorFrame() {
		super("ArtCreator");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.subject.attach(this);
		this.controller = new Controller(this, subject, creator);

		/* build view */
		this.btn.addActionListener(this.controller);
		this.panel.add(this.btn);
		this.getContentPane().add(this.panel);

		CreatorImpl impl = new CreatorImpl(null, null);
		BufferedImage[] images = impl.importImage("C:/Users/ninoo/Downloads/WhatsApp Image 2024-06-11 at 15.56.43.jpeg", null);
		
		this.panel.add(this.label);
		this.label.setIcon(new ImageIcon(images[0]));
	}

	public void update(State newState) {
		/* modify view if necessary */}

}
