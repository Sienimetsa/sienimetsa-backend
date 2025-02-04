package sienimetsa.sienimetsa_backend.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Finding {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long f_id;

    @ManyToOne
    @JoinColumn(name = "u_id")
    private Appuser appuser;

    @ManyToOne
    @JoinColumn(name = "m_id")
    private Mushroom mushroom;

    @Column(name = "f_time")
    private LocalDateTime f_time;

    @Column(name = "city")
    private String city;

    @Column(name = "notes")
    private String notes;

    public Finding() {
    }

    public Finding(Appuser appuser, Mushroom mushroom, LocalDateTime f_time, String city, String notes) {
        this.appuser = appuser;
        this.mushroom = mushroom;
        this.f_time = f_time;
        this.city = city;
        this.notes = notes;
    }

    public Long getF_Id() {
        return f_id;
    }

    public void setF_Id(Long f_id) {
        this.f_id = f_id;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }

    public Mushroom getMushroom() {
        return mushroom;
    }

    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    public LocalDateTime getF_time() {
        return f_time;
    }

    public void setF_time(LocalDateTime f_time) {
        this.f_time = f_time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Finding [f_id=" + f_id + ", appuser=" + appuser + ", mushroom=" + mushroom + ", f_time=" + f_time + ", city=" + city
                + ", notes=" + notes + "]";
    }

    
}
