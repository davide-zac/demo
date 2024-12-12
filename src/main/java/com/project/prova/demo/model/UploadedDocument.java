package com.project.prova.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
public class UploadedDocument {
    @Id
    private String id;
    private String name;
    private String contentType;
    private byte[] data;
    private boolean isVisibleToUsers; 


    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public boolean isVisibleToUsers() { return isVisibleToUsers; }
    public void setVisibleToUsers(boolean visibleToUsers) { this.isVisibleToUsers = visibleToUsers; }    

}
