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

	public void setImportImageState() {
		if (currentState == S.INIT || currentState == S.SWITCH_PICTURES || currentState == S.IMPORT_PROFILE) {
			setState(S.IMAGE_IMPORTED);
		} else {
			throw new IllegalStateException("Image import is only allowed from INIT, SWITCH_PICTURES, or IMPORT_PROFILE states.");
		}
	}

	public void setSwitchPicturesState() {
		if (currentState == S.IMAGE_IMPORTED) {
			setState(S.SWITCH_PICTURES);
		} else {
			throw new IllegalStateException("Switching pictures is only allowed from the IMAGE_IMPORTED state.");
		}
	}

	public void setImportProfileState() {
		if (currentState == S.IMAGE_IMPORTED) {
			setState(S.IMPORT_PROFILE);
		} else {
			throw new IllegalStateException("Profile import is only allowed from the IMAGE_IMPORTED state.");
		}
	}

	public void setSettingsState() {
		if (currentState == S.IMAGE_IMPORTED) {
			setState(S.SETTINGS);
		} else {
			throw new IllegalStateException("Settings can only be accessed from the IMAGE_IMPORTED state.");
		}
	}

	public void setSettingsDoneState() {
		if (currentState == S.SETTINGS) {
			setState(S.SETTINGS_DONE);
		} else {
			throw new IllegalStateException("Completing settings is only allowed from the SETTINGS state.");
		}
	}

	public void setTemplateGeneratedState() {
		if (currentState == S.SETTINGS_DONE) {
			setState(S.TEMPLATE_GENERATED);
		} else {
			throw new IllegalStateException("Template generation is only allowed from the SETTINGS_DONE state.");
		}
	}
}
