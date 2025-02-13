package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class AppuserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "u_id")
    private Appuser user;

    @Column(name = "chat_color")
    private String chatColor;

    @Column(name = "profile_picture")
    private String profilePicture;

    public AppuserProfile() {
    }

    public AppuserProfile(Appuser user, String chatColor, String profilePicture) {
        this.user = user;
        this.chatColor = chatColor;
        this.profilePicture = profilePicture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appuser getUser() {
        return user;
    }

    public void setUser(Appuser user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "AppuserProfile [id=" + id + ", chatColor=" + chatColor + ", profilePicture=" + profilePicture + "]";
    }
}
