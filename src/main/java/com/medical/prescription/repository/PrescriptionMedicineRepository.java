package com.medical.prescription.repository;

import com.medical.prescription.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
    
    List<PrescriptionMedicine> findByPrescriptionId(Long prescriptionId);
    
    List<PrescriptionMedicine> findByMedicineId(Long medicineId);
    
    @Query("SELECT pm FROM PrescriptionMedicine pm WHERE pm.prescription.id = :prescriptionId ORDER BY pm.id")
    List<PrescriptionMedicine> findByPrescriptionIdOrderById(@Param("prescriptionId") Long prescriptionId);
    
    @Query("SELECT COUNT(pm) FROM PrescriptionMedicine pm WHERE pm.medicine.id = :medicineId")
    Long countByMedicineId(@Param("medicineId") Long medicineId);
    
    @Query("SELECT pm.medicine, COUNT(pm) as usageCount FROM PrescriptionMedicine pm GROUP BY pm.medicine ORDER BY usageCount DESC")
    List<Object[]> findMostPrescribedMedicines();
}


