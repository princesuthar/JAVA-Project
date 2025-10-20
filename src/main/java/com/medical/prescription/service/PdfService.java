package com.medical.prescription.service;

import com.medical.prescription.entity.Prescription;
import java.io.ByteArrayInputStream;

public interface PdfService {
    ByteArrayInputStream generatePrescriptionPdf(Prescription prescription);
}


