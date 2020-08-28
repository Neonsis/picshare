package org.neonsis.picshare.ejb.service.impl;

import org.neonsis.picshare.ejb.model.ProfileUidGenerator;
import org.neonsis.picshare.ejb.service.ProfileUidService;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.neonsis.picshare.ejb.model.ProfileUidGenerator.Category.PRIMARY;

@ApplicationScoped
@ProfileUidGenerator(category = PRIMARY)
public class PrimaryProfileUidService implements ProfileUidService {

    @Override
    public List<String> generateProfileUidCandidates(String englishFirstName, String englishLastName) {
        return Collections.unmodifiableList(Arrays.asList(
                String.format("%s-%s", englishFirstName, englishLastName).toLowerCase(),
                String.format("%s.%s", englishFirstName, englishLastName).toLowerCase(),
                String.format("%s%s", englishFirstName, englishLastName).toLowerCase()
        ));
    }
}
