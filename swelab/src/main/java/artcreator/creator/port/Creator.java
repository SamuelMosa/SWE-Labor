package artcreator.creator.port;

import artcreator.creator.impl.Template;

import java.awt.image.BufferedImage;

import artcreator.creator.impl.Profile;
import artcreator.creator.impl.Settings;

public interface Creator {
	
	void sysop(String str);
	
	void importImage(String filePathFirstImage, String filePathSecondImage);
	
	void switchPictureOrder();
	
	Profile loadProfile(int profileID);
	
	int calculateColorID(String colorValue);
	
	void saveProfile(Profile profile);
	
	Template generateTemplate(BufferedImage leftImage, BufferedImage rightImage, Settings settings);
	
	void saveTemplate(Template template);
	
}
