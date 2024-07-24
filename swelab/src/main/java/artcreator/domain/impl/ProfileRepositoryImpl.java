package artcreator.domain.impl;

import artcreator.creator.impl.Profile;
import artcreator.domain.port.ProfileRepository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileRepositoryImpl implements ProfileRepository {
	private static final String FILE_PATH = "/Users/martinmergili/Documents/SWELab/profiles.json";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void saveProfiles(List<Profile> profiles) throws IOException {
		for(int i = 0; i<profiles.size()-1; i++) {
   			System.out.println(profiles.get(i).settings.getColorPalette());
   		}
		objectMapper.writeValue(new File(FILE_PATH), profiles);
	}

	@Override
	public List<Profile> loadProfiles() {
		List<Profile> profiles;

		try {
			// Try to read profiles from the file
			profiles = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Profile>>() {
			});
		} catch (IOException e) {

			// If an IOException occurs, create a new file and initialize an empty list
			System.out.println("IOException occurred: " + e.getMessage());
			System.out.println("Creating new file: " + FILE_PATH);

			File file = new File(FILE_PATH);
            if (!file.exists()) {
                try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
            // Initialize an empty list as the profile list
            profiles = new ArrayList<>();
            
            // Write an empty list to the file to initialize it
            try {
				objectMapper.writeValue(file, profiles);
			} catch (JsonGenerationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return profiles;
	}
}
