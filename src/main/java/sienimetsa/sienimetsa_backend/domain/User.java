package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "adminUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    
    @Column(name = "aUsername", unique = true)
    @NotBlank(message = "aUsername is needed")
    private String aUsername;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "password is needed")
    private String passwordHash;


    public User(){}

    public User(String aUsername,String passwordHash){
        super();
        this.aUsername= aUsername;
        this.passwordHash=passwordHash;
    
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return aUsername;
    }

    public void setUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

   

}
