package mis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Billing.
 */
@Entity
@Table(name = "billing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "flat_fair", nullable = false)
    private String flatFair;

    @NotNull
    @Column(name = "electricity_bill", nullable = false)
    private String electricityBill;

    @NotNull
    @Column(name = "gas_bill", nullable = false)
    private String gasBill;

    @NotNull
    @Column(name = "internet_bill", nullable = false)
    private String internetBill;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

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

    @ManyToOne(optional = false)
    @NotNull
    private Floor floor;

    @ManyToOne(optional = false)
    @NotNull
    private Flat flat;

    @ManyToOne(optional = false)
    @NotNull
    private Rent rent;

    @Column(name = "status")
    private Boolean status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlatFair() {
        return flatFair;
    }

    public Billing flatFair(String flatFair) {
        this.flatFair = flatFair;
        return this;
    }

    public void setFlatFair(String flatFair) {
        this.flatFair = flatFair;
    }

    public String getElectricityBill() {
        return electricityBill;
    }

    public Billing electricityBill(String electricityBill) {
        this.electricityBill = electricityBill;
        return this;
    }

    public void setElectricityBill(String electricityBill) {
        this.electricityBill = electricityBill;
    }

    public String getGasBill() {
        return gasBill;
    }

    public Billing gasBill(String gasBill) {
        this.gasBill = gasBill;
        return this;
    }

    public void setGasBill(String gasBill) {
        this.gasBill = gasBill;
    }

    public String getInternetBill() {
        return internetBill;
    }

    public Billing internetBill(String internetBill) {
        this.internetBill = internetBill;
        return this;
    }

    public void setInternetBill(String internetBill) {
        this.internetBill = internetBill;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Billing transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public Billing createdate(LocalDate createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public Billing createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Billing updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public Billing updateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Profile getProfile() {
        return profile;
    }

    public Billing profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public Billing user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public House getHouse() {
        return house;
    }

    public Billing house(House house) {
        this.house = house;
        return this;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Floor getFloor() {
        return floor;
    }

    public Billing floor(Floor floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Flat getFlat() {
        return flat;
    }

    public Billing flat(Flat flat) {
        this.flat = flat;
        return this;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public Rent getRent() {
        return rent;
    }

    public Billing rent(Rent rent) {
        this.rent = rent;
        return this;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
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
        Billing billing = (Billing) o;
        if (billing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Billing{" +
            "id=" + getId() +
            ", flatFair='" + getFlatFair() + "'" +
            ", electricityBill='" + getElectricityBill() + "'" +
            ", gasBill='" + getGasBill() + "'" +
            ", internetBill='" + getInternetBill() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
