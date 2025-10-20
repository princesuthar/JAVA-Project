-- Medical Prescription Management System Database Setup
-- MySQL Database Creation Script

-- Create database
CREATE DATABASE IF NOT EXISTS medical_prescription_db;
USE medical_prescription_db;

-- Create doctors table
CREATE TABLE IF NOT EXISTS doctors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    license_number VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255),
    phone VARCHAR(20),
    address TEXT,
    qualification VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create patients table
CREATE TABLE IF NOT EXISTS patients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    email VARCHAR(255),
    phone VARCHAR(20),
    address TEXT,
    emergency_contact VARCHAR(255),
    blood_group VARCHAR(10),
    allergies TEXT,
    medical_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create medicines table
CREATE TABLE IF NOT EXISTS medicines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    generic_name VARCHAR(255),
    manufacturer VARCHAR(255),
    dosage_form VARCHAR(100),
    strength VARCHAR(100),
    description TEXT,
    side_effects TEXT,
    contraindications TEXT,
    price DECIMAL(10,2),
    stock_quantity INT DEFAULT 0,
    is_prescription_required BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create prescriptions table
CREATE TABLE IF NOT EXISTS prescriptions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    diagnosis TEXT NOT NULL,
    symptoms TEXT,
    notes TEXT,
    follow_up_date DATETIME,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

-- Create prescription_medicines table
CREATE TABLE IF NOT EXISTS prescription_medicines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prescription_id BIGINT NOT NULL,
    medicine_id BIGINT NOT NULL,
    dosage VARCHAR(255) NOT NULL,
    frequency VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    duration VARCHAR(255),
    instructions TEXT,
    is_before_meal BOOLEAN DEFAULT FALSE,
    is_after_meal BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicines(id) ON DELETE CASCADE
);

-- Create disease_histories table
CREATE TABLE IF NOT EXISTS disease_histories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    disease_name VARCHAR(255) NOT NULL,
    description TEXT,
    diagnosis_date DATETIME,
    treatment_given TEXT,
    status ENUM('ACTIVE', 'RECOVERED', 'CHRONIC', 'UNDER_TREATMENT'),
    severity ENUM('MILD', 'MODERATE', 'SEVERE', 'CRITICAL'),
    recovery_date DATETIME,
    notes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_doctors_license ON doctors(license_number);
CREATE INDEX idx_doctors_email ON doctors(email);
CREATE INDEX idx_patients_email ON patients(email);
CREATE INDEX idx_patients_phone ON patients(phone);
CREATE INDEX idx_medicines_name ON medicines(name);
CREATE INDEX idx_medicines_generic ON medicines(generic_name);
CREATE INDEX idx_prescriptions_doctor ON prescriptions(doctor_id);
CREATE INDEX idx_prescriptions_patient ON prescriptions(patient_id);
CREATE INDEX idx_prescriptions_date ON prescriptions(created_at);
CREATE INDEX idx_prescription_medicines_prescription ON prescription_medicines(prescription_id);
CREATE INDEX idx_prescription_medicines_medicine ON prescription_medicines(medicine_id);
CREATE INDEX idx_disease_histories_patient ON disease_histories(patient_id);
CREATE INDEX idx_disease_histories_date ON disease_histories(diagnosis_date);

-- Insert sample data
INSERT INTO doctors (name, specialization, license_number, email, phone, address, qualification) VALUES
('Dr. John Smith', 'Cardiologist', 'DOC001', 'john.smith@hospital.com', '+1234567890', '123 Medical St, City', 'MD, Cardiology'),
('Dr. Sarah Johnson', 'General Physician', 'DOC002', 'sarah.johnson@hospital.com', '+1234567891', '456 Health Ave, City', 'MD, General Medicine'),
('Dr. Michael Brown', 'Pediatrician', 'DOC003', 'michael.brown@hospital.com', '+1234567892', '789 Children Blvd, City', 'MD, Pediatrics'),
('Dr. Emily Davis', 'Dermatologist', 'DOC004', 'emily.davis@hospital.com', '+1234567893', '321 Skin Care Ave, City', 'MD, Dermatology');

