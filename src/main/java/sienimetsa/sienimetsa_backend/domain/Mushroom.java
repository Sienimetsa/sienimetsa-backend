package sienimetsa.sienimetsa_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Mushroom {

    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long m_id;

    @Column(name = "mushroompic")
    private Integer mushroompic;

    @NotBlank(message = "Mushroom name cannot be blank")
    @Column(name = "mname", nullable = false)
    private String mname;


    @NotBlank(message = "Common mushroom name cannot be blank")
    @Column(name = "cmname", nullable = false)
    private String cmname;

    @NotBlank(message = "Toxicity level cannot be blank")
    @Column(name = "toxicity_level", nullable = false)
    private String toxicity_level;

    @NotBlank(message = "Color cannot be blank")
    @Column(name = "color", nullable = false)
    private String color;

    @NotBlank(message = "Gills information cannot be blank")
    @Column(name = "gills", nullable = false)
    private String gills;

    @NotBlank(message = "Cap information cannot be blank")
    @Column(name = "cap", nullable = false)
    private String cap;

    @NotBlank(message = "Taste description cannot be blank")
    @Column(name = "taste", nullable = false)
    private String taste;

    @Column(name = "description")
    private String description;

    public Mushroom() {
    }

    public Mushroom(Integer mushroompic, String mname, String cmname, String toxicity_level, String color, String gills, String cap,
            String taste, String description) {
        this.mushroompic = mushroompic;
        this.mname = mname;
        this.cmname = cmname;
        this.toxicity_level = toxicity_level;
        this.color = color;
        this.gills = gills;
        this.cap = cap;
        this.taste = taste;
        this.description = description;
    }

    public Long getM_id() {
        return m_id;
    }

    public void setM_id(Long m_id) {
        this.m_id = m_id;
    }

    public Integer getMushroompic() {
        return mushroompic;
    }

    public void setMushroompic(Integer mushroompic) {
        this.mushroompic = mushroompic;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getCmname() {
        return cmname;
    }

    public void setCmname(String cmname) {
        this.cmname = cmname;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Mushroom [m_id=" + m_id + ", mushroompic=" + mushroompic + ", mname=" + mname+", cmname=" + cmname + ", toxicity_level="
                + toxicity_level + ", color=" + color + ", gills=" + gills + ", cap=" + cap + ", taste=" + taste
                + ", description=" + description + "]";
    }

    

}
