package artcreator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import artcreator.creator.port.Creator;
import artcreator.gui.Controller;
import artcreator.gui.CreatorFrame;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;

public class ChooseColorPaletteController extends Controller {

	public ChooseColorPaletteController(CreatorFrame view, Subject subject, Creator model) {
		super(view, subject, model);
		
	}

	@Override
	public void update(State currentState) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getMyView().createColorPaletteView();
	}

}