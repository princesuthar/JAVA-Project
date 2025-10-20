package com.medical.prescription.service.impl;

import com.medical.prescription.entity.Patient;
import com.medical.prescription.repository.PatientRepository;
import com.medical.prescription.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
    
    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    
    @Override
    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
    
    @Override
    public Optional<Patient> getPatientByPhone(String phone) {
        return patientRepository.findByPhone(phone);
    }
    
    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    @Override
    public Patient updatePatient(Long id, Patient patient) {
        if (patientRepository.existsById(id)) {
            patient.setId(id);
            return patientRepository.save(patient);
        }
        throw new RuntimeException("Patient not found with id: " + id);
    }
    
    @Override
    public void deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Patient not found with id: " + id);
        }
    }
    
    @Override
    public List<Patient> searchPatients(String searchTerm) {
        return patientRepository.findByNameOrEmailOrPhoneContaining(searchTerm, searchTerm, searchTerm);
    }
    
    @Override
    public List<Patient> getPatientsByAgeRange(LocalDate startDate, LocalDate endDate) {
        return patientRepository.findByDateOfBirthBetween(startDate, endDate);
    }
    
    @Override
    public List<Patient> getPatientsByDoctor(Long doctorId) {
        return patientRepository.findPatientsByDoctorId(doctorId);
    }
}


