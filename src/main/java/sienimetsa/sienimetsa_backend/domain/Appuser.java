package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Appuser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long u_id;

    @Column(name = "username")
    private String username;

    @Column(name = "passwordhash")
    private String passwordHash;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country;

    public Appuser() {
    }

    public Appuser(String username, String passwordHash, String phone, String email, String country) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.email = email;
        this.country = country;
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

    @Override
    public String toString() {
        return "Appuser [u_id=" + u_id + ", username=" + username + ", password=" + passwordHash + ", phone=" + phone
                + ", email=" + email + ", country=" + country + "]";
    }
}
