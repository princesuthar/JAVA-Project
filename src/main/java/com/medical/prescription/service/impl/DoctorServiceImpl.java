package com.medical.prescription.service.impl;

import com.medical.prescription.entity.Doctor;
import com.medical.prescription.repository.DoctorRepository;
import com.medical.prescription.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }
    
    @Override
    public Optional<Doctor> getDoctorByLicenseNumber(String licenseNumber) {
        return doctorRepository.findByLicenseNumber(licenseNumber);
    }
    
    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        if (doctorRepository.existsById(id)) {
            doctor.setId(id);
            return doctorRepository.save(doctor);
        }
        throw new RuntimeException("Doctor not found with id: " + id);
    }
    
    @Override
    public void deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }
    
    @Override
    public List<Doctor> searchDoctors(String searchTerm) {
        return doctorRepository.findByNameOrSpecializationContainingIgnoreCase(searchTerm, searchTerm);
    }
    
    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }
}


