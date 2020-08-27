package org.neonsis.picshare.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "access_token", schema = "public")
public class AccessToken extends AbstractDomain {

    @Id
    @Basic(optional = false)
    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
