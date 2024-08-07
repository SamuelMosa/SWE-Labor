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

    public static Template generateTemplate(BufferedImage leftImage, BufferedImage rightImage, Settings settings) {
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

        // Calculate the required canvas dimensions
        int circleDiameter = 20;
        int spacing = 10;
        int canvasWidth = mosaicWidth * (circleDiameter + spacing) - spacing;
        int canvasHeight = mosaicHeight * (circleDiameter + spacing) - spacing;

        // Prepare the Graphics2D object
        BufferedImage leftCanvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D leftG2d = leftCanvas.createGraphics();

        BufferedImage rightCanvas = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D rightG2d = rightCanvas.createGraphics();

        // Display the mosaic image with circles
        drawMosaicCircles(leftG2d, leftMosaicImage, circleDiameter, spacing, settings); // Circle diameter and spacing
        drawMosaicCircles(rightG2d, rightMosaicImage, circleDiameter, spacing, settings); // Circle diameter and spacing

        // Dispose graphics objects
        leftG2d.dispose();
        rightG2d.dispose();

        System.out.println("Template generated");

        return new Template(leftCanvas, rightCanvas); // Return a suitable Template object instead of null
    }

    public static void saveTemplate(Template template, boolean isLeftImage) {
        BufferedImage image = isLeftImage ? template.getLeftImage() : template.getRightImage();
        String filePath = System.getProperty("user.dir");
        
        // Get the user's home directory and the Downloads folder
//        String userHome = System.getProperty("user.home");
//        String downloadPath = userHome + File.separator + "Downloads";
        
        // Create the file name based on the image type
        String fileName = isLeftImage ? "Linksbild.png" : "Rechtsbild.png";
        File outputFile = new File(filePath, fileName);
        
        try {
            // Write the image to the file as a PNG
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved to " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error saving image: " + e.getMessage());
        }
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

                // Display the color ID as a number on the circle
                int colorID = settings.getColorId(color);
                g2d.setColor(Color.BLACK);
                String idText = String.valueOf(colorID);
                int textWidth = g2d.getFontMetrics().stringWidth(idText);
                int textHeight = g2d.getFontMetrics().getHeight();
                g2d.drawString(idText, drawX + (circleDiameter - textWidth) / 2, drawY + (circleDiameter + textHeight) / 2 - 5);
            }
        }
    }

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
