package org.neonsis.picshare.rest.model;

import org.neonsis.picshare.common.annotation.converter.ConvertAsURL;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class ProfilePhotoREST {

    private Long id;

    @ConvertAsURL
    private String smallUrl;

    private long views;

    private long downloads;

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
}
