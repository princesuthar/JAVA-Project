package com.medical.prescription.controller.api;

import com.medical.prescription.entity.Patient;
import com.medical.prescription.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientApiController {
    
    @Autowired
    private PatientService patientService;
    
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        return patient.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient savedPatient = patientService.savePatient(patient);
        return ResponseEntity.ok(savedPatient);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String searchTerm) {
        List<Patient> patients = patientService.searchPatients(searchTerm);
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<Patient>> getPatientsByDoctor(@PathVariable Long doctorId) {
        List<Patient> patients = patientService.getPatientsByDoctor(doctorId);
        return ResponseEntity.ok(patients);
    }
    
    @GetMapping("/by-age")
    public ResponseEntity<List<Patient>> getPatientsByAgeRange(
            @RequestParam LocalDate startDate, 
            @RequestParam LocalDate endDate) {
        List<Patient> patients = patientService.getPatientsByAgeRange(startDate, endDate);
        return ResponseEntity.ok(patients);
    }
}


