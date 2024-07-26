package artcreator.creator.impl;

import java.awt.image.BufferedImage;

public class Template {
	private BufferedImage image;
	
	public Template(BufferedImage image) {
		this.image = image;
	};
	
	public BufferedImage getImage( ) {
		return image;
	}
}
