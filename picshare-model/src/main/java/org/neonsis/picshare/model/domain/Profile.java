package org.neonsis.picshare.model.domain;

import javax.persistence.*;

@Entity
@Table(name = "profile", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
        @UniqueConstraint(columnNames = {"uid"})
})
public class Profile extends AbstractDomain {

    @Id
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "uid", unique = true, nullable = false, length = 255, updatable = false)
    private String uid;

    @Basic(optional = false)
    @Column(name = "email", unique = true, nullable = false, length = 100, updatable = false)
    private String email;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @Basic(optional = false)
    @Column(name = "avatar_url", nullable = false, length = 255)
    private String avatarUrl;

    @Basic(optional = false)
    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @Basic(optional = false)
    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Basic(optional = false)
    @Column(name = "photo_count", nullable = false)
    private int photoCount;

    @Basic(optional = false)
    @Column(name = "rating", nullable = false)
    private int rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Transient
    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }
}
