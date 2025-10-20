package com.medical.prescription.service;

import com.medical.prescription.entity.Medicine;
import java.util.List;
import java.util.Optional;

public interface MedicineService {
    List<Medicine> getAllMedicines();
    Optional<Medicine> getMedicineById(Long id);
    Medicine saveMedicine(Medicine medicine);
    Medicine updateMedicine(Long id, Medicine medicine);
    void deleteMedicine(Long id);
    List<Medicine> searchMedicines(String searchTerm);
    List<Medicine> getMedicinesByManufacturer(String manufacturer);
    List<Medicine> getAvailableMedicines();
    List<Medicine> getLowStockMedicines(Integer threshold);
}


