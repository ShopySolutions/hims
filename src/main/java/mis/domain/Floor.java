package mis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Floor.
 */
@Entity
@Table(name = "floor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "floor_no", nullable = false)
    private String floorNo;

    @NotNull
    @Column(name = "total_flat", nullable = false)
    private String totalFlat;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "createdate")
    private LocalDate createdate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne(optional = false)
    private Profile profile;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    private House house;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public Floor floorNo(String floorNo) {
        this.floorNo = floorNo;
        return this;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getTotalFlat() {
        return totalFlat;
    }

    public Floor totalFlat(String totalFlat) {
        this.totalFlat = totalFlat;
        return this;
    }

    public void setTotalFlat(String totalFlat) {
        this.totalFlat = totalFlat;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public Floor createdate(LocalDate createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public Floor createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Floor updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public Floor updateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Profile getProfile() {
        return profile;
    }

    public Floor profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public Floor user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public House getHouse() {
        return house;
    }

    public Floor house(House house) {
        this.house = house;
        return this;
    }

    public void setHouse(House house) {
        this.house = house;
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
        Floor floor = (Floor) o;
        if (floor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), floor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Floor{" +
            "id=" + getId() +
            ", floorNo='" + getFloorNo() + "'" +
            ", totalFlat='" + getTotalFlat() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
