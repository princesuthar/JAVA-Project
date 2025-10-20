package com.medical.prescription.service;

import com.medical.prescription.entity.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Optional<Doctor> getDoctorById(Long id);
    Optional<Doctor> getDoctorByLicenseNumber(String licenseNumber);
    Doctor saveDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctor(Long id);
    List<Doctor> searchDoctors(String searchTerm);
    List<Doctor> getDoctorsBySpecialization(String specialization);
}


