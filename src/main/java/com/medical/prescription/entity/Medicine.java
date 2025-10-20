package com.medical.prescription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
@Table(name = "medicines")
public class Medicine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Medicine name is required")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "generic_name")
    private String genericName;
    
    @Column(name = "manufacturer")
    private String manufacturer;
    
    @Column(name = "dosage_form")
    private String dosageForm;
    
    @Column(name = "strength")
    private String strength;
    
    @Column(name = "description", length = 1000)
    private String description;
    
    @Column(name = "side_effects", length = 1000)
    private String sideEffects;
    
    @Column(name = "contraindications", length = 1000)
    private String contraindications;
    
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    @Column(name = "is_prescription_required")
    private Boolean isPrescriptionRequired = true;
    
    // Constructors
    public Medicine() {}
    
    public Medicine(String name, String genericName, String manufacturer, String dosageForm, String strength) {
        this.name = name;
        this.genericName = genericName;
        this.manufacturer = manufacturer;
        this.dosageForm = dosageForm;
        this.strength = strength;
    }
    
    // Getters and Setters
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
    
    public String getGenericName() {
        return genericName;
    }
    
    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }
    
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public String getDosageForm() {
        return dosageForm;
    }
    
    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }
    
    public String getStrength() {
        return strength;
    }
    
    public void setStrength(String strength) {
        this.strength = strength;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getSideEffects() {
        return sideEffects;
    }
    
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
    
    public String getContraindications() {
        return contraindications;
    }
    
    public void setContraindications(String contraindications) {
        this.contraindications = contraindications;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Boolean getIsPrescriptionRequired() {
        return isPrescriptionRequired;
    }
    
    public void setIsPrescriptionRequired(Boolean isPrescriptionRequired) {
        this.isPrescriptionRequired = isPrescriptionRequired;
    }
}


