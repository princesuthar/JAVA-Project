package com.medical.prescription.controller;

import com.medical.prescription.entity.Doctor;
import com.medical.prescription.entity.Patient;
import com.medical.prescription.entity.Prescription;
import com.medical.prescription.service.DoctorService;
import com.medical.prescription.service.PatientService;
import com.medical.prescription.service.PrescriptionService;
import com.medical.prescription.service.MedicineService;
import com.medical.prescription.entity.Medicine;
import com.medical.prescription.entity.PrescriptionMedicine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;
    
    @Autowired
    private PatientService patientService;
    
    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicineService medicineService;
    
    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(defaultValue = "1") Long doctorId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            model.addAttribute("doctor", doctor.get());
            
            // Get prescription statistics
            Long totalPrescriptions = prescriptionService.getPrescriptionCountByDoctor(doctorId);
            List<Patient> patients = patientService.getPatientsByDoctor(doctorId);
            List<Prescription> recentPrescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
            
            model.addAttribute("totalPrescriptions", totalPrescriptions);
            model.addAttribute("totalPatients", patients.size());
            model.addAttribute("recentPrescriptions", recentPrescriptions.size() > 5 ? recentPrescriptions.subList(0, 5) : recentPrescriptions);
            
            return "doctor/dashboard";
        }
        return "error";
    }
    
    @GetMapping("/patients")
    public String patients(@RequestParam(defaultValue = "1") Long doctorId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            List<Patient> patients = patientService.getPatientsByDoctor(doctorId);
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("patients", patients);
            return "doctor/patients";
        }
        return "error";
    }
    
    @GetMapping("/patients/search")
    public String searchPatients(
            @RequestParam(defaultValue = "1") Long doctorId,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String bloodGroup,
            Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            List<Patient> patients = patientService.getPatientsByDoctor(doctorId);
            
            // Apply filters
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String search = searchTerm.toLowerCase();
                patients = patients.stream()
                    .filter(p -> (p.getName() != null && p.getName().toLowerCase().contains(search)) ||
                                (p.getPhone() != null && p.getPhone().contains(search)))
                    .toList();
                model.addAttribute("searchTerm", searchTerm);
            }
            
            if (gender != null && !gender.isEmpty()) {
                patients = patients.stream()
                    .filter(p -> p.getGender() != null && p.getGender().toString().equals(gender))
                    .toList();
                model.addAttribute("gender", gender);
            }
            
            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                patients = patients.stream()
                    .filter(p -> p.getBloodGroup() != null && p.getBloodGroup().equals(bloodGroup))
                    .toList();
                model.addAttribute("bloodGroup", bloodGroup);
            }
            
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("patients", patients);
            return "doctor/patients";
        }
        return "error";
    }
    
    @GetMapping("/prescriptions")
    public String prescriptions(@RequestParam(defaultValue = "1") Long doctorId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("prescriptions", prescriptions);
            return "doctor/prescriptions";
        }
        return "error";
    }
    
    @GetMapping("/prescriptions/search")
    public String searchPrescriptions(
            @RequestParam(defaultValue = "1") Long doctorId,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctor(doctorId);
            
            // Apply search filter
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String search = searchTerm.toLowerCase();
                prescriptions = prescriptions.stream()
                    .filter(p -> (p.getPatient().getName() != null && p.getPatient().getName().toLowerCase().contains(search)) ||
                                (p.getDiagnosis() != null && p.getDiagnosis().toLowerCase().contains(search)))
                    .toList();
                model.addAttribute("searchTerm", searchTerm);
            }
            
            // Apply date range filter
            if (fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
                java.time.LocalDate from = java.time.LocalDate.parse(fromDate);
                java.time.LocalDate to = java.time.LocalDate.parse(toDate);
                prescriptions = prescriptions.stream()
                    .filter(p -> {
                        java.time.LocalDate prescDate = p.getCreatedAt().toLocalDate();
                        return !prescDate.isBefore(from) && !prescDate.isAfter(to);
                    })
                    .toList();
                model.addAttribute("fromDate", fromDate);
                model.addAttribute("toDate", toDate);
            }
            
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("prescriptions", prescriptions);
            return "doctor/prescriptions";
        }
        return "error";
    }
    
    @GetMapping("/new-prescription")
    public String newPrescription(@RequestParam(defaultValue = "1") Long doctorId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            List<Patient> patients = patientService.getAllPatients();
            List<Medicine> medicines = medicineService.getAllMedicines();
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("patients", patients);
            model.addAttribute("medicines", medicines);
            model.addAttribute("prescription", new Prescription());
            return "doctor/new-prescription";
        }
        return "error";
    }

    @GetMapping("/patients/add")
    public String addPatientForm(@RequestParam(defaultValue = "1") Long doctorId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("patient", new Patient());
            model.addAttribute("genders", Patient.Gender.values());
            return "doctor/add-patient";
        }
        return "error";
    }

    @PostMapping("/patients/save")
    public String savePatient(@RequestParam(defaultValue = "1") Long doctorId,
                              @Valid @ModelAttribute("patient") Patient patient,
                              BindingResult bindingResult,
                              Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (doctor.isPresent()) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("doctor", doctor.get());
                model.addAttribute("genders", Patient.Gender.values());
                return "doctor/add-patient";
            }
            patientService.savePatient(patient);
            return "redirect:/doctor/patients?doctorId=" + doctorId;
        }
        return "error";
    }
    
    @PostMapping("/save-prescription")
    public String savePrescription(@ModelAttribute Prescription prescription, 
                                 @RequestParam Long doctorId, 
                                 @RequestParam Long patientId,
                                 HttpServletRequest request) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        Optional<Patient> patient = patientService.getPatientById(patientId);

        if (doctor.isPresent() && patient.isPresent()) {
            prescription.setDoctor(doctor.get());
            prescription.setPatient(patient.get());

            // Parse medicines from request parameters named like medicines[0].name, medicines[0].dosage, etc.
            int idx = 0;
            while (true) {
                String base = "medicines[" + idx + "]";
                String medName = request.getParameter(base + ".name");
                if (medName == null || medName.trim().isEmpty()) {
                    break; // no more medicines
                }

                String dosage = request.getParameter(base + ".dosage");
                String frequency = request.getParameter(base + ".frequency");
                String quantityStr = request.getParameter(base + ".quantity");
                String duration = request.getParameter(base + ".duration");
                String instructions = request.getParameter(base + ".instructions");
                String morningDose = request.getParameter(base + ".morningDose");
                String afternoonDose = request.getParameter(base + ".afternoonDose");
                String nightDose = request.getParameter(base + ".nightDose");

                // find existing medicine by name (contains) or create new
                Medicine medicine = null;
                if (medName != null) {
                    medName = medName.trim();
                    // try to find similar medicines
                    java.util.List<Medicine> found = medicineService.searchMedicines(medName);
                    if (!found.isEmpty()) {
                        medicine = found.get(0);
                    } else {
                        Medicine m = new Medicine();
                        m.setName(medName);
                        medicine = medicineService.saveMedicine(m);
                    }
                }

                PrescriptionMedicine pm = new PrescriptionMedicine();
                pm.setPrescription(prescription);
                pm.setMedicine(medicine);
                pm.setDosage(dosage != null ? dosage : "");
                pm.setFrequency(frequency != null ? frequency : "");
                try {
                    pm.setQuantity(quantityStr != null && !quantityStr.isEmpty() ? Integer.parseInt(quantityStr) : 1);
                } catch (NumberFormatException ex) {
                    pm.setQuantity(1);
                }
                pm.setDuration(duration);
                pm.setInstructions(instructions);
                pm.setMorningDose(morningDose);
                pm.setAfternoonDose(afternoonDose);
                pm.setNightDose(nightDose);

                // attach to prescription
                prescription.getPrescriptionMedicines().add(pm);

                idx++;
            }

            prescriptionService.savePrescription(prescription);
            return "redirect:/doctor/prescriptions?doctorId=" + doctorId;
        }
        return "error";
    }
    
    @GetMapping("/patient/{patientId}")
    public String viewPatient(@RequestParam Long doctorId, @PathVariable Long patientId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        Optional<Patient> patient = patientService.getPatientById(patientId);
        
        if (doctor.isPresent() && patient.isPresent()) {
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDoctorAndPatient(doctorId, patientId);
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("patient", patient.get());
            model.addAttribute("prescriptions", prescriptions);
            return "doctor/patient-details";
        }
        return "error";
    }
    
    @GetMapping("/patients/edit/{patientId}")
    public String editPatientForm(@RequestParam(defaultValue = "1") Long doctorId, 
                                  @PathVariable Long patientId, Model model) {
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        Optional<Patient> patient = patientService.getPatientById(patientId);
        
        if (doctor.isPresent() && patient.isPresent()) {
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("patient", patient.get());
            model.addAttribute("genders", Patient.Gender.values());
            return "doctor/edit-patient";
        }
        return "error";
    }
    
    @PostMapping("/patients/edit/{patientId}")
    public String updatePatient(@RequestParam Long doctorId, 
                               @PathVariable Long patientId,
                               @Valid @ModelAttribute("patient") Patient patient,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
            if (doctor.isPresent()) {
                model.addAttribute("doctor", doctor.get());
                model.addAttribute("genders", Patient.Gender.values());
            }
            return "doctor/edit-patient";
        }
        
        Optional<Patient> existingPatient = patientService.getPatientById(patientId);
        if (existingPatient.isPresent()) {
            Patient updatedPatient = existingPatient.get();
            updatedPatient.setName(patient.getName());
            updatedPatient.setDateOfBirth(patient.getDateOfBirth());
            updatedPatient.setGender(patient.getGender());
            updatedPatient.setPhone(patient.getPhone());
            updatedPatient.setEmail(patient.getEmail());
            updatedPatient.setAddress(patient.getAddress());
            updatedPatient.setBloodGroup(patient.getBloodGroup());
            updatedPatient.setAllergies(patient.getAllergies());
            updatedPatient.setEmergencyContact(patient.getEmergencyContact());
            
            patientService.savePatient(updatedPatient);
        }
        
        return "redirect:/doctor/patient/" + patientId + "?doctorId=" + doctorId;
    }
    
    @PostMapping("/patients/delete/{patientId}")
    public String deletePatient(@RequestParam Long doctorId, @PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return "redirect:/doctor/patients?doctorId=" + doctorId;
    }
}


