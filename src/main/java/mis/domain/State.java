package mis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A State.
 */
@Entity
@Table(name = "state")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "regional_name", nullable = false)
    private String regionalName;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "createdate")
    private LocalDate createdate;

    @Column(name = "createby")
    private Long createby;

    @Column(name = "updatedate")
    private LocalDate updatedate;

    @Column(name = "updateby")
    private Long updateby;

    @ManyToOne(optional = false)
    @NotNull
    private Country country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public State name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionalName() {
        return regionalName;
    }

    public State regionalName(String regionalName) {
        this.regionalName = regionalName;
        return this;
    }

    public void setRegionalName(String regionalName) {
        this.regionalName = regionalName;
    }

    public String getLat() {
        return lat;
    }

    public State lat(String lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public State lon(String lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public State createdate(LocalDate createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public Long getCreateby() {
        return createby;
    }

    public State createby(Long createby) {
        this.createby = createby;
        return this;
    }

    public void setCreateby(Long createby) {
        this.createby = createby;
    }

    public LocalDate getUpdatedate() {
        return updatedate;
    }

    public State updatedate(LocalDate updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    public void setUpdatedate(LocalDate updatedate) {
        this.updatedate = updatedate;
    }

    public Long getUpdateby() {
        return updateby;
    }

    public State updateby(Long updateby) {
        this.updateby = updateby;
        return this;
    }

    public void setUpdateby(Long updateby) {
        this.updateby = updateby;
    }

    public Country getCountry() {
        return country;
    }

    public State country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        if (state.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), state.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", regionalName='" + getRegionalName() + "'" +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", createby=" + getCreateby() +
            ", updatedate='" + getUpdatedate() + "'" +
            ", updateby=" + getUpdateby() +
            "}";
    }
}
