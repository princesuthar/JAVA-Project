package com.medical.prescription.service.impl;

import com.medical.prescription.entity.Prescription;
import com.medical.prescription.repository.PrescriptionRepository;
import com.medical.prescription.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
    
    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }
    
    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
    
    @Override
    public Prescription updatePrescription(Long id, Prescription prescription) {
        if (prescriptionRepository.existsById(id)) {
            prescription.setId(id);
            return prescriptionRepository.save(prescription);
        }
        throw new RuntimeException("Prescription not found with id: " + id);
    }
    
    @Override
    public void deletePrescription(Long id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Prescription not found with id: " + id);
        }
    }
    
    @Override
    public List<Prescription> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorIdOrderByCreatedAtDesc(doctorId);
    }
    
    @Override
    public List<Prescription> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }
    
    @Override
    public List<Prescription> getPrescriptionsByDoctorAndPatient(Long doctorId, Long patientId) {
        return prescriptionRepository.findByDoctorIdAndPatientIdOrderByCreatedAtDesc(doctorId, patientId);
    }
    
    @Override
    public List<Prescription> getPrescriptionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return prescriptionRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate);
    }
    
    @Override
    public List<Prescription> getPrescriptionsByDoctorAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate) {
        return prescriptionRepository.findByDoctorIdAndCreatedAtBetweenOrderByCreatedAtDesc(doctorId, startDate, endDate);
    }
    
    @Override
    public List<Prescription> searchPrescriptionsByDiagnosis(String diagnosis) {
        return prescriptionRepository.findByDiagnosisContainingIgnoreCaseOrderByCreatedAtDesc(diagnosis);
    }
    
    @Override
    public Long getPrescriptionCountByDoctor(Long doctorId) {
        return prescriptionRepository.countByDoctorId(doctorId);
    }
    
    @Override
    public Long getPrescriptionCountByPatient(Long patientId) {
        return prescriptionRepository.countByPatientId(patientId);
    }
}


