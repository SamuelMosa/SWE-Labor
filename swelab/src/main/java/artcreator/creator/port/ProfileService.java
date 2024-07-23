package artcreator.creator.port;

import artcreator.creator.impl.Profile;
import java.io.IOException;
import java.util.List;

public interface ProfileService {
    void saveProfiles(List<Profile> profiles) throws IOException;
    List<Profile> loadProfiles();
}
