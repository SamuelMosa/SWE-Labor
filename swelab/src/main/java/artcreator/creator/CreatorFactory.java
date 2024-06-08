package artcreator.creator;

import artcreator.creator.port.Creator;

public interface CreatorFactory {
	
	CreatorFactory FACTORY = new CreatorFacade();
	Creator creator();

}
