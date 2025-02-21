package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class Mushroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long m_id;

    @Column(name = "mushroompic")
    private Long mushroompic = 1L;

    @Column(name = "mname")
    private String mname;

    @Column(name = "toxicity_level")
    private String toxicity_level;

    @Column(name = "color")
    private String color;

    @Column(name = "gills")
    private String gills;

    @Column(name = "cap")
    private String cap;

    @Column(name = "taste")
    private String taste;

    public Mushroom() {
    }

    public Mushroom(Long mushroompic, String mname, String toxicity_level, String color, String gills, String cap, String taste) {
        this.mname = mname;
        this.toxicity_level = toxicity_level;
        this.color = color;
        this.gills = gills;
        this.cap = cap;
        this.taste = taste;
    }

    @PrePersist
    private void prePersist() {
        this.mushroompic = this.m_id + 1;
    }
    
    public Long getM_id() {
        return m_id;
    }

    public void setM_id(Long m_id) {
        this.m_id = m_id;
    }

    public Long getMushroompic() {
        return mushroompic;
    }

    public void setMushroompic(Long mushroompic) {
        this.mushroompic = mushroompic;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getToxicity_level() {
        return toxicity_level;
    }

    public void setToxicity_level(String toxicity_level) {
        this.toxicity_level = toxicity_level;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGills() {
        return gills;
    }

    public void setGills(String gills) {
        this.gills = gills;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Override
    public String toString() {
        return "Mushroom [m_id=" + m_id + ", mushroompic=" + mushroompic + ", mname=" + mname + ", toxicity_level="
                + toxicity_level + ", color=" + color + ", gills=" + gills + ", cap=" + cap + ", taste=" + taste + "]";
    }

    

}
