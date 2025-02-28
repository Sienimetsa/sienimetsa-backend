package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Appuser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long u_id;

    @Column(name = "username", unique = true)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "passwordhash")
    @NotBlank(message = "Password is mandatory")
    private String passwordHash;

    @Column(name = "phone", unique = true)
    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "country")
    @NotBlank(message = "Country is mandatory")
    private String country;

    @Column(name = "chat_color")
    @NotBlank(message = "Chat color is mandatory")
    private String chatColor;

    @Column(name = "profile_picture")
    @NotBlank(message = "Profile picture is mandatory")
    private String profilePicture;

    @Column(name = "level")
    @Min(value = 1, message = "Level needs to be at least 1")
    private int level;

    public Appuser() {
    }

    public Appuser( String username, String passwordHash, String phone, String email, String country, String chatColor, String profilePicture, int level) {
  
        this.username = username;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.chatColor =chatColor;
        this.profilePicture =profilePicture;
        this.level = level;
    }

    // Getters and setters
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

    public String getChatColor() {
        return chatColor;
    }

    public void setChatColor(String chatColor) {
        this.chatColor = chatColor;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Appuser{" +
                "u_id=" + u_id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", chatColor='" + chatColor + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", level=" + level +
                '}';
    }


}
