package mis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "mobile_no", nullable = false)
    private String mobileNo;

    @Column(name = "telephone_no")
    private String telephoneNo;

    @Column(name = "status")
    private Boolean status;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "nid_or_passport_no", nullable = false)
    private String nidOrPassportNo;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotNull
    @Column(name = "religion", nullable = false)
    private String religion;

    @Column(name = "createdate")
    private LocalDate createdate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @NotNull
    @Lob
    @Column(name = "profile_image", nullable = false)
    private byte[] profileImage;

    @Column(name = "profile_image_content_type", nullable = false)
    private String profileImageContentType;

    @Lob
    @Column(name = "nid_or_passport")
    private byte[] nidOrPassport;

    @Column(name = "nid_or_passport_content_type")
    private String nidOrPassportContentType;

    @ManyToOne(optional = false)
    @NotNull
    private Country country;

    @ManyToOne(optional = false)
    @NotNull
    private State state;

    @ManyToOne(optional = false)
    @NotNull
    private City city;

    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profile firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Profile lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public Profile mobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public Profile telephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
        return this;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getEmail() {
        return email;
    }

    public Profile email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNidOrPassportNo() {
        return nidOrPassportNo;
    }

    public Profile nidOrPassportNo(String nidOrPassportNo) {
        this.nidOrPassportNo = nidOrPassportNo;
        return this;
    }

    public void setNidOrPassportNo(String nidOrPassportNo) {
        this.nidOrPassportNo = nidOrPassportNo;
    }

    public String getAddress() {
        return address;
    }

    public Profile address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public Profile gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public Profile religion(String religion) {
        this.religion = religion;
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public Profile createdate(LocalDate createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public Profile createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Profile updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public Profile updateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public Profile profileImage(byte[] profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageContentType() {
        return profileImageContentType;
    }

    public Profile profileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
        return this;
    }

    public void setProfileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
    }

    public byte[] getNidOrPassport() {
        return nidOrPassport;
    }

    public Profile nidOrPassport(byte[] nidOrPassport) {
        this.nidOrPassport = nidOrPassport;
        return this;
    }

    public void setNidOrPassport(byte[] nidOrPassport) {
        this.nidOrPassport = nidOrPassport;
    }

    public String getNidOrPassportContentType() {
        return nidOrPassportContentType;
    }

    public Profile nidOrPassportContentType(String nidOrPassportContentType) {
        this.nidOrPassportContentType = nidOrPassportContentType;
        return this;
    }

    public void setNidOrPassportContentType(String nidOrPassportContentType) {
        this.nidOrPassportContentType = nidOrPassportContentType;
    }

    public Country getCountry() {
        return country;
    }

    public Profile country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public State getState() {
        return state;
    }

    public Profile state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public City getCity() {
        return city;
    }

    public Profile city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
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
        Profile profile = (Profile) o;
        if (profile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", telephoneNo='" + getTelephoneNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", nidOrPassportNo='" + getNidOrPassportNo() + "'" +
            ", address='" + getAddress() + "'" +
            ", gender='" + getGender() + "'" +
            ", religion='" + getReligion() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            ", profileImage='" + getProfileImage() + "'" +
            ", profileImageContentType='" + getProfileImageContentType() + "'" +
            ", nidOrPassport='" + getNidOrPassport() + "'" +
            ", nidOrPassportContentType='" + getNidOrPassportContentType() + "'" +
            "}";
    }
}
