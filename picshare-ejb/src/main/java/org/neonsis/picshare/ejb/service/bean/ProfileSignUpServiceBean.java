package org.neonsis.picshare.ejb.service.bean;

import org.neonsis.picshare.common.model.URLImageResource;
import org.neonsis.picshare.exception.ObjectNotFoundException;
import org.neonsis.picshare.model.AsyncOperation;
import org.neonsis.picshare.model.domain.Profile;
import org.neonsis.picshare.service.ProfileService;
import org.neonsis.picshare.service.ProfileSignUpService;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.neonsis.picshare.common.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;

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
        if (profile.getAvatarUrl() != null) {
            profileService.uploadNewAvatar(profile, new URLImageResource(profile.getAvatarUrl()), new AsyncOperation<Profile>() {

                @Override
                public void onSuccess(Profile result) {
                    logger.log(Level.INFO, "Profile avatar successful saved to {0}", result.getAvatarUrl());
                }

                @Override
                public void onFailed(Throwable throwable) {
                    logger.log(Level.SEVERE, "Profile avatar can't saved: " + throwable.getMessage(), throwable);
                }

                @Override
                public long getTimeOutInMillis() {
                    return DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
                }
            });
        }
    }

    @Override
    @Remove
    public void cancel() {
        profile = null;
    }
}
