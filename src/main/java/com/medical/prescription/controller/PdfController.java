package com.medical.prescription.controller;

import com.medical.prescription.entity.Prescription;
import com.medical.prescription.service.PdfService;
import com.medical.prescription.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Controller
@RequestMapping("/pdf")
public class PdfController {
    
    @Autowired
    private PdfService pdfService;
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @GetMapping("/prescription/{id}")
    public ResponseEntity<InputStreamResource> generatePrescriptionPdf(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        
        if (prescription.isPresent()) {
            ByteArrayInputStream bis = pdfService.generatePrescriptionPdf(prescription.get());
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=prescription_" + id + ".pdf");
            
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/prescription/{id}/download")
    public ResponseEntity<InputStreamResource> downloadPrescriptionPdf(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        
        if (prescription.isPresent()) {
            ByteArrayInputStream bis = pdfService.generatePrescriptionPdf(prescription.get());
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=prescription_" + id + ".pdf");
            
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        }
        
        return ResponseEntity.notFound().build();
    }
}


