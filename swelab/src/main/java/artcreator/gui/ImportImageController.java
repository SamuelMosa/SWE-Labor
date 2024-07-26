package artcreator.gui;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.port.Creator;
import artcreator.gui.Controller;
import artcreator.creator.impl.*;
import artcreator.gui.CreatorFrame;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;

public class ImportImageController extends Controller {

	private JLabel imageLabel;

	public ImportImageController(CreatorFrame view, Subject subject, Creator model) {
		super(view, subject, model);
	}

	public void setImageLabel(JLabel imageLabel) {
		this.imageLabel = imageLabel;
	}

	@Override
	public void update(State currentState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = (((JButton) e.getSource()).getText());
		StateMachine stateMachine = StateMachineFactory.FACTORY.stateMachine();
		State currentState = stateMachine.getState();
		if (currentState.equals(State.S.IMAGE_IMPORTED)) {
			if (str.equals("Linkes Bild Importieren")) {
				getMyModel().setLeftImageFilePath(openFileChooser(true));
			} else {
				getMyModel().setRightImageFilePath(openFileChooser(false));
			}
		} else {
			throw new IllegalArgumentException("wrong state!");
		}
	}

	private String openFileChooser(boolean isLeftImgage) {
		FileDialog fileDialog = new FileDialog(super.getMyView());
		fileDialog.setVisible(true);
		String directory = fileDialog.getDirectory();
		String fileName = fileDialog.getFile();
		System.out.println("File: " + fileName);
		if (fileName != null && directory != null) {
			File file = new File(directory, fileName);
			if (isLeftImgage) {
				getMyView().getLeftImagePlaceHolder().setIcon(new ImageIcon(file.getPath()));
			} else {
				getMyView().getRightImagePlaceHolder().setIcon(new ImageIcon(file.getPath()));
			}
		}
		return fileName;
	}
}
