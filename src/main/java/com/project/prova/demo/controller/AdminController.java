package com.project.prova.demo.controller;

import com.project.prova.demo.model.UploadedDocument;
import com.project.prova.demo.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final DocumentService documentService;

    public AdminController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard() {
        log.info("Accesso al dashboard admin");
        return "Benvenuto nella dashboard degli Admin!";
    }

    @PostMapping("/documents/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UploadedDocument> uploadDocument(@RequestParam("file") MultipartFile file,  
    @RequestParam("visibleToUsers") boolean visibleToUsers) {
        log.info("Tentativo di caricamento file da parte di un amministratore.");
        try {
            UploadedDocument savedDocument = documentService.saveDocument(file,visibleToUsers);
            log.info("File salvato con successo: {}", savedDocument.getName());
            return ResponseEntity.ok(savedDocument);
        } catch (Exception e) {
            log.error("Errore durante il caricamento del file: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
