package com.medical.prescription.repository;

import com.medical.prescription.entity.DiseaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiseaseHistoryRepository extends JpaRepository<DiseaseHistory, Long> {
    
    List<DiseaseHistory> findByPatientIdOrderByDiagnosisDateDesc(Long patientId);
    
    @Query("SELECT d FROM DiseaseHistory d WHERE d.patient.id = :patientId AND d.status = :status ORDER BY d.diagnosisDate DESC")
    List<DiseaseHistory> findByPatientIdAndStatusOrderByDiagnosisDateDesc(@Param("patientId") Long patientId, @Param("status") DiseaseHistory.DiseaseStatus status);
    
    @Query("SELECT d FROM DiseaseHistory d WHERE LOWER(d.diseaseName) LIKE LOWER(CONCAT('%', :diseaseName, '%')) ORDER BY d.diagnosisDate DESC")
    List<DiseaseHistory> findByDiseaseNameContainingIgnoreCaseOrderByDiagnosisDateDesc(@Param("diseaseName") String diseaseName);
    
    @Query("SELECT d FROM DiseaseHistory d WHERE d.diagnosisDate BETWEEN :startDate AND :endDate ORDER BY d.diagnosisDate DESC")
    List<DiseaseHistory> findByDiagnosisDateBetweenOrderByDiagnosisDateDesc(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT d FROM DiseaseHistory d WHERE d.patient.id = :patientId AND d.diagnosisDate BETWEEN :startDate AND :endDate ORDER BY d.diagnosisDate DESC")
    List<DiseaseHistory> findByPatientIdAndDiagnosisDateBetweenOrderByDiagnosisDateDesc(@Param("patientId") Long patientId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(d) FROM DiseaseHistory d WHERE d.patient.id = :patientId")
    Long countByPatientId(@Param("patientId") Long patientId);
    
    @Query("SELECT COUNT(d) FROM DiseaseHistory d WHERE d.patient.id = :patientId AND d.status = :status")
    Long countByPatientIdAndStatus(@Param("patientId") Long patientId, @Param("status") DiseaseHistory.DiseaseStatus status);
}


