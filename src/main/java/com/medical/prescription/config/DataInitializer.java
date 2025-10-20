package com.medical.prescription.config;

import com.medical.prescription.entity.*;
import com.medical.prescription.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private MedicineRepository medicineRepository;
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    @Autowired
    private PrescriptionMedicineRepository prescriptionMedicineRepository;
    
    @Autowired
    private DiseaseHistoryRepository diseaseHistoryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Only initialize if no data exists
        if (doctorRepository.count() == 0) {
            initializeSampleData();
        }
        // Create default admin user if no users exist
        if (userRepository.count() == 0) {
            createDefaultUsers();
        }
    }
    
    private void createDefaultUsers() {
        // Create default admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setEmail("admin@medical.com");
        admin.setFullName("System Administrator");
        admin.setRole("ADMIN");
        admin.setEnabled(true);
        userRepository.save(admin);
        
        // Create default doctor user (linked to Dr. Ishan Mota)
        User doctorUser = new User();
        doctorUser.setUsername("doctor");
        doctorUser.setPassword(passwordEncoder.encode("doctor123"));
        doctorUser.setEmail("doctor@medical.com");
        doctorUser.setFullName("Dr. Ishan Mota");
        doctorUser.setRole("DOCTOR");
        doctorUser.setEnabled(true);
        if (doctorRepository.count() > 0) {
            doctorUser.setDoctor(doctorRepository.findById(1L).orElse(null));
        }
        userRepository.save(doctorUser);
    }
    
    private void initializeSampleData() {
        // Create sample doctors
        Doctor doctor1 = new Doctor("Dr. Ishan Mota", "Conservative Dentistry and Endodontics", "DOC001", 
                                   "info@identistindia.com", "+91 95522 63314", 
                                   "India", "BDS, MDS - Conservative Dentistry and Endodontics");
        doctor1 = doctorRepository.save(doctor1);
        
        Doctor doctor2 = new Doctor("Dr. Sarah Johnson", "General Physician", "DOC002", 
                                   "sarah.johnson@hospital.com", "+1234567891", 
                                   "456 Health Ave, City", "MD, General Medicine");
        doctor2 = doctorRepository.save(doctor2);
        
        // Create sample patients
        Patient patient1 = new Patient("Alice Brown", LocalDate.of(1985, 5, 15), 
                                      Patient.Gender.FEMALE, "alice.brown@email.com", 
                                      "+1987654321", "789 Patient St, City");
        patient1.setBloodGroup("O+");
        patient1.setAllergies("Penicillin");
        patient1.setMedicalHistory("Hypertension, Diabetes Type 2");
        patient1 = patientRepository.save(patient1);
        
        Patient patient2 = new Patient("Bob Wilson", LocalDate.of(1978, 8, 22), 
                                      Patient.Gender.MALE, "bob.wilson@email.com", 
                                      "+1987654322", "321 Health Rd, City");
        patient2.setBloodGroup("A+");
        patient2.setEmergencyContact("+1987654323");
        patient2 = patientRepository.save(patient2);
        
        Patient patient3 = new Patient("Carol Davis", LocalDate.of(1990, 3, 10), 
                                      Patient.Gender.FEMALE, "carol.davis@email.com", 
                                      "+1987654324", "654 Wellness Blvd, City");
        patient3.setBloodGroup("B+");
        patient3.setAllergies("Latex");
        patient3 = patientRepository.save(patient3);
        
        // Create sample medicines
        Medicine medicine1 = new Medicine("Paracetamol", "Acetaminophen", "PharmaCorp", 
                                         "Tablet", "500mg");
        medicine1.setDescription("Pain reliever and fever reducer");
        medicine1.setSideEffects("Nausea, stomach pain");
        medicine1.setPrice(new BigDecimal("5.99"));
        medicine1.setStockQuantity(100);
        medicine1 = medicineRepository.save(medicine1);
        
        Medicine medicine2 = new Medicine("Ibuprofen", "Ibuprofen", "MediHealth", 
                                         "Tablet", "200mg");
        medicine2.setDescription("Anti-inflammatory pain reliever");
        medicine2.setSideEffects("Stomach upset, dizziness");
        medicine2.setPrice(new BigDecimal("8.99"));
        medicine2.setStockQuantity(75);
        medicine2 = medicineRepository.save(medicine2);
        
        Medicine medicine3 = new Medicine("Amoxicillin", "Amoxicillin", "AntibioLab", 
                                         "Capsule", "500mg");
        medicine3.setDescription("Antibiotic for bacterial infections");
        medicine3.setSideEffects("Diarrhea, nausea");
        medicine3.setPrice(new BigDecimal("12.99"));
        medicine3.setStockQuantity(50);
        medicine3 = medicineRepository.save(medicine3);
        
        Medicine medicine4 = new Medicine("Lisinopril", "Lisinopril", "CardioPharm", 
                                         "Tablet", "10mg");
        medicine4.setDescription("ACE inhibitor for blood pressure");
        medicine4.setSideEffects("Dry cough, dizziness");
        medicine4.setPrice(new BigDecimal("15.99"));
        medicine4.setStockQuantity(30);
        medicine4 = medicineRepository.save(medicine4);
        
        // Create sample prescriptions
        Prescription prescription1 = new Prescription(doctor1, patient1, 
                                                     "Hypertension Management", 
                                                     "High blood pressure, headaches", 
                                                     "Monitor blood pressure daily");
        prescription1.setFollowUpDate(LocalDateTime.now().plusDays(30));
        prescription1 = prescriptionRepository.save(prescription1);
        
        Prescription prescription2 = new Prescription(doctor2, patient2, 
                                                     "Acute Bronchitis", 
                                                     "Cough, chest congestion, fever", 
                                                     "Rest and plenty of fluids");
        prescription2.setFollowUpDate(LocalDateTime.now().plusDays(14));
        prescription2 = prescriptionRepository.save(prescription2);
        
        Prescription prescription3 = new Prescription(doctor1, patient3, 
                                                     "Routine Checkup", 
                                                     "Annual physical examination", 
                                                     "Continue regular exercise and healthy diet");
        prescription3 = prescriptionRepository.save(prescription3);
        
        // Create prescription medicines
        PrescriptionMedicine pm1 = new PrescriptionMedicine(prescription1, medicine4, 
                                                           "10mg", "Once daily", 30);
        pm1.setDuration("30 days");
        pm1.setInstructions("Take with food");
        prescriptionMedicineRepository.save(pm1);
        
        PrescriptionMedicine pm2 = new PrescriptionMedicine(prescription2, medicine3, 
                                                           "500mg", "Three times daily", 21);
        pm2.setDuration("7 days");
        pm2.setInstructions("Take with plenty of water");
        prescriptionMedicineRepository.save(pm2);
        
        PrescriptionMedicine pm3 = new PrescriptionMedicine(prescription2, medicine1, 
                                                           "500mg", "Every 6 hours", 20);
        pm3.setDuration("5 days");
        pm3.setInstructions("Take as needed for fever");
        prescriptionMedicineRepository.save(pm3);
        
        // Create disease histories
        DiseaseHistory disease1 = new DiseaseHistory(patient1, "Hypertension", 
                                                    "Chronic high blood pressure", 
                                                    LocalDateTime.now().minusMonths(6));
        disease1.setStatus(DiseaseHistory.DiseaseStatus.CHRONIC);
        disease1.setSeverity(DiseaseHistory.Severity.MODERATE);
        disease1.setTreatmentGiven("ACE inhibitors, lifestyle modifications");
        diseaseHistoryRepository.save(disease1);
        
        DiseaseHistory disease2 = new DiseaseHistory(patient2, "Acute Bronchitis", 
                                                    "Inflammation of the bronchial tubes", 
                                                    LocalDateTime.now().minusDays(3));
        disease2.setStatus(DiseaseHistory.DiseaseStatus.UNDER_TREATMENT);
        disease2.setSeverity(DiseaseHistory.Severity.MILD);
        disease2.setTreatmentGiven("Antibiotics, rest, fluids");
        diseaseHistoryRepository.save(disease2);
        
        DiseaseHistory disease3 = new DiseaseHistory(patient1, "Diabetes Type 2", 
                                                    "Insulin resistance diabetes", 
                                                    LocalDateTime.now().minusYears(2));
        disease3.setStatus(DiseaseHistory.DiseaseStatus.CHRONIC);
        disease3.setSeverity(DiseaseHistory.Severity.MODERATE);
        disease3.setTreatmentGiven("Metformin, diet control, exercise");
        diseaseHistoryRepository.save(disease3);
    }
}


