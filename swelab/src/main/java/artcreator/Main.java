package artcreator;

import artcreator.gui.CreatorFrame;

import java.util.Scanner;
import java.io.IOException;
import java.util.*;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.impl.Profile;
import artcreator.creator.impl.Settings;
import artcreator.creator.port.ProfileService;
import artcreator.creator.impl.ProfileServiceImpl;

public class Main {

	public static void main(String[] args) {
		CreatorFrame frame = new CreatorFrame();
		frame.setVisible(true);
		System.out.println("Projekt erfolgreich eingerichtet");
		CreatorImpl impl = new CreatorImpl(null, null);
		// impl.importImage("C:/Users/ninoo/Downloads/WhatsApp Image 2024-06-11 at
		// 15.56.43.jpeg", null);
		Settings settings_0 = new Settings(2.0f, 100);
		Profile profile_0 = new Profile(2, "Profile 2", settings_0);
		Profile profile_3 = new Profile(3, "Profile 3", settings_0);

		
		Profile importedProfile = impl.loadProfile(3);
		
		System.out.println("Profile ID: " + importedProfile.getId());
		System.out.println("Profile Name: " + importedProfile.getName());
		
		try {
			impl.saveProfile(profile_3);
			System.out.println("File saved successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		ProfileService profileService = new ProfileServiceImpl();

		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Profile Manager!");

		// Benutzer gibt Profilinformationen ein
		System.out.print("Enter Profile ID: ");
		int profileID = scanner.nextInt();
		scanner.nextLine(); // Consume newline

		System.out.print("Enter Profile Name: ");
		String profileName = scanner.nextLine();

		// Erstellen von Settings und Profile
		Settings settings = new Settings(2.0f, 10);
		Profile profile = new Profile(profileID, profileName, settings);
		
		
		List<Profile> profiles = profileService.loadProfiles();
		System.out.println("Loaded Profiles:");
		for (Profile p : profiles) {
			System.out.println("Profile ID: " + p.getId());
			System.out.println("Profile Name: " + p.getName());
			System.out.println();
		}
		
		profiles.add(profile_0);
		try {
			profileService.saveProfiles(profiles);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
	}

}
