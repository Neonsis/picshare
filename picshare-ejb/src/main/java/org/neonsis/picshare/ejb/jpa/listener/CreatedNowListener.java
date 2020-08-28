package org.neonsis.picshare.ejb.jpa.listener;

import org.neonsis.picshare.model.domain.AbstractDomain;

import javax.persistence.PrePersist;
import java.util.Date;

public class CreatedNowListener {

    @PrePersist
    public void setNow(AbstractDomain model) {
        model.setCreated(new Date(System.currentTimeMillis() - 1));
    }
}
