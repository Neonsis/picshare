package org.neonsis.picshare.model.domain;

import org.neonsis.picshare.model.validation.EnglishLanguage;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "uid", unique = true, nullable = false, length = 255, updatable = false)
    private String uid;

    @Email
    @NotNull
    @Size(max = 100)
    @Basic(optional = false)
    @Column(name = "email", unique = true, nullable = false, length = 100, updatable = false)
    private String email;

    @NotNull
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    @Size(min = 1, max = 60)
    @Basic(optional = false)
    @Column(name = "first_name", nullable = false, length = 60)
    private String firstName;

    @NotNull
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    @Size(min = 1, max = 60)
    @Basic(optional = false)
    @Column(name = "last_name", nullable = false, length = 60)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "avatar_url", nullable = false, length = 255)
    private String avatarUrl;

    @NotNull
    @EnglishLanguage(withNumbers = false, withSpecialSymbols = false)
    @Size(min = 3, max = 100)
    @Basic(optional = false)
    @Column(name = "job_title", nullable = false, length = 100)
    private String jobTitle;

    @NotNull
    @Size(min = 5, max = 100)
    @Basic(optional = false)
    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Min(0)
    @Basic(optional = false)
    @Column(name = "photo_count", nullable = false)
    private int photoCount;

    @Min(0)
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
