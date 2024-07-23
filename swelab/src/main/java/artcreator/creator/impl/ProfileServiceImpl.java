package artcreator.creator.impl;

import artcreator.domain.DomainFacade;
import artcreator.creator.impl.Profile;
import artcreator.creator.port.ProfileService;

import java.io.IOException;
import java.util.List;

public class ProfileServiceImpl implements ProfileService {
    private DomainFacade domainFacade;

    public ProfileServiceImpl() {
        this.domainFacade = new DomainFacade(); // Idealerweise Dependency Injection verwenden
    }

    @Override
    public void saveProfiles(List<Profile> profiles) throws IOException {
        domainFacade.saveProfiles(profiles);
    }

    @Override
    public List<Profile> loadProfiles() {
        return domainFacade.loadProfiles();
    }
}
