package artcreator.creator.impl;

import java.awt.image.BufferedImage;

public class Template {
	private BufferedImage leftImage;
	private BufferedImage rightImage;
	
	public Template(BufferedImage leftImage, BufferedImage rightImage) {
		this.leftImage = leftImage;
		this.rightImage = rightImage;
	};
	
	public BufferedImage getLeftImage() {
		return leftImage;
	}
	
	public BufferedImage getRightImage() {
		return rightImage;
	}
}
