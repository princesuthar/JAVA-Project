package com.medical.prescription.repository;

import com.medical.prescription.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByEmail(String email);
    
    Optional<Patient> findByPhone(String phone);
    
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT p FROM Patient p WHERE p.dateOfBirth BETWEEN :startDate AND :endDate")
    List<Patient> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT p FROM Patient p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%')) OR p.phone LIKE CONCAT('%', :phone, '%')")
    List<Patient> findByNameOrEmailOrPhoneContaining(@Param("name") String name, @Param("email") String email, @Param("phone") String phone);
    
    @Query("SELECT p FROM Patient p WHERE p.id IN (SELECT DISTINCT pr.patient.id FROM Prescription pr WHERE pr.doctor.id = :doctorId)")
    List<Patient> findPatientsByDoctorId(@Param("doctorId") Long doctorId);
}


