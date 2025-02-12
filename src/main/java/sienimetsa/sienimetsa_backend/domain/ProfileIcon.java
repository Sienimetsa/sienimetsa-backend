package sienimetsa.sienimetsa_backend.domain;

public enum ProfileIcon {
    KONSTA("assets/profileicons/Konsta.png");

    private final String path;

    ProfileIcon(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
