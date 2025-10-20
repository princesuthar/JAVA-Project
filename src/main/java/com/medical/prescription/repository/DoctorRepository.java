package com.medical.prescription.repository;

import com.medical.prescription.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    
    Optional<Doctor> findByEmail(String email);
    
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
    
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))")
    List<Doctor> findByNameOrSpecializationContainingIgnoreCase(@Param("name") String name, @Param("specialization") String specialization);
}


