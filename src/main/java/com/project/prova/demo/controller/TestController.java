package com.project.prova.demo.controller;

import com.project.prova.demo.model.UploadedDocument;
import com.project.prova.demo.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    private final DocumentService documentService;

    // Inietta il DocumentService per accedere ai documenti
    public TestController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('TEST')")
    public String testDashboard() {
        log.info("Accesso al dashboard test");
        return "Benvenuto nella dashboard dei Test Users!";
    }

    // Restituisce la lista dei documenti visibili
    @GetMapping("/documents/visible")
    @PreAuthorize("hasRole('TEST')")
    public ResponseEntity<List<UploadedDocument>> getVisibleDocuments() {
        log.info("Recupero documenti visibili per gli utenti test");
        List<UploadedDocument> visibleDocuments = documentService.getVisibleDocuments();
        return ResponseEntity.ok(visibleDocuments);
    }

    // Endpoint per scaricare un documento
    @GetMapping("/documents/{id}/download")
    @PreAuthorize("hasRole('TEST')")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String id) {
        log.info("Download del documento con ID: {}", id);
        UploadedDocument document = documentService.getDocumentById(id);

        if (document == null) {
            return ResponseEntity.status(404).body(null);
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"")
                // .contentType(org.springframework.http.MediaType.parseMediaType(document.getContentType()))
                .body(document.getData());
    }
}


