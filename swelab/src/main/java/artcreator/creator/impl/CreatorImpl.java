package artcreator.creator.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import artcreator.creator.port.ProfileService;
import artcreator.domain.port.Domain;
import artcreator.statemachine.port.StateMachine;

public class CreatorImpl {
	private StateMachine stateMachine;
    private Domain domain;
    private ProfileService profileService;

	public CreatorImpl(StateMachine stateMachine, Domain domain) {
        this.stateMachine = stateMachine;
        this.domain = domain;
        this.profileService = new ProfileServiceImpl(); // Idealerweise Dependency Injection verwenden
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

	public void switchPictureOrder() {
		// TODO Auto-generated method stub

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

	public int calculateColorID(String colorValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void saveProfile(Profile profile) throws IOException {
        List<Profile> profiles = profileService.loadProfiles();
        profiles.add(profile);
        profileService.saveProfiles(profiles);
    }

	public Template generateTemplate(Image leftImage, Image rightImage, Settings settings) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveTemplate(Template template) {
		// TODO Auto-generated method stub

	}

}
