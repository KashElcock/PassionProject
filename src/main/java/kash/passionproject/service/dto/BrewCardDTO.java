package kash.passionproject.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import kash.passionproject.domain.enumeration.BrewMethod;
import kash.passionproject.domain.enumeration.CoffeeRegion;
import kash.passionproject.domain.enumeration.CoffeeSubregion;
import kash.passionproject.domain.enumeration.CoffeeType;
import kash.passionproject.domain.enumeration.FlavorProfile;
import kash.passionproject.domain.enumeration.ProcessingMethod;
import kash.passionproject.domain.enumeration.RoastLevel;

/**
 * A DTO for the {@link kash.passionproject.domain.BrewCard} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BrewCardDTO implements Serializable {

    private Long id;

    private String name;

    private BrewMethod brewMethod;

    private CoffeeType coffeeType;

    private CoffeeRegion coffeeRegion;

    private CoffeeSubregion coffeeSubregion;

    private RoastLevel roastLevel;

    private ProcessingMethod processingMethod;

    private FlavorProfile flavorProfile;

    @DecimalMin(value = "0")
    private Double coffeeWeight;

    @DecimalMin(value = "0")
    private Double waterWeight;

    @DecimalMin(value = "0")
    @DecimalMax(value = "200")
    private Double waterTemp;

    @Min(value = 0)
    private Integer brewTime;

    private LocalDate brewDate;

    private String equipment;

    private String notes;

    @Lob
    private byte[] attachment;

    private String attachmentContentType;
    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BrewMethod getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(BrewMethod brewMethod) {
        this.brewMethod = brewMethod;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
    }

    public CoffeeRegion getCoffeeRegion() {
        return coffeeRegion;
    }

    public void setCoffeeRegion(CoffeeRegion coffeeRegion) {
        this.coffeeRegion = coffeeRegion;
    }

    public CoffeeSubregion getCoffeeSubregion() {
        return coffeeSubregion;
    }

    public void setCoffeeSubregion(CoffeeSubregion coffeeSubregion) {
        this.coffeeSubregion = coffeeSubregion;
    }

    public RoastLevel getRoastLevel() {
        return roastLevel;
    }

    public void setRoastLevel(RoastLevel roastLevel) {
        this.roastLevel = roastLevel;
    }

    public ProcessingMethod getProcessingMethod() {
        return processingMethod;
    }

    public void setProcessingMethod(ProcessingMethod processingMethod) {
        this.processingMethod = processingMethod;
    }

    public FlavorProfile getFlavorProfile() {
        return flavorProfile;
    }

    public void setFlavorProfile(FlavorProfile flavorProfile) {
        this.flavorProfile = flavorProfile;
    }

    public Double getCoffeeWeight() {
        return coffeeWeight;
    }

    public void setCoffeeWeight(Double coffeeWeight) {
        this.coffeeWeight = coffeeWeight;
    }

    public Double getWaterWeight() {
        return waterWeight;
    }

    public void setWaterWeight(Double waterWeight) {
        this.waterWeight = waterWeight;
    }

    public Double getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Double waterTemp) {
        this.waterTemp = waterTemp;
    }

    public Integer getBrewTime() {
        return brewTime;
    }

    public void setBrewTime(Integer brewTime) {
        this.brewTime = brewTime;
    }

    public LocalDate getBrewDate() {
        return brewDate;
    }

    public void setBrewDate(LocalDate brewDate) {
        this.brewDate = brewDate;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrewCardDTO)) {
            return false;
        }

        BrewCardDTO brewCardDTO = (BrewCardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, brewCardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BrewCardDTO{" +
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
            ", user=" + getUser() +
            "}";
    }
}
