package com.medical.prescription.service;

import com.medical.prescription.entity.Patient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAllPatients();
    Optional<Patient> getPatientById(Long id);
    Optional<Patient> getPatientByEmail(String email);
    Optional<Patient> getPatientByPhone(String phone);
    Patient savePatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
    List<Patient> searchPatients(String searchTerm);
    List<Patient> getPatientsByAgeRange(LocalDate startDate, LocalDate endDate);
    List<Patient> getPatientsByDoctor(Long doctorId);
}


