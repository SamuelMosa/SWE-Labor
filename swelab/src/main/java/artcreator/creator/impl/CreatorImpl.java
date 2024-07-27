package artcreator.creator.impl;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import artcreator.creator.port.ProfileService;
import artcreator.domain.port.Domain;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {
	private StateMachine stateMachine;
	private Domain domain;
	private ProfileService profileService;

	private String leftImageFilePath;
	private String rightImageFilePath;

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
		this.stateMachine = stateMachine;
		this.domain = domain;
		this.profileService = new ProfileServiceImpl();
	}

	public void sysop(String str) {
		System.out.println(str);

	}

	public BufferedImage[] importImage(String filePathFirstImage, String filePathSecondImage) {
		BufferedImage[] images;

		if (filePathSecondImage == null) {
			images = new BufferedImage[1];
		} else {
			images = new BufferedImage[2];
		}

		for (int i = 0; i < images.length; i++) {
			String filepath;
			if (i == 0) {
				filepath = filePathFirstImage;
			} else {
				filepath = filePathSecondImage;
			}
			try {
				BufferedImage image = ImageIO.read(new File(filepath));
				images[i] = image;
				if (image != null) {
					System.out.println("Image loaded successfully");
				} else {
					System.out.println("Failed to load image");
				}

			} catch (Exception e) {
				System.out.println("Error reading the image file: " + e.getMessage());
				e.printStackTrace();
			}
		}

		return images;

	}

	public void switchPictureOrder(BufferedImage[] twoImages) {
		BufferedImage firstImage = twoImages[0];
		twoImages[0] = twoImages[1];
		twoImages[1] = firstImage;
	}

	public Profile loadProfile(int profileID) {
		List<Profile> profiles = profileService.loadProfiles();
		for (Profile profile : profiles) {
			if (profile.getId() == profileID) {
				return profile;
			}
		}
		return null; // oder eine Ausnahme werfen, wenn das Profil nicht gefunden wird
	}

	public int calculateColorID(String colorValue, Settings settings) {
		return settings.insertColor(colorValue);
	}

	public void saveProfile(Profile profile) throws IOException {
		List<Profile> profiles = profileService.loadProfiles();
		profiles.add(profile);
		profileService.saveProfiles(profiles);
	}

	public Template generateTemplate(BufferedImage leftImage, BufferedImage rightImage, Settings settings) {

	    // Convert hexadecimal colors to Color objects
	    List<Color> colorList = new ArrayList<>();
	    for (String hex : settings.getColorPalette()) {
	        colorList.add(Color.decode(hex));
	    }

	    // Specify the dimensions of the mosaic
	    int mosaicWidth = (int) Math.sqrt(settings.getNumToothpicks());
	    int mosaicHeight = mosaicWidth;

	    // Create the mosaic image
	    BufferedImage leftMosaicImage = createMosaicImage(leftImage, mosaicWidth, mosaicHeight, colorList);
	    BufferedImage rightMosaicImage = createMosaicImage(rightImage, mosaicWidth, mosaicHeight, colorList);


	    // Prepare the Graphics2D object
	    BufferedImage leftCanvas = new BufferedImage(leftImage.getWidth()+50, leftImage.getHeight()+50, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D leftG2d = leftCanvas.createGraphics();

	    BufferedImage rightCanvas = new BufferedImage(rightImage.getWidth()+50, rightImage.getHeight()+50, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D rightG2d = rightCanvas.createGraphics();
	    
	    // Display the mosaic image with circles
	    drawMosaicCircles(leftG2d, leftMosaicImage, 20, 10, settings); // Circle diameter and spacing
	    drawMosaicCircles(rightG2d, rightMosaicImage, 20, 10, settings); // Circle diameter and spacing

	    //g2d.dispose();

	    System.out.println("Template generated");

	    return new Template(leftCanvas, rightCanvas); // Return a suitable Template object instead of null
	}

	public void saveTemplate(Template template) {
	    // TODO Auto-generated method stub
	}

	private static BufferedImage createMosaicImage(BufferedImage originalImage, int mosaicWidth, int mosaicHeight, List<Color> colorList) {
	    int width = originalImage.getWidth();
	    int height = originalImage.getHeight();

	    // Create a new image with the reduced dimensions
	    BufferedImage scaledImage = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

	    // Scale down the original image to the new dimensions
	    Graphics2D g2d = scaledImage.createGraphics();
	    g2d.drawImage(originalImage, 0, 0, mosaicWidth, mosaicHeight, null);
	    g2d.dispose();

	    // Create a new image for the mosaic with closest colors
	    BufferedImage mosaicImage = new BufferedImage(mosaicWidth, mosaicHeight, BufferedImage.TYPE_INT_ARGB);

	    for (int y = 0; y < mosaicHeight; y++) {
	        for (int x = 0; x < mosaicWidth; x++) {
	            // Get the color of the current pixel
	            int pixelColor = scaledImage.getRGB(x, y);
	            Color pixel = new Color(pixelColor, true);

	            // Find the closest color from the list
	            Color closestColor = findClosestColor(pixel, colorList);

	            // Set the color in the mosaic image
	            mosaicImage.setRGB(x, y, closestColor.getRGB());
	        }
	    }

	    return mosaicImage;
	}

	private static Color findClosestColor(Color color, List<Color> colorList) {
	    Color closestColor = null;
	    int minDistance = Integer.MAX_VALUE;

	    for (Color candidate : colorList) {
	        int distance = colorDistance(color, candidate);
	        if (distance < minDistance) {
	            minDistance = distance;
	            closestColor = candidate;
	        }
	    }

	    return closestColor;
	}

	private static int colorDistance(Color color1, Color color2) {
	    int rDiff = color1.getRed() - color2.getRed();
	    int gDiff = color1.getGreen() - color2.getGreen();
	    int bDiff = color1.getBlue() - color2.getBlue();
	    return rDiff * rDiff + gDiff * gDiff + bDiff * bDiff; // Squared Euclidean distance
	}

	private static void drawMosaicCircles(Graphics2D g2d, BufferedImage mosaicImage, int circleDiameter, int spacing, Settings settings) {
	    int mosaicWidth = mosaicImage.getWidth();
	    int mosaicHeight = mosaicImage.getHeight();

	    for (int y = 0; y < mosaicHeight; y++) {
	        for (int x = 0; x < mosaicWidth; x++) {
	            int pixelColor = mosaicImage.getRGB(x, y);
	            Color color = new Color(pixelColor);

	            int drawX = x * (circleDiameter + spacing);
	            int drawY = y * (circleDiameter + spacing);

	            g2d.setColor(color);
	            g2d.fillOval(drawX, drawY, circleDiameter, circleDiameter);

	            int colorID = settings.getColorId(color);
	            g2d.setColor(Color.BLACK);
	            String idText = String.valueOf(colorID);
	            int textWidth = g2d.getFontMetrics().stringWidth(idText);
	            int textHeight = g2d.getFontMetrics().getHeight();
	            g2d.drawString(idText, drawX + (circleDiameter - textWidth) / 2, drawY + (circleDiameter + textHeight) / 2 - 5);
	        }
	    }
	}




//	private static void drawMosaicCircles(Graphics2D g2d, BufferedImage mosaicImage, int circleDiameter, int spacing,
//			Settings settings) {
//		int mosaicWidth = mosaicImage.getWidth();
//		int mosaicHeight = mosaicImage.getHeight();
//
//		for (int y = 0; y < mosaicHeight; y++) {
//			for (int x = 0; x < mosaicWidth; x++) {
//				int pixelColor = mosaicImage.getRGB(x, y);
//				Color color = new Color(pixelColor);
//
//				int drawX = x * (circleDiameter + spacing);
//				int drawY = y * (circleDiameter + spacing);
//
//				g2d.setColor(color);
//				g2d.fillOval(drawX, drawY, circleDiameter, circleDiameter);
//
//				int colorID = settings.getColorId(color);
//				g2d.setColor(Color.BLACK);
//				String idText = String.valueOf(colorID);
//				int textWidth = g2d.getFontMetrics().stringWidth(idText);
//				int textHeight = g2d.getFontMetrics().getHeight();
//				g2d.drawString(idText, drawX + (circleDiameter - textWidth) / 2,
//						drawY + (circleDiameter + textHeight) / 2 - 5);
//			}
//		}
//	}
//
//	private static void displayMosaicCircles(BufferedImage mosaicImage, int circleDiameter, int spacing,
//			Settings setting) {
//		// Calculate the size needed for the JFrame
//		int mosaicWidth = mosaicImage.getWidth();
//		int mosaicHeight = mosaicImage.getHeight();
//		int totalWidth = mosaicWidth * (circleDiameter + spacing) - spacing;
//		int totalHeight = mosaicHeight * (circleDiameter + spacing) - spacing;
//
//		// Create a custom JPanel to display the image with circles
//		JPanel panel = new JPanel() {
//			@Override
//			protected void paintComponent(Graphics g) {
//				super.paintComponent(g);
//				Graphics g2d = drawMosaicCircles(g, mosaicImage, circleDiameter, spacing, setting);
//			}
//		};
//
//		// Create a JFrame to hold the JPanel
//		JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.add(panel);
//		frame.setSize(totalWidth, totalHeight);
//		frame.setVisible(true);
//
//	}

	public String getLeftImageFilePath() {
		return leftImageFilePath;
	}

	public void setLeftImageFilePath(String leftImageFilePath) {
		this.leftImageFilePath = leftImageFilePath;
	}

	public String getRightImageFilePath() {
		return rightImageFilePath;
	}

	public void setRightImageFilePath(String rightImageFilePath) {
		this.rightImageFilePath = rightImageFilePath;
	}

}
