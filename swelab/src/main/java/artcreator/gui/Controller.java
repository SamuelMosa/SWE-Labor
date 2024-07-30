package artcreator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

import javax.swing.JButton;

import artcreator.creator.port.Creator;
import artcreator.statemachine.port.Observer;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.Subject;

public class Controller implements ActionListener, Observer {

	private CreatorFrame myView;
	public Creator myModel;
	private Subject subject;

	public Controller(CreatorFrame view, Subject subject, Creator model) {
		this.setMyView(view);
		this.setMyModel(model);
		this.subject = subject;
		this.subject.attach(this);
	}

	public synchronized void actionPerformed(ActionEvent e) {

		/* read input */
		String str = (((JButton) e.getSource()).getText());

		CompletableFuture.runAsync(() -> this.getMyModel().sysop(str));
	}

	public void update(State newState) {
		/* modify controller or view if necessary */}

	public CreatorFrame getMyView() {
		return myView;
	}

	public void setMyView(CreatorFrame myView) {
		this.myView = myView;
	}

	public Creator getMyModel() {
		return myModel;
	}

	public void setMyModel(Creator myModel) {
		this.myModel = myModel;
	}

}
