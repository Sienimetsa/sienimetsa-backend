package sienimetsa.sienimetsa_backend.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Appuser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long u_id;


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

    @Column(name = "chat_color")
    @NotBlank(message = "Chat color is mandatory")
    private String chatColor;

    @Column(name = "profile_picture")
    @NotBlank(message = "Profile picture is mandatory")
    private String profilePicture;


    public Appuser() {
    }

    public Appuser( String username, String passwordHash, String phone, String email, String country, String chatColor,String profilePicture) {
  
        this.username = username;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.email = email;
        this.country = country;
        this.chatColor =chatColor;
        this.profilePicture =profilePicture;
    }

  
    // Getters and setters
    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

  
    @Override
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

    // Overriding methods from UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // If roles are implemented, you can return them here
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // You can implement account expiry logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // You can implement account locking logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // You can implement credentials expiry logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // You can implement account enable/disable logic if needed
    }

    @Override
    public String toString() {
        return "Appuser [u_id=" + u_id + ", username=" + username + ", password=" + passwordHash + 
               ", phone=" + phone + ", email=" + email + ", country=" + country + 
                "]";
    }
}
