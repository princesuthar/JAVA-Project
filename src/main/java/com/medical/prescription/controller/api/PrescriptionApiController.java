package com.medical.prescription.controller.api;

import com.medical.prescription.entity.Prescription;
import com.medical.prescription.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionApiController {
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        return prescription.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        Prescription savedPrescription = prescriptionService.savePrescription(prescription);
        return ResponseEntity.ok(savedPrescription);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescription);
            return ResponseEntity.ok(updatedPrescription);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        try {
            prescriptionService.deletePrescription(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByPatient(patientId);
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/doctor/{doctorId}/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctorAndPatient(
            @PathVariable Long doctorId, 
            @PathVariable Long patientId) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctorAndPatient(doctorId, patientId);
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Prescription>> searchPrescriptionsByDiagnosis(@RequestParam String diagnosis) {
        List<Prescription> prescriptions = prescriptionService.searchPrescriptionsByDiagnosis(diagnosis);
        return ResponseEntity.ok(prescriptions);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDateRange(
            @RequestParam LocalDateTime startDate, 
            @RequestParam LocalDateTime endDate) {
        List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(prescriptions);
    }
}


