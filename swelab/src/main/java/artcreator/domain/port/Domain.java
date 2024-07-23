package artcreator.domain.port;

import java.io.IOException;
import java.util.List;

import artcreator.creator.impl.Profile;

/* Factory for creating domain objects */

public interface Domain {
	
	void saveProfiles(List<Profile> profiles) throws IOException;

	List<Profile> loadProfiles() throws IOException;

	/* Factory methods */
	Object mkObject();

}
