package artcreator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import artcreator.creator.port.Creator;
import artcreator.gui.Controller;
import artcreator.gui.CreatorFrame;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.Subject;

public class AddColorController extends Controller {

	  private JTextField textField;
	    private JButton actionButton;
	    private DefaultTableModel tableModel;

	    public AddColorController(CreatorFrame view, Subject subject, Creator model, JTextField textField, JButton actionButton, DefaultTableModel tableModel) {
	        super(view, subject, model);
	        this.textField = textField;
	        this.actionButton = actionButton;
	        this.tableModel = tableModel;
	        this.actionButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                handleActionButton();
	            }
	        });
	    }

	    @Override
	    public void update(State currentState) {
	        // Implementation for update method if needed
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        // Delegate the action to the specific method
	        handleActionButton();
	    }

	    private void handleActionButton() {
	        String text = textField.getText();
	        
	        System.out.println("Text entered: " + text);
	        
	        int newColorID = tableModel.getRowCount() + 1; 
	        tableModel.addRow(new Object[]{newColorID, text});
	        
	        getMyView().getSettings().insertColor(text);
	        
	        textField.setText("");
	    }

}