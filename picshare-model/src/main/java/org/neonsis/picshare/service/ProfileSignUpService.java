package org.neonsis.picshare.service;

import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.model.domain.Profile;

public interface ProfileSignUpService {

    void createSignUpProfile(Profile profile);

    Profile getCurrentProfile() throws ObjectNotFoundException;

    void completeSignUp();

    void cancel();
}
