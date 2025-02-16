package sienimetsa.sienimetsa_backend.dto;
public class MobileProfileUpdateDTO {
    private String username;
    private String password;
    private String profilePicture;
    private String chatColor;

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public String getChatColor() { return chatColor; }
    public void setChatColor(String chatColor) { this.chatColor = chatColor; }
}
