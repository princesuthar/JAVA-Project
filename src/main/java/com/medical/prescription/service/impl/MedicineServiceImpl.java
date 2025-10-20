package com.medical.prescription.service.impl;

import com.medical.prescription.entity.Medicine;
import com.medical.prescription.repository.MedicineRepository;
import com.medical.prescription.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicineServiceImpl implements MedicineService {
    
    @Autowired
    private MedicineRepository medicineRepository;
    
    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
    
    @Override
    public Optional<Medicine> getMedicineById(Long id) {
        return medicineRepository.findById(id);
    }
    
    @Override
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }
    
    @Override
    public Medicine updateMedicine(Long id, Medicine medicine) {
        if (medicineRepository.existsById(id)) {
            medicine.setId(id);
            return medicineRepository.save(medicine);
        }
        throw new RuntimeException("Medicine not found with id: " + id);
    }
    
    @Override
    public void deleteMedicine(Long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
        } else {
            throw new RuntimeException("Medicine not found with id: " + id);
        }
    }
    
    @Override
    public List<Medicine> searchMedicines(String searchTerm) {
        return medicineRepository.findByNameOrGenericNameContainingIgnoreCase(searchTerm);
    }
    
    @Override
    public List<Medicine> getMedicinesByManufacturer(String manufacturer) {
        return medicineRepository.findByManufacturerContainingIgnoreCase(manufacturer);
    }
    
    @Override
    public List<Medicine> getAvailableMedicines() {
        return medicineRepository.findAvailableMedicines();
    }
    
    @Override
    public List<Medicine> getLowStockMedicines(Integer threshold) {
        return medicineRepository.findLowStockMedicines(threshold);
    }
}


