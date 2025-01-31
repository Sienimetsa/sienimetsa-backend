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
    private Long u_Id;

    @Column(name = "username")
    private String username;

    @Column(name = "passwordhash")
    private String passwordhash;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "country")
    private String country;

    public Appuser() {
    }

    public Appuser(String username, String passwordhash, String phone, String email, String country) {
        this.username = username;
        this.passwordhash = passwordhash;
        this.phone = phone;
        this.email = email;
        this.country = country;
    }

    public Long getU_Id() {
        return u_Id;
    }

    public void setU_Id(Long u_Id) {
        this.u_Id = u_Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordhash;
    }

    public void setPassword(String passwordhash) {
        this.passwordhash = passwordhash;
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
        return "Appuser [u_Id=" + u_Id + ", username=" + username + ", password=" + passwordhash + ", phone=" + phone
                + ", email=" + email + ", country=" + country + "]";
    }
}
