package com.medical.prescription.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Doctor name is required")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "Specialization is required")
    @Column(name = "specialization", nullable = false)
    private String specialization;
    
    @NotBlank(message = "License number is required")
    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;
    
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;
    
    @Pattern(regexp = "^[\\+]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[0-9]{1,9}$", message = "Invalid phone number format")
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "qualification")
    private String qualification;
    
    // Constructors
    public Doctor() {}
    
    public Doctor(String name, String specialization, String licenseNumber, String email, String phone, String address, String qualification) {
        this.name = name;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.qualification = qualification;
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
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}


