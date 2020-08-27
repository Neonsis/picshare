package org.neonsis.picshare.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "photo", schema = "public")
public class Photo extends AbstractDomain {

    @Id
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "small_url", nullable = false, length = 255, updatable = false)
    private String smallUrl;

    @Basic(optional = false)
    @Column(name = "large_url", nullable = false, length = 255, updatable = false)
    private String largeUrl;

    @Basic(optional = false)
    @Column(name = "original_url", nullable = false, length = 255, updatable = false)
    private String originalUrl;

    @Basic(optional = false)
    @Column(nullable = false)
    private long views;

    @Basic(optional = false)
    @Column(nullable = false)
    private long downloads;

    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
