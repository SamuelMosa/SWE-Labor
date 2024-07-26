package artcreator.gui;

import java.awt.event.ActionEvent;

import artcreator.creator.port.Creator;
import artcreator.gui.Controller;
import artcreator.gui.CreatorFrame;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;

public class SettingsController extends Controller {

	public SettingsController(CreatorFrame view, Subject subject, Creator model) {
		super(view, subject, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(State currentState) {
// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StateMachine stateMachine = StateMachineFactory.FACTORY.stateMachine();
		State currentState = stateMachine.getState();
		if (currentState.equals(State.S.IMAGE_IMPORTED)) {
			stateMachine.setState(State.S.SETTINGS);
		}
	}

}