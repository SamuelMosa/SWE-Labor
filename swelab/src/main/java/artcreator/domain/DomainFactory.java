package artcreator.domain;

import artcreator.domain.port.Domain;

public interface DomainFactory {

	DomainFactory FACTORY = new DomainFacade();
	
	Domain domain();
}
