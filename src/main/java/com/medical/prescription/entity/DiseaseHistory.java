package com.medical.prescription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "disease_histories")
public class DiseaseHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Patient is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @NotBlank(message = "Disease name is required")
    @Column(name = "disease_name", nullable = false)
    private String diseaseName;
    
    @Column(name = "description", length = 1000)
    private String description;
    
    @Column(name = "diagnosis_date")
    private LocalDateTime diagnosisDate;
    
    @Column(name = "treatment_given", length = 1000)
    private String treatmentGiven;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private DiseaseStatus status;
    
    @Column(name = "severity")
    @Enumerated(EnumType.STRING)
    private Severity severity;
    
    @Column(name = "recovery_date")
    private LocalDateTime recoveryDate;
    
    @Column(name = "notes", length = 1000)
    private String notes;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public DiseaseHistory() {
        this.createdAt = LocalDateTime.now();
    }
    
    public DiseaseHistory(Patient patient, String diseaseName, String description, LocalDateTime diagnosisDate) {
        this();
        this.patient = patient;
        this.diseaseName = diseaseName;
        this.description = description;
        this.diagnosisDate = diagnosisDate;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    public String getDiseaseName() {
        return diseaseName;
    }
    
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getDiagnosisDate() {
        return diagnosisDate;
    }
    
    public void setDiagnosisDate(LocalDateTime diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }
    
    public String getTreatmentGiven() {
        return treatmentGiven;
    }
    
    public void setTreatmentGiven(String treatmentGiven) {
        this.treatmentGiven = treatmentGiven;
    }
    
    public DiseaseStatus getStatus() {
        return status;
    }
    
    public void setStatus(DiseaseStatus status) {
        this.status = status;
    }
    
    public Severity getSeverity() {
        return severity;
    }
    
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
    
    public LocalDateTime getRecoveryDate() {
        return recoveryDate;
    }
    
    public void setRecoveryDate(LocalDateTime recoveryDate) {
        this.recoveryDate = recoveryDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Enums
    public enum DiseaseStatus {
        ACTIVE, RECOVERED, CHRONIC, UNDER_TREATMENT
    }
    
    public enum Severity {
        MILD, MODERATE, SEVERE, CRITICAL
    }
}


