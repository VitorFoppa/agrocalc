package com.agrocalc.service;

import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public String extrairTexto(InputStream file) {
        try (PDDocument document = PDDocument.load(file)) {

            PDFTextStripper stripper = new PDFTextStripper();
            String texto = stripper.getText(document);

            if (texto == null || texto.isBlank()) {
                throw new RuntimeException("PDF não contém texto legível");
            }

            return texto;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler PDF", e);
        }
    }
}