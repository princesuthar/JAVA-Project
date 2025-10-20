package com.medical.prescription.repository;

import com.medical.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    List<Prescription> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
    
    List<Prescription> findByPatientIdOrderByCreatedAtDesc(Long patientId);
    
    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorId AND p.patient.id = :patientId ORDER BY p.createdAt DESC")
    List<Prescription> findByDoctorIdAndPatientIdOrderByCreatedAtDesc(@Param("doctorId") Long doctorId, @Param("patientId") Long patientId);
    
    @Query("SELECT p FROM Prescription p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    List<Prescription> findByCreatedAtBetweenOrderByCreatedAtDesc(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM Prescription p WHERE p.doctor.id = :doctorId AND p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    List<Prescription> findByDoctorIdAndCreatedAtBetweenOrderByCreatedAtDesc(@Param("doctorId") Long doctorId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT p FROM Prescription p WHERE LOWER(p.diagnosis) LIKE LOWER(CONCAT('%', :diagnosis, '%')) ORDER BY p.createdAt DESC")
    List<Prescription> findByDiagnosisContainingIgnoreCaseOrderByCreatedAtDesc(@Param("diagnosis") String diagnosis);
    
    @Query("SELECT COUNT(p) FROM Prescription p WHERE p.doctor.id = :doctorId")
    Long countByDoctorId(@Param("doctorId") Long doctorId);
    
    @Query("SELECT COUNT(p) FROM Prescription p WHERE p.patient.id = :patientId")
    Long countByPatientId(@Param("patientId") Long patientId);
}


