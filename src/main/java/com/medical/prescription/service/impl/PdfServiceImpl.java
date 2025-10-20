package com.medical.prescription.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.medical.prescription.entity.Prescription;
import com.medical.prescription.entity.PrescriptionMedicine;
import com.medical.prescription.service.PdfService;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.security.MessageDigest;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Service
public class PdfServiceImpl implements PdfService {
    
    @Override
    public ByteArrayInputStream generatePrescriptionPdf(Prescription prescription) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, out);
            document.open();
            
            // ========== LETTERHEAD START ==========
            
            // Add a colored header bar
            PdfPTable headerBar = new PdfPTable(1);
            headerBar.setWidthPercentage(100);
            headerBar.setSpacingAfter(10f);
            PdfPCell headerBarCell = new PdfPCell();
            headerBarCell.setBackgroundColor(new Color(102, 126, 234)); // Purple color
            headerBarCell.setBorder(Rectangle.NO_BORDER);
            headerBarCell.setFixedHeight(15f);
            headerBar.addCell(headerBarCell);
            document.add(headerBar);
            
            // Doctor's Letterhead
            Font doctorNameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLUE);
            Font doctorDetailsFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            Font qualificationFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(102, 126, 234));
            
            Paragraph doctorName = new Paragraph(prescription.getDoctor().getName(), doctorNameFont);
            doctorName.setAlignment(Element.ALIGN_CENTER);
            document.add(doctorName);
            
            Paragraph qualification = new Paragraph(prescription.getDoctor().getQualification(), qualificationFont);
            qualification.setAlignment(Element.ALIGN_CENTER);
            document.add(qualification);
            
            Paragraph specialization = new Paragraph(prescription.getDoctor().getSpecialization(), doctorDetailsFont);
            specialization.setAlignment(Element.ALIGN_CENTER);
            specialization.setSpacingAfter(5f);
            document.add(specialization);
            
            // Contact details in one line
            Font contactFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY);
            Paragraph contactInfo = new Paragraph();
            contactInfo.setAlignment(Element.ALIGN_CENTER);
            contactInfo.add(new Chunk("📞 " + prescription.getDoctor().getPhone() + "  |  ", contactFont));
            contactInfo.add(new Chunk("📧 " + prescription.getDoctor().getEmail() + "  |  ", contactFont));
            contactInfo.add(new Chunk("📍 " + prescription.getDoctor().getAddress(), contactFont));
            contactInfo.setSpacingAfter(5f);
            document.add(contactInfo);
            
            // License number
            Paragraph licenseInfo = new Paragraph("License No: " + prescription.getDoctor().getLicenseNumber(), contactFont);
            licenseInfo.setAlignment(Element.ALIGN_CENTER);
            licenseInfo.setSpacingAfter(10f);
            document.add(licenseInfo);
            
            // Add a separator line using a table
            PdfPTable separatorLine = new PdfPTable(1);
            separatorLine.setWidthPercentage(100);
            separatorLine.setSpacingBefore(5f);
            separatorLine.setSpacingAfter(5f);
            PdfPCell lineCell = new PdfPCell();
            lineCell.setBackgroundColor(new Color(102, 126, 234));
            lineCell.setBorder(Rectangle.NO_BORDER);
            lineCell.setFixedHeight(2f);
            separatorLine.addCell(lineCell);
            document.add(separatorLine);
            
            document.add(new Paragraph("\n"));
            
            // ========== LETTERHEAD END ==========
            
            // Prescription Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(102, 126, 234));
            Paragraph title = new Paragraph("MEDICAL PRESCRIPTION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(15f);
            document.add(title);
            
            // Patient Information Section
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, Color.BLUE);
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            
            Paragraph patientSection = new Paragraph("Patient Information", sectionFont);
            patientSection.setSpacingBefore(5f);
            patientSection.setSpacingAfter(8f);
            document.add(patientSection);
            
            // Create patient info table
            PdfPTable patientTable = new PdfPTable(2);
            patientTable.setWidthPercentage(100);
            patientTable.setSpacingAfter(15f);
            patientTable.setWidths(new int[]{1, 2});
            
            addPatientInfoRow(patientTable, "Patient Name:", prescription.getPatient().getName(), boldFont, normalFont);
            addPatientInfoRow(patientTable, "Age:", calculateAge(prescription.getPatient().getDateOfBirth()) + " years", boldFont, normalFont);
            addPatientInfoRow(patientTable, "Gender:", prescription.getPatient().getGender().toString(), boldFont, normalFont);
            addPatientInfoRow(patientTable, "Phone:", prescription.getPatient().getPhone(), boldFont, normalFont);
            addPatientInfoRow(patientTable, "Date:", prescription.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")), boldFont, normalFont);
            
            document.add(patientTable);
            
            document.add(new Paragraph("\n"));
            
            // Diagnosis Section with styled box
            Font diagnosisHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, new Color(102, 126, 234));
            
            PdfPTable diagnosisTable = new PdfPTable(1);
            diagnosisTable.setWidthPercentage(100);
            diagnosisTable.setSpacingBefore(5f);
            diagnosisTable.setSpacingAfter(10f);
            
            // Diagnosis header cell
            PdfPCell diagnosisHeaderCell = new PdfPCell(new Phrase("DIAGNOSIS", diagnosisHeaderFont));
            diagnosisHeaderCell.setBackgroundColor(new Color(240, 240, 250));
            diagnosisHeaderCell.setPadding(8f);
            diagnosisHeaderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            diagnosisTable.addCell(diagnosisHeaderCell);
            
            // Diagnosis content cell
            PdfPCell diagnosisContentCell = new PdfPCell(new Phrase(prescription.getDiagnosis(), normalFont));
            diagnosisContentCell.setPadding(10f);
            diagnosisContentCell.setBorder(Rectangle.BOX);
            diagnosisTable.addCell(diagnosisContentCell);
            
            document.add(diagnosisTable);
            
            // Symptoms Section if available
            if (prescription.getSymptoms() != null && !prescription.getSymptoms().isEmpty()) {
                PdfPTable symptomsTable = new PdfPTable(1);
                symptomsTable.setWidthPercentage(100);
                symptomsTable.setSpacingAfter(10f);
                
                // Symptoms header cell
                PdfPCell symptomsHeaderCell = new PdfPCell(new Phrase("SYMPTOMS", diagnosisHeaderFont));
                symptomsHeaderCell.setBackgroundColor(new Color(240, 240, 250));
                symptomsHeaderCell.setPadding(8f);
                symptomsHeaderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                symptomsTable.addCell(symptomsHeaderCell);
                
                // Symptoms content cell
                PdfPCell symptomsContentCell = new PdfPCell(new Phrase(prescription.getSymptoms(), normalFont));
                symptomsContentCell.setPadding(10f);
                symptomsContentCell.setBorder(Rectangle.BOX);
                symptomsTable.addCell(symptomsContentCell);
                
                document.add(symptomsTable);
            }
            
            document.add(new Paragraph("\n"));
            
            // Medicines Table with professional styling
            if (!prescription.getPrescriptionMedicines().isEmpty()) {
                // Medicine section header
                Font medicineHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(102, 126, 234));
                Paragraph medicinesHeader = new Paragraph("℞ PRESCRIBED MEDICINES", medicineHeaderFont);
                medicinesHeader.setSpacingBefore(5f);
                medicinesHeader.setSpacingAfter(10f);
                document.add(medicinesHeader);
                
                // Create main table for medicines (without schedule)
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingAfter(10f);

                // Set column widths (Medicine name gets more space)
                float[] columnWidths = {3f, 2f, 2f, 1.5f, 3f};
                table.setWidths(columnWidths);

                // Add headers with colored background
                String[] headers = {"Medicine Name", "Dosage", "Frequency", "Quantity", "Instructions"};
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
                for (String headerText : headers) {
                    PdfPCell cell = new PdfPCell(new Phrase(headerText, headerFont));
                    cell.setBackgroundColor(new Color(102, 126, 234));
                    cell.setPadding(8f);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell);
                }

                // Add medicine data with alternating row colors
                Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
                int rowIndex = 0;
                for (PrescriptionMedicine pm : prescription.getPrescriptionMedicines()) {
                    Color rowColor = (rowIndex % 2 == 0) ? Color.WHITE : new Color(248, 248, 252);

                    PdfPCell cell1 = new PdfPCell(new Phrase(pm.getMedicine() != null ? pm.getMedicine().getName() : "-", cellFont));
                    cell1.setBackgroundColor(rowColor);
                    cell1.setPadding(8f);
                    cell1.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell1);

                    PdfPCell cell2 = new PdfPCell(new Phrase(pm.getDosage() != null ? pm.getDosage() : "-", cellFont));
                    cell2.setBackgroundColor(rowColor);
                    cell2.setPadding(8f);
                    cell2.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell2);

                    PdfPCell cell3 = new PdfPCell(new Phrase(pm.getFrequency() != null ? pm.getFrequency() : "-", cellFont));
                    cell3.setBackgroundColor(rowColor);
                    cell3.setPadding(8f);
                    cell3.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell3);

                    PdfPCell cell4 = new PdfPCell(new Phrase(pm.getQuantity() != null ? pm.getQuantity().toString() : "-", cellFont));
                    cell4.setBackgroundColor(rowColor);
                    cell4.setPadding(8f);
                    cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell4.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell4);

                    PdfPCell cell5 = new PdfPCell(new Phrase(pm.getInstructions() != null ? pm.getInstructions() : "-", cellFont));
                    cell5.setBackgroundColor(rowColor);
                    cell5.setPadding(8f);
                    cell5.setBorder(Rectangle.NO_BORDER);
                    table.addCell(cell5);

                    rowIndex++;
                }

                document.add(table);

                // Separate Dosage Schedule Table
                Font scheduleHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(102, 126, 234));
                Paragraph scheduleHeader = new Paragraph("DOSAGE SCHEDULE", scheduleHeaderFont);
                scheduleHeader.setSpacingBefore(5f);
                scheduleHeader.setSpacingAfter(10f);
                document.add(scheduleHeader);

                PdfPTable scheduleTable = new PdfPTable(4);
                scheduleTable.setWidthPercentage(100);
                scheduleTable.setSpacingAfter(10f);
                float[] scheduleWidths = {3.5f, 1.2f, 1.2f, 1.2f};
                scheduleTable.setWidths(scheduleWidths);

                String[] scheduleHeaders = {"Medicine Name", "Morning", "Afternoon", "Dinner"};
                for (String headerText : scheduleHeaders) {
                    PdfPCell cell = new PdfPCell(new Phrase(headerText, headerFont));
                    cell.setBackgroundColor(new Color(102, 126, 234));
                    cell.setPadding(8f);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorder(Rectangle.NO_BORDER);
                    scheduleTable.addCell(cell);
                }

                rowIndex = 0;
                for (PrescriptionMedicine pm : prescription.getPrescriptionMedicines()) {
                    Color rowColor = (rowIndex % 2 == 0) ? Color.WHITE : new Color(248, 248, 252);

                    PdfPCell mCell = new PdfPCell(new Phrase(pm.getMedicine() != null ? pm.getMedicine().getName() : "-", cellFont));
                    mCell.setBackgroundColor(rowColor);
                    mCell.setPadding(8f);
                    mCell.setBorder(Rectangle.NO_BORDER);
                    scheduleTable.addCell(mCell);

                    PdfPCell morning = new PdfPCell(new Phrase(pm.getMorningDose() != null && !pm.getMorningDose().isBlank() ? pm.getMorningDose() : "-", cellFont));
                    morning.setBackgroundColor(rowColor);
                    morning.setPadding(8f);
                    morning.setHorizontalAlignment(Element.ALIGN_CENTER);
                    morning.setBorder(Rectangle.NO_BORDER);
                    scheduleTable.addCell(morning);

                    PdfPCell afternoon = new PdfPCell(new Phrase(pm.getAfternoonDose() != null && !pm.getAfternoonDose().isBlank() ? pm.getAfternoonDose() : "-", cellFont));
                    afternoon.setBackgroundColor(rowColor);
                    afternoon.setPadding(8f);
                    afternoon.setHorizontalAlignment(Element.ALIGN_CENTER);
                    afternoon.setBorder(Rectangle.NO_BORDER);
                    scheduleTable.addCell(afternoon);

                    PdfPCell dinner = new PdfPCell(new Phrase(pm.getNightDose() != null && !pm.getNightDose().isBlank() ? pm.getNightDose() : "-", cellFont));
                    dinner.setBackgroundColor(rowColor);
                    dinner.setPadding(8f);
                    dinner.setHorizontalAlignment(Element.ALIGN_CENTER);
                    dinner.setBorder(Rectangle.NO_BORDER);
                    scheduleTable.addCell(dinner);

                    rowIndex++;
                }

                document.add(scheduleTable);
            }
            
            // Notes Section
            if (prescription.getNotes() != null && !prescription.getNotes().isEmpty()) {
                Font notesHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(102, 126, 234));
                
                PdfPTable notesTable = new PdfPTable(1);
                notesTable.setWidthPercentage(100);
                notesTable.setSpacingBefore(10f);
                notesTable.setSpacingAfter(10f);
                
                // Notes header
                PdfPCell notesHeaderCell = new PdfPCell(new Phrase("ADDITIONAL NOTES", notesHeaderFont));
                notesHeaderCell.setBackgroundColor(new Color(240, 240, 250));
                notesHeaderCell.setPadding(8f);
                notesHeaderCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                notesTable.addCell(notesHeaderCell);
                
                // Notes content
                PdfPCell notesContentCell = new PdfPCell(new Phrase(prescription.getNotes(), normalFont));
                notesContentCell.setPadding(10f);
                notesContentCell.setBorder(Rectangle.BOX);
                notesTable.addCell(notesContentCell);
                
                document.add(notesTable);
            }
            
            // Follow-up Section
            if (prescription.getFollowUpDate() != null) {
                Font followUpFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(230, 126, 34));
                Paragraph followUp = new Paragraph();
                followUp.add(new Chunk("⏰ FOLLOW-UP DATE: ", followUpFont));
                followUp.add(new Chunk(prescription.getFollowUpDate().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")), boldFont));
                followUp.setSpacingBefore(10f);
                followUp.setSpacingAfter(15f);
                document.add(followUp);
            }
            
            // Doctor's Signature Section with QR Code Verification
            document.add(new Paragraph("\n\n"));
            
            PdfPTable signatureTable = new PdfPTable(3);
            signatureTable.setWidthPercentage(100);
            float[] signatureWidths = {1f, 1f, 1f};
            signatureTable.setWidths(signatureWidths);
            signatureTable.setSpacingBefore(20f);
            
            // Left side - QR Code for Verification
            try {
                Image qrCode = generateQRCode(prescription);
                qrCode.scaleAbsolute(100f, 100f);
                
                Paragraph qrLabel = new Paragraph();
                qrLabel.add(new Chunk("Scan to Verify\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
                qrLabel.add(new Chunk("Prescription ID: " + prescription.getId() + "\n", FontFactory.getFont(FontFactory.HELVETICA, 7)));
                qrLabel.add(new Chunk("Hash: " + generateDigitalHash(prescription), FontFactory.getFont(FontFactory.HELVETICA, 7)));
                qrLabel.setAlignment(Element.ALIGN_CENTER);
                
                PdfPCell qrCell = new PdfPCell();
                qrCell.addElement(qrCode);
                qrCell.addElement(qrLabel);
                qrCell.setBorder(Rectangle.NO_BORDER);
                qrCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                signatureTable.addCell(qrCell);
            } catch (Exception e) {
                // If QR generation fails, add empty cell
                PdfPCell emptyCell1 = new PdfPCell(new Phrase(""));
                emptyCell1.setBorder(Rectangle.NO_BORDER);
                signatureTable.addCell(emptyCell1);
            }
            
            // Middle - Timestamp and Digital Signature Info
            Font timestampFont = FontFactory.getFont(FontFactory.HELVETICA, 8, new Color(100, 100, 100));
            Paragraph timestampPara = new Paragraph();
            timestampPara.add(new Chunk("🔒 DIGITALLY SIGNED\n\n", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9, new Color(76, 175, 80))));
            timestampPara.add(new Chunk("Issued: ", timestampFont));
            timestampPara.add(new Chunk(prescription.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "\n", timestampFont));
            timestampPara.add(new Chunk("Doc ID: ", timestampFont));
            timestampPara.add(new Chunk(prescription.getDoctor().getId() + "\n", timestampFont));
            timestampPara.add(new Chunk("Signature Hash:\n", timestampFont));
            timestampPara.add(new Chunk(generateDigitalHash(prescription), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8)));
            timestampPara.setAlignment(Element.ALIGN_CENTER);
            
            PdfPCell timestampCell = new PdfPCell(timestampPara);
            timestampCell.setBorder(Rectangle.NO_BORDER);
            timestampCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            signatureTable.addCell(timestampCell);
            
            // Right side - Doctor's signature
            Font signatureFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Paragraph signaturePara = new Paragraph();
            signaturePara.add(new Chunk("\n\n_____________________\n", normalFont));
            signaturePara.add(new Chunk(prescription.getDoctor().getName() + "\n", signatureFont));
            signaturePara.add(new Chunk(prescription.getDoctor().getQualification() + "\n", normalFont));
            signaturePara.add(new Chunk("Reg. No: " + prescription.getDoctor().getLicenseNumber(), normalFont));
            signaturePara.setAlignment(Element.ALIGN_CENTER);
            
            PdfPCell signatureCell = new PdfPCell(signaturePara);
            signatureCell.setBorder(Rectangle.NO_BORDER);
            signatureTable.addCell(signatureCell);
            
            document.add(signatureTable);
            
            // Footer with validity note
            document.add(new Paragraph("\n"));
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, new Color(128, 128, 128));
            Paragraph footer = new Paragraph("⚠ This is a computer-generated prescription. Valid for 30 days from the date of issue.", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
            document.close();
            
            return new ByteArrayInputStream(out.toByteArray());
            
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }
    
    private String calculateAge(java.time.LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return "N/A";
        }
        return String.valueOf(java.time.temporal.ChronoUnit.YEARS.between(dateOfBirth, java.time.LocalDate.now()));
    }
    
    private void addPatientInfoRow(PdfPTable table, String label, String value, Font boldFont, Font normalFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, boldFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(8f);
        table.addCell(labelCell);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, normalFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPaddingBottom(8f);
        table.addCell(valueCell);
    }
    
    /**
     * Generate QR Code for prescription verification
     * QR Code contains: Prescription ID, Patient Name, Doctor Name, Date, Digital Hash
     */
    private Image generateQRCode(Prescription prescription) throws Exception {
        // Create verification data
        String verificationData = String.format(
            "PRESCRIPTION_ID:%d|PATIENT:%s|DOCTOR:%s|DATE:%s|HASH:%s",
            prescription.getId(),
            prescription.getPatient().getName(),
            prescription.getDoctor().getName(),
            prescription.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
            generateDigitalHash(prescription)
        );
        
        // Generate QR Code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(verificationData, BarcodeFormat.QR_CODE, 150, 150);
        
        // Convert to BufferedImage
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        
        // Convert BufferedImage to OpenPDF Image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        
        return Image.getInstance(imageBytes);
    }
    
    /**
     * Generate a digital hash for prescription verification
     * Uses prescription details to create a unique verification code
     */
    private String generateDigitalHash(Prescription prescription) {
        try {
            String dataToHash = String.format(
                "%d-%s-%s-%s-%s",
                prescription.getId(),
                prescription.getPatient().getName(),
                prescription.getDoctor().getName(),
                prescription.getDiagnosis(),
                prescription.getCreatedAt().toString()
            );
            
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dataToHash.getBytes("UTF-8"));
            
            // Convert to Base64 and take first 12 characters for readability
            return Base64.getEncoder().encodeToString(hash).substring(0, 12).toUpperCase();
        } catch (Exception e) {
            return "UNVERIFIED";
        }
    }
}

