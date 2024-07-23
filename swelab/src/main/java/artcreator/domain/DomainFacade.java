package artcreator.domain;

import java.io.IOException;
import java.util.List;

import artcreator.creator.impl.Profile;
import artcreator.domain.impl.DomainImpl;
import artcreator.domain.port.Domain;

import artcreator.domain.port.ProfileRepository;
import artcreator.domain.impl.ProfileRepositoryImpl;

public class DomainFacade implements DomainFactory, Domain{

    private ProfileRepository profileRepository;

	private DomainImpl domain = new DomainImpl();
	
	public DomainFacade() {
        this.profileRepository = new ProfileRepositoryImpl(); // Idealerweise Dependency Injection verwenden
    }
	
	
	@Override
	public synchronized Domain domain() {
		if (this.domain == null)
				this.domain = new DomainImpl();
			return this;
		}

	
	@Override
	public synchronized Object mkObject() {
		return this.domain.mkObject();
	}
 

	@Override
	public void saveProfiles(List<Profile> profiles) throws IOException {
        profileRepository.saveProfiles(profiles);   
		
	}


	@Override
	public List<Profile> loadProfiles() {
        return profileRepository.loadProfiles();
	}




}
