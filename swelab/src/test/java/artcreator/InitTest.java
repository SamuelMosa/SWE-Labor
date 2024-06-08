package artcreator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import artcreator.creator.CreatorFactory;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.domain.port.Domain;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;

class InitTest implements Observer{
	
	State s;
	

	@Test
	void test() {
		
		
		StateMachine stateMachine = StateMachineFactory.FACTORY.stateMachine();
		Assertions.assertNotNull(stateMachine);
		Subject subject = StateMachineFactory.FACTORY.subject();
		Assertions.assertEquals(stateMachine, subject);
		subject.attach(this);
		
		Assertions.assertTrue(stateMachine.getState().isSubStateOf( S.CREATE_TEMPLATE));
		Assertions.assertEquals(S.CREATE_TEMPLATE, this.s);
		subject.detach(this);

		Domain domain = DomainFactory.FACTORY.domain();
		Assertions.assertNotNull(domain);
				
		
		Creator creator = CreatorFactory.FACTORY.creator();
		Assertions.assertNotNull(creator);
		
		creator.sysop("test");
		Assertions.assertTrue(true);

	}

	@Override
	public void update(State currentState) {
		this.s = currentState;
		
	}

}
