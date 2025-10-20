package com.medical.prescription.service.impl;

import com.medical.prescription.entity.DiseaseHistory;
import com.medical.prescription.repository.DiseaseHistoryRepository;
import com.medical.prescription.service.DiseaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DiseaseHistoryServiceImpl implements DiseaseHistoryService {
    
    @Autowired
    private DiseaseHistoryRepository diseaseHistoryRepository;
    
    @Override
    public List<DiseaseHistory> getAllDiseaseHistories() {
        return diseaseHistoryRepository.findAll();
    }
    
    @Override
    public Optional<DiseaseHistory> getDiseaseHistoryById(Long id) {
        return diseaseHistoryRepository.findById(id);
    }
    
    @Override
    public DiseaseHistory saveDiseaseHistory(DiseaseHistory diseaseHistory) {
        return diseaseHistoryRepository.save(diseaseHistory);
    }
    
    @Override
    public DiseaseHistory updateDiseaseHistory(Long id, DiseaseHistory diseaseHistory) {
        if (diseaseHistoryRepository.existsById(id)) {
            diseaseHistory.setId(id);
            return diseaseHistoryRepository.save(diseaseHistory);
        }
        throw new RuntimeException("Disease history not found with id: " + id);
    }
    
    @Override
    public void deleteDiseaseHistory(Long id) {
        if (diseaseHistoryRepository.existsById(id)) {
            diseaseHistoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Disease history not found with id: " + id);
        }
    }
    
    @Override
    public List<DiseaseHistory> getDiseaseHistoriesByPatient(Long patientId) {
        return diseaseHistoryRepository.findByPatientIdOrderByDiagnosisDateDesc(patientId);
    }
    
    @Override
    public List<DiseaseHistory> getDiseaseHistoriesByPatientAndStatus(Long patientId, DiseaseHistory.DiseaseStatus status) {
        return diseaseHistoryRepository.findByPatientIdAndStatusOrderByDiagnosisDateDesc(patientId, status);
    }
    
    @Override
    public List<DiseaseHistory> searchDiseaseHistoriesByDiseaseName(String diseaseName) {
        return diseaseHistoryRepository.findByDiseaseNameContainingIgnoreCaseOrderByDiagnosisDateDesc(diseaseName);
    }
    
    @Override
    public List<DiseaseHistory> getDiseaseHistoriesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return diseaseHistoryRepository.findByDiagnosisDateBetweenOrderByDiagnosisDateDesc(startDate, endDate);
    }
    
    @Override
    public List<DiseaseHistory> getDiseaseHistoriesByPatientAndDateRange(Long patientId, LocalDateTime startDate, LocalDateTime endDate) {
        return diseaseHistoryRepository.findByPatientIdAndDiagnosisDateBetweenOrderByDiagnosisDateDesc(patientId, startDate, endDate);
    }
    
    @Override
    public Long getDiseaseHistoryCountByPatient(Long patientId) {
        return diseaseHistoryRepository.countByPatientId(patientId);
    }
    
    @Override
    public Long getDiseaseHistoryCountByPatientAndStatus(Long patientId, DiseaseHistory.DiseaseStatus status) {
        return diseaseHistoryRepository.countByPatientIdAndStatus(patientId, status);
    }
}


