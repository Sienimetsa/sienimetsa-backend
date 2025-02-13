package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Appuser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long u_id;

    @Enumerated(EnumType.STRING)
    private ProfileIcon profileicon;

    @Column(name = "username")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "passwordhash")
    @NotBlank(message = "Password is mandatory")
    private String passwordHash;

    @Column(name = "phone")
    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @Column(name = "email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "country")
    @NotBlank(message = "Country is mandatory")
    private String country;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private AppuserProfile profile;

    public Appuser() {
    }

    public Appuser(ProfileIcon profileIcon, String username, String passwordHash, String phone, String email, String country) {
        this.profileicon = profileIcon;
        this.username = username;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.email = email;
        this.country = country;
    }

    public ProfileIcon getProfileicon() {
        return profileicon;
    }

    public void setProfileicon(ProfileIcon profileicon) {
        this.profileicon = profileicon;
    }

    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AppuserProfile getProfile() {
        return profile;
    }

    public void setProfile(AppuserProfile profile) {
        this.profile = profile;
        profile.setUser(this); 
    }

    @Override
    public String toString() {
        return "Appuser [u_id=" + u_id + ", username=" + username + ", password=" + passwordHash + 
               ", phone=" + phone + ", email=" + email + ", country=" + country + 
               ", profile=" + (profile != null ? profile.toString() : "No Profile") + "]";
    }
}
