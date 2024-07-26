package artcreator.creator;

import java.awt.image.BufferedImage;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.impl.Profile;
import artcreator.creator.impl.Settings;
import artcreator.creator.impl.Template;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.StateMachine;
import artcreator.statemachine.port.State.S;

public class CreatorFacade implements CreatorFactory, Creator {

	private CreatorImpl creator;
	private StateMachine stateMachine;
	
	@Override
	public Creator creator() {
		if (this.creator == null) {
			this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
			this.creator = new CreatorImpl(stateMachine, DomainFactory.FACTORY.domain());
		}
		return this;
	}

	@Override
	public synchronized void sysop(String str) {
		if (this.stateMachine.getState().isSubStateOf( S.INIT /* choose right state*/ ))
			this.creator.sysop(str);
	}

	@Override
	public void importImage(String filePathFirstImage, String filePathSecondImage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchPictureOrder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Profile loadProfile(int profileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calculateColorID(String colorValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Template generateTemplate(BufferedImage leftImage, BufferedImage rightImage, Settings settings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTemplate(Template template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setImage(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLeftImageFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLeftImageFilePath(String leftImageFilePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRightImageFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRightImageFilePath(String rightImageFilePath) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
