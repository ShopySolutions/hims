package mis.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Flat.
 */
@Entity
@Table(name = "flat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Flat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "flat_name_or_no", nullable = false)
    private String flatNameOrNo;

    @NotNull
    @Column(name = "flat_size", nullable = false)
    private String flatSize;

    @NotNull
    @Column(name = "total_bed_room", nullable = false)
    private Integer totalBedRoom;

    @NotNull
    @Column(name = "draw_din_dra_cum_din", nullable = false)
    private String drawDinDraCumDin;

    @NotNull
    @Column(name = "total_washroom", nullable = false)
    private Integer totalWashroom;

    @NotNull
    @Column(name = "kitchen", nullable = false)
    private Integer kitchen;

    @NotNull
    @Column(name = "total_belcony", nullable = false)
    private Integer totalBelcony;

    @NotNull
    @Column(name = "store_room", nullable = false)
    private Integer storeRoom;

    @NotNull
    @Column(name = "floor_type", nullable = false)
    private String floorType;

    @NotNull
    @Column(name = "flat_fair", nullable = false)
    private String flatFair;

    @NotNull
    @Column(name = "service_charge", nullable = false)
    private String serviceCharge;

    @NotNull
    @Column(name = "total_fair", nullable = false)
    private String totalFair;

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

    @ManyToOne(optional = false)
    @NotNull
    private Floor floor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlatNameOrNo() {
        return flatNameOrNo;
    }

    public Flat flatNameOrNo(String flatNameOrNo) {
        this.flatNameOrNo = flatNameOrNo;
        return this;
    }

    public void setFlatNameOrNo(String flatNameOrNo) {
        this.flatNameOrNo = flatNameOrNo;
    }

    public String getFlatSize() {
        return flatSize;
    }

    public Flat flatSize(String flatSize) {
        this.flatSize = flatSize;
        return this;
    }

    public void setFlatSize(String flatSize) {
        this.flatSize = flatSize;
    }

    public Integer getTotalBedRoom() {
        return totalBedRoom;
    }

    public Flat totalBedRoom(Integer totalBedRoom) {
        this.totalBedRoom = totalBedRoom;
        return this;
    }

    public void setTotalBedRoom(Integer totalBedRoom) {
        this.totalBedRoom = totalBedRoom;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDrawDinDraCumDin() {
        return drawDinDraCumDin;
    }

    public Flat drawDinDraCumDin(String drawDinDraCumDin) {
        this.drawDinDraCumDin = drawDinDraCumDin;
        return this;
    }

    public void setDrawDinDraCumDin(String drawDinDraCumDin) {
        this.drawDinDraCumDin = drawDinDraCumDin;
    }

    public Integer getTotalWashroom() {
        return totalWashroom;
    }

    public Flat totalWashroom(Integer totalWashroom) {
        this.totalWashroom = totalWashroom;
        return this;
    }

    public void setTotalWashroom(Integer totalWashroom) {
        this.totalWashroom = totalWashroom;
    }

    public Integer getKitchen() {
        return kitchen;
    }

    public Flat kitchen(Integer kitchen) {
        this.kitchen = kitchen;
        return this;
    }

    public void setKitchen(Integer kitchen) {
        this.kitchen = kitchen;
    }

    public Integer getTotalBelcony() {
        return totalBelcony;
    }

    public Flat totalBelcony(Integer totalBelcony) {
        this.totalBelcony = totalBelcony;
        return this;
    }

    public void setTotalBelcony(Integer totalBelcony) {
        this.totalBelcony = totalBelcony;
    }

    public Integer getStoreRoom() {
        return storeRoom;
    }

    public Flat storeRoom(Integer storeRoom) {
        this.storeRoom = storeRoom;
        return this;
    }

    public void setStoreRoom(Integer storeRoom) {
        this.storeRoom = storeRoom;
    }

    public String getFloorType() {
        return floorType;
    }

    public Flat floorType(String floorType) {
        this.floorType = floorType;
        return this;
    }

    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    public String getFlatFair() {
        return flatFair;
    }

    public Flat flatFair(String flatFair) {
        this.flatFair = flatFair;
        return this;
    }

    public void setFlatFair(String flatFair) {
        this.flatFair = flatFair;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public Flat serviceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
        return this;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getTotalFair() {
        return totalFair;
    }

    public Flat totalFair(String totalFair) {
        this.totalFair = totalFair;
        return this;
    }

    public void setTotalFair(String totalFair) {
        this.totalFair = totalFair;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public Flat createdate(LocalDate createdate) {
        this.createdate = createdate;
        return this;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public Flat createBy(Long createBy) {
        this.createBy = createBy;
        return this;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public Flat updateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public Flat updateBy(Long updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Profile getProfile() {
        return profile;
    }

    public Flat profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User getUser() {
        return user;
    }

    public Flat user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public House getHouse() {
        return house;
    }

    public Flat house(House house) {
        this.house = house;
        return this;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Floor getFloor() {
        return floor;
    }

    public Flat floor(Floor floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
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
        Flat flat = (Flat) o;
        if (flat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), flat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Flat{" +
            "id=" + getId() +
            ", flatNameOrNo='" + getFlatNameOrNo() + "'" +
            ", flatSize='" + getFlatSize() + "'" +
            ", totalBedRoom=" + getTotalBedRoom() +
            ", drawDinDraCumDin='" + getDrawDinDraCumDin() + "'" +
            ", totalWashroom=" + getTotalWashroom() +
            ", kitchen=" + getKitchen() +
            ", totalBelcony=" + getTotalBelcony() +
            ", storeRoom=" + getStoreRoom() +
            ", floorType='" + getFloorType() + "'" +
            ", flatFair='" + getFlatFair() + "'" +
            ", serviceCharge='" + getServiceCharge() + "'" +
            ", totalFair='" + getTotalFair() + "'" +
            ", createdate='" + getCreatedate() + "'" +
            ", createBy=" + getCreateBy() +
            ", updateDate='" + getUpdateDate() + "'" +
            ", updateBy=" + getUpdateBy() +
            "}";
    }
}
