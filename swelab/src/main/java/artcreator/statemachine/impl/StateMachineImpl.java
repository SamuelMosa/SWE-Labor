package artcreator.statemachine.impl;

import java.util.ArrayList;
import java.util.List;

import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;

public class StateMachineImpl {

	
	private List<Observer> observers = new ArrayList<>();

	private State currentState = S.INITIAL_STATE;

	public void attach(Observer obs) {
		if (this.observers.add(obs))
			obs.update(this.currentState);
	}

	public void detach(Observer obs) {
		if (this.observers.remove(obs))
			obs.update(this.currentState);
	}

	public void detachAll() {
		this.observers.clear();
	}

	public State getState() {
		return this.currentState;
	}

	public boolean setState(State state) {
		if (state == null)
			return false;
		this.currentState = state;
		this.observers.forEach(obs -> obs.update(this.currentState));
		return true;
	}


	
}