INSERT INTO patients (name, date_of_birth, gender, email, phone, address, emergency_contact, blood_group, allergies, medical_history) VALUES
('Alice Brown', '1985-05-15', 'FEMALE', 'alice.brown@email.com', '+1987654321', '789 Patient St, City', '+1987654320', 'O+', 'Penicillin', 'Hypertension, Diabetes Type 2'),
('Bob Wilson', '1978-08-22', 'MALE', 'bob.wilson@email.com', '+1987654322', '321 Health Rd, City', '+1987654323', 'A+', NULL, 'No significant medical history'),
('Carol Davis', '1990-03-10', 'FEMALE', 'carol.davis@email.com', '+1987654324', '654 Wellness Blvd, City', '+1987654325', 'B+', 'Latex', 'Seasonal allergies'),
('David Miller', '1982-12-05', 'MALE', 'david.miller@email.com', '+1987654326', '987 Care Lane, City', '+1987654327', 'AB+', 'Sulfa drugs', 'Asthma, Hypertension'),
('Eva Garcia', '1995-07-18', 'FEMALE', 'eva.garcia@email.com', '+1987654328', '456 Medical Dr, City', '+1987654329', 'O-', NULL, 'No significant medical history');

INSERT INTO medicines (name, generic_name, manufacturer, dosage_form, strength, description, side_effects, contraindications, price, stock_quantity) VALUES
('Paracetamol', 'Acetaminophen', 'PharmaCorp', 'Tablet', '500mg', 'Pain reliever and fever reducer', 'Nausea, stomach pain', 'Liver disease', 5.99, 100),
('Ibuprofen', 'Ibuprofen', 'MediHealth', 'Tablet', '200mg', 'Anti-inflammatory pain reliever', 'Stomach upset, dizziness', 'Stomach ulcers', 8.99, 75),
('Amoxicillin', 'Amoxicillin', 'AntibioLab', 'Capsule', '500mg', 'Antibiotic for bacterial infections', 'Diarrhea, nausea', 'Penicillin allergy', 12.99, 50),
('Lisinopril', 'Lisinopril', 'CardioPharm', 'Tablet', '10mg', 'ACE inhibitor for blood pressure', 'Dry cough, dizziness', 'Pregnancy, angioedema', 15.99, 30),
('Metformin', 'Metformin', 'DiabetaCorp', 'Tablet', '500mg', 'Diabetes medication', 'Nausea, diarrhea', 'Kidney disease', 9.99, 60),
('Atorvastatin', 'Atorvastatin', 'CholesterolMed', 'Tablet', '20mg', 'Cholesterol lowering medication', 'Muscle pain, liver problems', 'Liver disease', 18.99, 40),
('Omeprazole', 'Omeprazole', 'GastricHealth', 'Capsule', '20mg', 'Proton pump inhibitor for acid reflux', 'Headache, nausea', 'Severe liver disease', 11.99, 80),
('Cetirizine', 'Cetirizine', 'AllergyRelief', 'Tablet', '10mg', 'Antihistamine for allergies', 'Drowsiness, dry mouth', 'Severe kidney disease', 7.99, 120);

-- Insert sample prescriptions
INSERT INTO prescriptions (doctor_id, patient_id, diagnosis, symptoms, notes, follow_up_date, created_at) VALUES
(1, 1, 'Hypertension Management', 'High blood pressure, headaches', 'Monitor blood pressure daily', DATE_ADD(NOW(), INTERVAL 30 DAY), NOW()),
(2, 2, 'Acute Bronchitis', 'Cough, chest congestion, fever', 'Rest and plenty of fluids', DATE_ADD(NOW(), INTERVAL 14 DAY), NOW()),
(1, 3, 'Routine Checkup', 'Annual physical examination', 'Continue regular exercise and healthy diet', NULL, NOW()),
(2, 4, 'Asthma Exacerbation', 'Wheezing, shortness of breath', 'Use inhaler as prescribed', DATE_ADD(NOW(), INTERVAL 7 DAY), NOW()),
(1, 5, 'Diabetes Screening', 'Blood sugar monitoring', 'Follow diabetic diet and exercise', DATE_ADD(NOW(), INTERVAL 21 DAY), NOW());

-- Insert sample prescription medicines
INSERT INTO prescription_medicines (prescription_id, medicine_id, dosage, frequency, quantity, duration, instructions, is_before_meal, is_after_meal) VALUES
(1, 4, '10mg', 'Once daily', 30, '30 days', 'Take with food', FALSE, TRUE),
(2, 3, '500mg', 'Three times daily', 21, '7 days', 'Take with plenty of water', FALSE, FALSE),
(2, 1, '500mg', 'Every 6 hours', 20, '5 days', 'Take as needed for fever', FALSE, FALSE),
(4, 6, '20mg', 'Once daily', 30, '30 days', 'Take with food', FALSE, TRUE),
(5, 5, '500mg', 'Twice daily', 60, '30 days', 'Take with meals', FALSE, TRUE);

