package com.medical.prescription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "prescription_medicines")
public class PrescriptionMedicine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Prescription is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;
    
    @NotNull(message = "Medicine is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;
    
    @NotBlank(message = "Dosage is required")
    @Column(name = "dosage", nullable = false)
    private String dosage;
    
    @NotBlank(message = "Frequency is required")
    @Column(name = "frequency", nullable = false)
    private String frequency;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "duration")
    private String duration;
    
    @Column(name = "instructions", length = 500)
    private String instructions;
    
    @Column(name = "is_before_meal")
    private Boolean isBeforeMeal = false;
    
    @Column(name = "is_after_meal")
    private Boolean isAfterMeal = false;

    // New: Dosage schedule per time of day
    @Column(name = "morning_dose", length = 10)
    private String morningDose; // e.g., "1", "1/2", "0"

    @Column(name = "afternoon_dose", length = 10)
    private String afternoonDose; // e.g., "1", "1/2", "0"

    @Column(name = "night_dose", length = 10)
    private String nightDose; // e.g., "1", "1/2", "0"
    
    // Constructors
    public PrescriptionMedicine() {}
    
    public PrescriptionMedicine(Prescription prescription, Medicine medicine, String dosage, String frequency, Integer quantity) {
        this.prescription = prescription;
        this.medicine = medicine;
        this.dosage = dosage;
        this.frequency = frequency;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Prescription getPrescription() {
        return prescription;
    }
    
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
    
    public Medicine getMedicine() {
        return medicine;
    }
    
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public Boolean getIsBeforeMeal() {
        return isBeforeMeal;
    }
    
    public void setIsBeforeMeal(Boolean isBeforeMeal) {
        this.isBeforeMeal = isBeforeMeal;
    }
    
    public Boolean getIsAfterMeal() {
        return isAfterMeal;
    }
    
    public void setIsAfterMeal(Boolean isAfterMeal) {
        this.isAfterMeal = isAfterMeal;
    }

    public String getMorningDose() {
        return morningDose;
    }

    public void setMorningDose(String morningDose) {
        this.morningDose = morningDose;
    }

    public String getAfternoonDose() {
        return afternoonDose;
    }

    public void setAfternoonDose(String afternoonDose) {
        this.afternoonDose = afternoonDose;
    }

    public String getNightDose() {
        return nightDose;
    }

    public void setNightDose(String nightDose) {
        this.nightDose = nightDose;
    }
}


