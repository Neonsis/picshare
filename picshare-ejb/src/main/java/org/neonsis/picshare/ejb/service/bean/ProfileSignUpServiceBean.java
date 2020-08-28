package org.neonsis.picshare.ejb.service.bean;

import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.model.domain.Profile;
import org.neonsis.picshare.service.ProfileService;
import org.neonsis.picshare.service.ProfileSignUpService;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MINUTES;

@Stateful
@StatefulTimeout(value = 30, unit = MINUTES)
public class ProfileSignUpServiceBean implements ProfileSignUpService, Serializable {

    @Inject
    private transient Logger logger;

    @Inject
    private transient ProfileService profileService;

    private Profile profile;

    @Override
    public void createSignUpProfile(Profile profile) {
        this.profile = profile;
        profileService.translitSocialProfile(profile);
    }

    @Override
    public Profile getCurrentProfile() throws ObjectNotFoundException {
        if (profile == null) {
            throw new ObjectNotFoundException("Profile not found. Please create profile before use");
        }
        return profile;
    }

    @Override
    @Remove
    public void completeSignUp() {
        profileService.signUp(profile, false);
    }

    @Override
    @Remove
    public void cancel() {
        profile = null;
    }
}
