package com.medical.prescription.service;

import com.medical.prescription.entity.DiseaseHistory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiseaseHistoryService {
    List<DiseaseHistory> getAllDiseaseHistories();
    Optional<DiseaseHistory> getDiseaseHistoryById(Long id);
    DiseaseHistory saveDiseaseHistory(DiseaseHistory diseaseHistory);
    DiseaseHistory updateDiseaseHistory(Long id, DiseaseHistory diseaseHistory);
    void deleteDiseaseHistory(Long id);
    List<DiseaseHistory> getDiseaseHistoriesByPatient(Long patientId);
    List<DiseaseHistory> getDiseaseHistoriesByPatientAndStatus(Long patientId, DiseaseHistory.DiseaseStatus status);
    List<DiseaseHistory> searchDiseaseHistoriesByDiseaseName(String diseaseName);
    List<DiseaseHistory> getDiseaseHistoriesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<DiseaseHistory> getDiseaseHistoriesByPatientAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate);
    Long getDiseaseHistoryCountByPatient(Long patientId);
    Long getDiseaseHistoryCountByPatientAndStatus(Long patientId, DiseaseHistory.DiseaseStatus status);
}


