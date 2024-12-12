package com.project.prova.demo.service;

import com.project.prova.demo.model.UploadedDocument;
import com.project.prova.demo.repository.DocumentRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public UploadedDocument saveDocument(MultipartFile file, boolean visibleToUsers) throws Exception {
        UploadedDocument document = new UploadedDocument();
        document.setName(file.getOriginalFilename());
        document.setContentType(file.getContentType());
        document.setData(file.getBytes());
        document.setVisibleToUsers(visibleToUsers); // Imposta la visibilità

        return documentRepository.save(document);
    }
    public List<UploadedDocument> getVisibleDocuments() {
        return documentRepository.findByisVisibleToUsersTrue();  // Trova i documenti con isVisible == true
    }

    // Metodo per recuperare un documento tramite id
    public UploadedDocument getDocumentById(String id) {
        return documentRepository.findById(id).orElse(null);  // Trova un documento per id
    }
}
