package artcreator.statemachine;

import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;

public interface StateMachineFactory {
	
	StateMachineFactory FACTORY = new StateMachineFacade();
	
	StateMachine stateMachine();

	Subject subject();


}
