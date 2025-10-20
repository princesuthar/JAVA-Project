package com.medical.prescription.repository;

import com.medical.prescription.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    
    @Query("SELECT m FROM Medicine m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Medicine> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT m FROM Medicine m WHERE LOWER(m.genericName) LIKE LOWER(CONCAT('%', :genericName, '%'))")
    List<Medicine> findByGenericNameContainingIgnoreCase(@Param("genericName") String genericName);
    
    @Query("SELECT m FROM Medicine m WHERE LOWER(m.manufacturer) LIKE LOWER(CONCAT('%', :manufacturer, '%'))")
    List<Medicine> findByManufacturerContainingIgnoreCase(@Param("manufacturer") String manufacturer);
    
    List<Medicine> findByDosageForm(String dosageForm);
    
    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity > 0")
    List<Medicine> findAvailableMedicines();
    
    @Query("SELECT m FROM Medicine m WHERE m.stockQuantity <= :threshold")
    List<Medicine> findLowStockMedicines(@Param("threshold") Integer threshold);
    
    @Query("SELECT m FROM Medicine m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(m.genericName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Medicine> findByNameOrGenericNameContainingIgnoreCase(@Param("searchTerm") String searchTerm);
}