-- Insert sample disease histories
INSERT INTO disease_histories (patient_id, disease_name, description, diagnosis_date, treatment_given, status, severity, notes) VALUES
(1, 'Hypertension', 'Chronic high blood pressure', DATE_SUB(NOW(), INTERVAL 6 MONTH), 'ACE inhibitors, lifestyle modifications', 'CHRONIC', 'MODERATE', 'Well controlled with medication'),
(2, 'Acute Bronchitis', 'Inflammation of the bronchial tubes', DATE_SUB(NOW(), INTERVAL 3 DAY), 'Antibiotics, rest, fluids', 'UNDER_TREATMENT', 'MILD', 'Improving with treatment'),
(1, 'Diabetes Type 2', 'Insulin resistance diabetes', DATE_SUB(NOW(), INTERVAL 2 YEAR), 'Metformin, diet control, exercise', 'CHRONIC', 'MODERATE', 'HbA1c within target range'),
(4, 'Asthma', 'Chronic respiratory condition', DATE_SUB(NOW(), INTERVAL 1 YEAR), 'Inhaled corticosteroids, bronchodilators', 'CHRONIC', 'MILD', 'Well controlled with inhalers'),
(3, 'Seasonal Allergies', 'Allergic rhinitis', DATE_SUB(NOW(), INTERVAL 3 MONTH), 'Antihistamines, nasal sprays', 'ACTIVE', 'MILD', 'Symptoms during pollen season');

-- Create views for common queries
CREATE VIEW patient_prescription_summary AS
SELECT 
    p.id as patient_id,
    p.name as patient_name,
    COUNT(pr.id) as total_prescriptions,
    MAX(pr.created_at) as last_prescription_date
FROM patients p
LEFT JOIN prescriptions pr ON p.id = pr.patient_id
GROUP BY p.id, p.name;

CREATE VIEW doctor_prescription_summary AS
SELECT 
    d.id as doctor_id,
    d.name as doctor_name,
    d.specialization,
    COUNT(pr.id) as total_prescriptions,
    COUNT(DISTINCT pr.patient_id) as unique_patients
FROM doctors d
LEFT JOIN prescriptions pr ON d.id = pr.doctor_id
GROUP BY d.id, d.name, d.specialization;

CREATE VIEW medicine_usage_summary AS
SELECT 
    m.id as medicine_id,
    m.name as medicine_name,
    m.generic_name,
    COUNT(pm.id) as prescription_count,
    SUM(pm.quantity) as total_quantity_prescribed
FROM medicines m
LEFT JOIN prescription_medicines pm ON m.id = pm.medicine_id
GROUP BY m.id, m.name, m.generic_name;

-- Create stored procedures for common operations
DELIMITER //

CREATE PROCEDURE GetPatientHistory(IN patient_id BIGINT)
BEGIN
    SELECT 
        p.name as patient_name,
        pr.diagnosis,
        pr.symptoms,
        pr.created_at as prescription_date,
        d.name as doctor_name,
        d.specialization
    FROM patients p
    JOIN prescriptions pr ON p.id = pr.patient_id
    JOIN doctors d ON pr.doctor_id = d.id
    WHERE p.id = patient_id
    ORDER BY pr.created_at DESC;
END //

CREATE PROCEDURE GetDoctorStatistics(IN doctor_id BIGINT)
BEGIN
    SELECT 
        COUNT(pr.id) as total_prescriptions,
        COUNT(DISTINCT pr.patient_id) as unique_patients,
        DATE(MIN(pr.created_at)) as first_prescription_date,
        DATE(MAX(pr.created_at)) as last_prescription_date
    FROM prescriptions pr
    WHERE pr.doctor_id = doctor_id;
END //

DELIMITER ;

-- Grant permissions (adjust as needed for your environment)
-- GRANT ALL PRIVILEGES ON medical_prescription_db.* TO 'your_username'@'localhost';
-- FLUSH PRIVILEGES;

-- Display setup completion message
SELECT 'Medical Prescription Management System database setup completed successfully!' as message;
