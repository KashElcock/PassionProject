package kash.passionproject.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import kash.passionproject.domain.enumeration.BrewMethod;
import kash.passionproject.domain.enumeration.CoffeeRegion;
import kash.passionproject.domain.enumeration.CoffeeSubregion;
import kash.passionproject.domain.enumeration.CoffeeType;
import kash.passionproject.domain.enumeration.FlavorProfile;
import kash.passionproject.domain.enumeration.ProcessingMethod;
import kash.passionproject.domain.enumeration.RoastLevel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BrewCard.
 */
@Entity
@Table(name = "brew_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BrewCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "brew_method")
    private BrewMethod brewMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_type")
    private CoffeeType coffeeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_region")
    private CoffeeRegion coffeeRegion;

    @Enumerated(EnumType.STRING)
    @Column(name = "coffee_subregion")
    private CoffeeSubregion coffeeSubregion;

    @Enumerated(EnumType.STRING)
    @Column(name = "roast_level")
    private RoastLevel roastLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "processing_method")
    private ProcessingMethod processingMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "flavor_profile")
    private FlavorProfile flavorProfile;

    @DecimalMin(value = "0")
    @Column(name = "coffee_weight")
    private Double coffeeWeight;

    @DecimalMin(value = "0")
    @Column(name = "water_weight")
    private Double waterWeight;

    @DecimalMin(value = "0")
    @DecimalMax(value = "200")
    @Column(name = "water_temp")
    private Double waterTemp;

    @Min(value = 0)
    @Column(name = "brew_time")
    private Integer brewTime;

    @Column(name = "brew_date")
    private LocalDate brewDate;

    @Column(name = "equipment")
    private String equipment;

    @Column(name = "notes")
    private String notes;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BrewCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BrewCard name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrewMethod getBrewMethod() {
        return this.brewMethod;
    }

    public BrewCard brewMethod(BrewMethod brewMethod) {
        this.setBrewMethod(brewMethod);
        return this;
    }

    public void setBrewMethod(BrewMethod brewMethod) {
        this.brewMethod = brewMethod;
    }

    public CoffeeType getCoffeeType() {
        return this.coffeeType;
    }

    public BrewCard coffeeType(CoffeeType coffeeType) {
        this.setCoffeeType(coffeeType);
        return this;
    }

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
    }

    public CoffeeRegion getCoffeeRegion() {
        return this.coffeeRegion;
    }

    public BrewCard coffeeRegion(CoffeeRegion coffeeRegion) {
        this.setCoffeeRegion(coffeeRegion);
        return this;
    }

    public void setCoffeeRegion(CoffeeRegion coffeeRegion) {
        this.coffeeRegion = coffeeRegion;
    }

    public CoffeeSubregion getCoffeeSubregion() {
        return this.coffeeSubregion;
    }

    public BrewCard coffeeSubregion(CoffeeSubregion coffeeSubregion) {
        this.setCoffeeSubregion(coffeeSubregion);
        return this;
    }

    public void setCoffeeSubregion(CoffeeSubregion coffeeSubregion) {
        this.coffeeSubregion = coffeeSubregion;
    }

    public RoastLevel getRoastLevel() {
        return this.roastLevel;
    }

    public BrewCard roastLevel(RoastLevel roastLevel) {
        this.setRoastLevel(roastLevel);
        return this;
    }

    public void setRoastLevel(RoastLevel roastLevel) {
        this.roastLevel = roastLevel;
    }

    public ProcessingMethod getProcessingMethod() {
        return this.processingMethod;
    }

    public BrewCard processingMethod(ProcessingMethod processingMethod) {
        this.setProcessingMethod(processingMethod);
        return this;
    }

    public void setProcessingMethod(ProcessingMethod processingMethod) {
        this.processingMethod = processingMethod;
    }

    public FlavorProfile getFlavorProfile() {
        return this.flavorProfile;
    }

    public BrewCard flavorProfile(FlavorProfile flavorProfile) {
        this.setFlavorProfile(flavorProfile);
        return this;
    }

    public void setFlavorProfile(FlavorProfile flavorProfile) {
        this.flavorProfile = flavorProfile;
    }

    public Double getCoffeeWeight() {
        return this.coffeeWeight;
    }

    public BrewCard coffeeWeight(Double coffeeWeight) {
        this.setCoffeeWeight(coffeeWeight);
        return this;
    }

    public void setCoffeeWeight(Double coffeeWeight) {
        this.coffeeWeight = coffeeWeight;
    }

    public Double getWaterWeight() {
        return this.waterWeight;
    }

    public BrewCard waterWeight(Double waterWeight) {
        this.setWaterWeight(waterWeight);
        return this;
    }

    public void setWaterWeight(Double waterWeight) {
        this.waterWeight = waterWeight;
    }

    public Double getWaterTemp() {
        return this.waterTemp;
    }

    public BrewCard waterTemp(Double waterTemp) {
        this.setWaterTemp(waterTemp);
        return this;
    }

    public void setWaterTemp(Double waterTemp) {
        this.waterTemp = waterTemp;
    }

    public Integer getBrewTime() {
        return this.brewTime;
    }

    public BrewCard brewTime(Integer brewTime) {
        this.setBrewTime(brewTime);
        return this;
    }

    public void setBrewTime(Integer brewTime) {
        this.brewTime = brewTime;
    }

    public LocalDate getBrewDate() {
        return this.brewDate;
    }

    public BrewCard brewDate(LocalDate brewDate) {
        this.setBrewDate(brewDate);
        return this;
    }

    public void setBrewDate(LocalDate brewDate) {
        this.brewDate = brewDate;
    }

    public String getEquipment() {
        return this.equipment;
    }

    public BrewCard equipment(String equipment) {
        this.setEquipment(equipment);
        return this;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getNotes() {
        return this.notes;
    }

    public BrewCard notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getAttachment() {
        return this.attachment;
    }

    public BrewCard attachment(byte[] attachment) {
        this.setAttachment(attachment);
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return this.attachmentContentType;
    }

    public BrewCard attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BrewCard user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrewCard)) {
            return false;
        }
        return id != null && id.equals(((BrewCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BrewCard{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brewMethod='" + getBrewMethod() + "'" +
            ", coffeeType='" + getCoffeeType() + "'" +
            ", coffeeRegion='" + getCoffeeRegion() + "'" +
            ", coffeeSubregion='" + getCoffeeSubregion() + "'" +
            ", roastLevel='" + getRoastLevel() + "'" +
            ", processingMethod='" + getProcessingMethod() + "'" +
            ", flavorProfile='" + getFlavorProfile() + "'" +
            ", coffeeWeight=" + getCoffeeWeight() +
            ", waterWeight=" + getWaterWeight() +
            ", waterTemp=" + getWaterTemp() +
            ", brewTime=" + getBrewTime() +
            ", brewDate='" + getBrewDate() + "'" +
            ", equipment='" + getEquipment() + "'" +
            ", notes='" + getNotes() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            "}";
    }
}
