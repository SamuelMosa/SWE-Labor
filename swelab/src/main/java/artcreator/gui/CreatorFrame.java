package artcreator.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import artcreator.creator.CreatorFactory;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class CreatorFrame extends JFrame implements Observer{

		
	   private Creator creator = CreatorFactory.FACTORY.creator();
	   private Subject subject = StateMachineFactory.FACTORY.subject();
	   private Controller controller;
	   
	   private static final int WIDTH = 600;
	   private static final int HEIGHT = 500;
	   
	   private JButton btn = new JButton("Hello SWE");
	   private JPanel panel = new JPanel();
	   

	   public CreatorFrame()  {
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
	   }

	   public void update(State newState) {/* modify view if necessary */}

	}
