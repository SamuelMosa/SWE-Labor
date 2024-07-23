package artcreator.creator.impl;

import artcreator.creator.impl.Settings;

public class Profile {
	private int id;
	private String name;
	private Settings settings;
	
	public Profile() {}
	
	public Profile(int profileID, String profileName, Settings profileSettings) {
		super();
		this.id = profileID;
		this.name = profileName;
		this.settings = profileSettings;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
}
