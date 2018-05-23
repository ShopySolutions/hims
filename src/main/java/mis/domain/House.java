package mis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A House.
 */
@Entity
@Table(name = "house")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "house_name", nullable = false)
    private String houseName;

    @NotNull
    @Column(name = "house_no", nullable = false)
    private String houseNo;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "house_to_floor_no", nullable = false)
    private Integer houseToFloorNo;

    @NotNull
    @Column(name = "own_to_floor_no", nullable = false)
    private Integer ownToFloorNo;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;

    @Column(name = "createdate")
    private LocalDate createdate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne(optional = false)
    @NotNull
    private Country country;

    @ManyToOne(optional = false)
    @NotNull
    private State state;

    @ManyToOne(optional = false)
    @NotNull
    private City city;

    @ManyToOne(optional = false)
    private Profile profile;

    @ManyToOne(optional = false)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseName() {
        return houseName;
    }

    public House houseName(String houseName) {
        this.houseName = houseName;
        return this;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public House houseNo(String houseNo) {
        this.houseNo = houseNo;
        return this;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getAddress() {
        return address;
    }

    public House address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHouseToFloorNo() {
        return houseToFloorNo;
    }

    public House houseToFloorNo(Integer houseToFloorNo) {
        this.houseToFloorNo = houseToFloorNo;
        return this;
    }

    public void setHouseToFloorNo(Integer houseToFloorNo) {
        this.houseToFloorNo = houseToFloorNo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getOwnToFloorNo() {
        return ownToFloorNo;
    }

    public House ownToFloorNo(Integer ownToFloorNo) {
        this.ownToFloorNo = ownToFloorNo;
        return this;
    }

    public void setOwnToFloorNo(Integer ownToFloorNo) {
        this.ownToFloorNo = ownToFloorNo;
    }

    public String getLat() {
        return lat;
    }

    public House lat(String lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public House lon(String lon) {
        this.lon = lon;
        return this;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public House createdate(LocalDate createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public House createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public House updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public House updateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public byte[] getImage() {
        return image;
    }

    public House image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public House imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Country getCountry() {
        return country;
    }

    public House country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public House state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public House city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Profile getProfile() {
        return profile;
    }

    public House profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public House user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        House house = (House) o;
        if (house.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), house.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "House{" +
            "id=" + getId() +
            ", houseName='" + getHouseName() + "'" +
            ", houseNo='" + getHouseNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", houseToFloorNo=" + getHouseToFloorNo() +
            ", ownToFloorNo=" + getOwnToFloorNo() +
            ", lat='" + getLat() + "'" +
            ", lon='" + getLon() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
