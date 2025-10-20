package com.medical.prescription.service;

import com.medical.prescription.entity.Prescription;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    List<Prescription> getAllPrescriptions();
    Optional<Prescription> getPrescriptionById(Long id);
    Prescription savePrescription(Prescription prescription);
    Prescription updatePrescription(Long id, Prescription prescription);
    void deletePrescription(Long id);
    List<Prescription> getPrescriptionsByDoctor(Long doctorId);
    List<Prescription> getPrescriptionsByPatient(Long patientId);
    List<Prescription> getPrescriptionsByDoctorAndPatient(Long doctorId, Long patientId);
    List<Prescription> getPrescriptionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Prescription> getPrescriptionsByDoctorAndDateRange(Long doctorId, LocalDateTime startDate, LocalDateTime endDate);
    List<Prescription> searchPrescriptionsByDiagnosis(String diagnosis);
    Long getPrescriptionCountByDoctor(Long doctorId);
    Long getPrescriptionCountByPatient(Long patientId);
}


