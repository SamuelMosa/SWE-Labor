package artcreator.creator.impl;

import artcreator.domain.port.Domain;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		// TODO Auto-generated constructor stub
	}

	public void sysop(String str) {
		System.out.println(str);
		
	}
	
	public void importImage(String filePathFirstImage, String filePathSecondImage) {
		// TODO Auto-generated method stub
		
	}

	public void switchPictureOrder() {
		// TODO Auto-generated method stub
		
	}

	public Profile loadProfile(int profileID) {
		// TODO Auto-generated method stub
		return null;
	}

	public int calculateColorID(String colorValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void saveProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

	public Template generateTemplate(Image leftImage, Image rightImage, Settings settings) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveTemplate(Template template) {
		// TODO Auto-generated method stub
		
	}
	
}
