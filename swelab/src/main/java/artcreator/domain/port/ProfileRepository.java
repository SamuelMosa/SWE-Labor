package artcreator.domain.port;

import artcreator.creator.impl.Profile;
import java.io.IOException;
import java.util.List;

public interface ProfileRepository {
    void saveProfiles(List<Profile> profiles) throws IOException;
    List<Profile> loadProfiles();
}
