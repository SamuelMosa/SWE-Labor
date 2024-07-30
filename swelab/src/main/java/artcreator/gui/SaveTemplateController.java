package artcreator.gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.port.Creator;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;

public class SaveTemplateController extends Controller {
	public SaveTemplateController(CreatorFrame view, Subject subject, Creator model) {
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
		CreatorImpl creatorImpl = new CreatorImpl(null, null);

		if (currentState.equals(State.S.TEMPLATE_GENERATED)) {
			System.out.println("SaveSettingsController");
			String str = (((JButton) e.getSource()).getText());
			if (str.equals("Linksbild speichern")) {
				creatorImpl.saveTemplate(getMyView().getTemplate(), true);
			} else if (str.equals("Rechtsbild speichern")) {
				creatorImpl.saveTemplate(getMyView().getTemplate(), false);
			} else {
				throw new IllegalArgumentException("wrong state!");
			}
		}
	}
}

		
	