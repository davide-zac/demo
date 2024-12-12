package com.project.prova.demo.repository;

import com.project.prova.demo.model.UploadedDocument;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<UploadedDocument, String> {
  //  List<UploadedDocument> findByVisibleToUsers(boolean visibleToUsers);
    List<UploadedDocument> findByisVisibleToUsersTrue();  // Trova i documenti visibili

}
